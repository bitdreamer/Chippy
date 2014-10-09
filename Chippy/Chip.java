package Chippy;
// Chip.java
// Edited by Samah and Emily and Susan
// Barrett Koster 2005 edits ....

import java.awt.*;
import java.io.*;
import java.util.*;

public abstract class Chip extends RectangularPiece implements Serializable
{
   //protected boolean rotated; // false=pin1 at upper left? get rid of this 
   protected boolean selected;
   protected Pin pinArray[]; // 0 not used, pins are 1-14 say
   protected int numPins = 14;
   protected String chip74;
   protected boolean powered = false; // true iff chip has power
   
   int powerpin = 14;
   int groundpin = 7;

   // names of available chips.  MUST agree with method below.
   public static String[] chipNames =
      {"Choose a Chip", // 0
       "7404", // 1
       "7408", // 2
       "7432",  // 3
       "74377" // 4
      };

   // return a Chip object of type t1 from list above
   // This is a bad way to code this.  Change it to localize all chip-particular
   // information in the subclass for that type of chip.
   public static Chip makeChip( int t1 )
   {
      //System.out.println("Chip.makeChip: entering ...");
      Chip c = null;
      switch ( t1 )
      {
          case 0: break;
          case 1: c = new Chip7404(); break;
          case 2: c = new Chip7408(); break;
          case 3: c = new Chip7432(); break;
          case 4: c = new Chip74377(); break;
      }       
      return c;
   }
   
   public static Chip makeChip( StringTokenizer st )
   {

      Chip c = null;
      
      try
      {
         String key = st.nextToken();
         if      ( key.equals("7404" ) ) { c = new Chip7404(); }
         else if ( key.equals("7408" ) ) { c = new Chip7408(); }
         else if ( key.equals("7432" ) ) { c = new Chip7432(); }
         else if ( key.equals("74377") ) { c = new Chip74377(); }
          
         int x = Integer.parseInt( st.nextToken() );
         int y = Integer.parseInt( st.nextToken() );
         
         c.move(x,y);
      }
      catch( Exception e )
      { System.out.println("read error on TTL ..."+e); }
      
      return c;
   }
   
   public String saveMe()
   {
      return "TTL "+ chip74 + " " +xanchor+" "+yanchor+"\n";
   }

   
   // make a generic chip, give it np1 number of legs
   public Chip( int np1 )
   {
      System.out.println("Chip.Chip: entering");
      xanchor = 160;
      yanchor = 250;
      height = 65;
      width = 20;

      numPins = np1;
      pinArray = new Pin[numPins+1]; // Pin[0] is not used
      pinArray[0] = new Pin(this,0,0); // Pin(0,0,0, this );
         // dummy pin, shouldn't be used
      int y = 0;
      for ( int j=1; j<=numPins/2; j++ )
      {
          int k = numPins-j+1;
          pinArray[j] = new Pin(this,-10,y); // Pin( xanchor-10, y, j, this );
          connectix.add(pinArray[j]);
          pinArray[k] = new Pin(this,20,y); // Pin( xanchor+20, y, k, this );
          connectix.add(pinArray[k]);
          y += 10;
      }
      
      // usual default
      groundpin = numPins / 2;
      powerpin = numPins;
      
      troll(); // movePins();
   }
   
   // set powered=true if chip has power.  Only responds if 
   // needs is set on power or ground pin.
   // Returns value of powered.  
   public boolean checkPower()
   {
      Pin gp = pinArray[groundpin]; 
      Pin pp = pinArray[powerpin];
      
      if ( gp.needs || pp.needs )
      {
         powered = false;
         Hole h0 = (Hole)(pinArray[groundpin].getBuddy());
         Hole h5 = (Hole)(pinArray[powerpin].getBuddy());
         if ( h0!=null && h5!=null )
         {
            if (   h0.getIdrive() && h0.getVoltage()==0
                && h5.getIdrive() && h5.getVoltage()==5
               )
            { powered = true; setAllNeeds(); }
         }
         gp.needs = pp.needs = false; // These don't need attention any more; you just did it.
      }
      return powered;
   }
   
   // set all of the needs flags on this chip.   Use this when powering
   // up a chip, all pin interactions need attention.
   public void setAllNeeds()
   {
      for ( int i=0; i<numPins; i++ )
      {
         Pin p = pinArray[i];
         if ( !p.idrive ) { p.needs = true; }
      }
   }
   
   // set all of the pins to floating.  We do this when the chip is 
   // unpowered.  
   public void setAllFloating()
   {
      for ( int i=0; i<numPins; i++ )
      {
         Pin p = pinArray[i];
         p.setVoltage(3);
      }
   }
   
   public abstract boolean charge();

   // draw.  Draws the chip, with pins and chip74 string down the middle
  	@Override
   public void draw(Graphics g )
   {	   
      // draw the gray box part
      if (isSelected) {g.setColor(Color.lightGray.darker());}
		else            {g.setColor(Color.lightGray         );}
		g.fillRect(xanchor-2,yanchor,width,height);
		
		//label the chip (write chip74 string down the chip)
		g.setColor(Color.black);
      int tx = xanchor+3;
      int ty = yanchor + 15;
      for ( int i=0; i<chip74.length(); i++ )
      {
          g.drawString(""+chip74.charAt(i), tx, ty );
          ty += 12;
      }
	 	
		//goes through the pin array and draws each pin
      // Note: we count 1-14, not 0-13
	 	for (int i = 1; i<=numPins; i++)
	 	{
	 		pinArray[i].draw(g);
	 	}
   }
}