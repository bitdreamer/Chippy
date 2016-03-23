// Wire.java 
// Susan Hwang and Samah Kattan  2005
// Edited by Samah and Emily//=================================================================
// Barrett Koster 2005 edits .... 214 edits ...

// Wire represents a wire.  It has two pins on the ends and a color.

package Chippy;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.JComboBox;

public class Wire extends Piece implements Serializable
{
   static boolean bug = false;
   static LinkedList <colorChoice> colors;

	   private static String[] wireNames =   {"Choose a Wire", "black", "red"
                                   ,"yellow","green","blue"
                                  }; 

	
	private String color; // printable name for this color
	private Pin endpoint1, endpoint2; 
	//private int wireLength = 40;
	private Color c; // wire is this color
	int pinSelected = 0;

  	protected static Chippy theChippy;
  	/*
  	public Wire( int x, int y, Color c1 )
  	{
  	}
*/
  	
  	// This function returns a JComboBox object that contains all
  	// of the current color choices
  	static public JComboBox getChoiceBox()
  	{
  	   JComboBox jcb;
  	   colors = new LinkedList <colorChoice> ();
  	   
  	   colorChoice cc; // temp., used in loop later, holds colorChoice, 
  	   
  	   // Here is the list of wire colors.  If you want a new color, add it here.
      colors.add( new colorChoice( " Wires - black", Color.black ) );
      colors.add( new colorChoice( "red", Color.red ) );
      colors.add( new colorChoice( "yellow", Color.yellow ) );
      colors.add( new colorChoice( "green", Color.green ) );
      colors.add( new colorChoice( "blue", Color.blue ) );
      colors.add( new colorChoice( "pink", new Color(255,200,200) ) );
      
      String[] ca = new String[colors.size()];
      Iterator <colorChoice> it = colors.iterator();
      for ( int i=0; i<colors.size(); i++ )
      {
         cc = it.next();
         ca[i] = cc.name;
      }
  	   
      jcb = new JComboBox( ca );
  	   return jcb;
  	}
	
  	// given a string name, returns the Color that goes with that.
  	// It should never fail (return null), because the choices should always
  	// come from the list we are looking in.
  	static public Color whatColor( String n )
  	{
  	   Color c=null;
  	   
  	   boolean found = false;
  	   Iterator <colorChoice> it = colors.iterator();
  	   while ( !found && it.hasNext() )
  	   {
  	      colorChoice cc = it.next();
  	      if ( cc.name.equals(n) ) { found = true; c = cc.color; }
  	   }
  	   return c;
  	}
	
  	
   // constructor
   public Wire(int x , int y, int color)
   {
      xanchor = x; yanchor = y; name = "wire";
      //super( x, y );
      endpoint1 = new Pin(this,0,0); // Pin(x, y, "endpoint1", this);
      //pins.add(endpoint1);
      connectix.add(endpoint1);
      endpoint2 = new Pin(this,0,50); // Pin(x, y+50, "endpoint2", this);
      //pins.add(endpoint2);
      connectix.add(endpoint2);
      
      if      (color == 1) {c = Color.black;}
      else if (color == 2) {c = Color.red;}
      else if ( color==3 ) { c = Color.yellow; }
      else if ( color==4 ) { c = Color.green; }
      else if ( color==5 ) { c = Color.blue; }
   }
   // constructor
   public Wire(int x , int y, Color c1 )
   {
      xanchor = x; yanchor = y; name = "wire";
      //super( x, y );
      endpoint1 = new Pin(this,0,0); // Pin(x, y, "endpoint1", this);
      //pins.add(endpoint1);
      connectix.add(endpoint1);
      endpoint2 = new Pin(this,0,50); // Pin(x, y+50, "endpoint2", this);
      //pins.add(endpoint2);
      connectix.add(endpoint2);
      
      c = c1;
   }
	
	public Wire( int x1, int y1, int x2, int y2, Color c1 )
	{
      xanchor = x1; yanchor = y1; name = "wire";
      //super( x, y );
      endpoint1 = new Pin(this,0,0); // Pin(x, y, "endpoint1", this);
      //pins.add(endpoint1);
      connectix.add(endpoint1);
      endpoint2 = new Pin(this,x2-x1,y2-y1); // Pin(x, y+50, "endpoint2", this);
      //pins.add(endpoint2);
      connectix.add(endpoint2);
      
      c = c1;

	}
	
	public String saveMe()
	{
      return "wire "+c.getRed()+" "+c.getGreen()+" "+c.getBlue()+" "
            +endpoint1.xab+" "+endpoint1.yab + " "
            +endpoint2.xab+" "+endpoint2.yab+"\n";
    
	}
	
	
	//--------------------------------------------------------------------------
	// sets and gets
	//--------------------------------------------------------------------------
	public void setEndpoint1(int x) {endpoint1.setX(x);}
	public void setEndpoint2(int y) {endpoint2.setY(y);}
	public int getEndpoint1() 		  {return endpoint1.getX();}
	public int getEndpoint2()   	  {return endpoint2.getY();}

   @Override
   // Do the wire thing between the endpoints.
   // Look at inputs from holes and figure out output state of pins.
   // If there are two drivers, voltage = 2 (fire).
   // Else, propagate whatever is driven from one end to the other.
   // If no drivers, float.
	public boolean charge()
	{
      //if (bug) { System.out.println("Wire.charge: entering ..."); }
      
      return likeWire( endpoint1, endpoint2 );
   }

   // This method is done as a static so that we can 
   // use it to make a button look like a wire when it is pressed.
   public static boolean likeWire( Pin endpoint1, Pin endpoint2 )
   {
      if (bug) { System.out.println("Wire.likeWire: entering ..."); }
      
      boolean changed = false; // start with no change 
      
      boolean e1d = endpoint1.isDriven();
      boolean e2d = endpoint2.isDriven();
      if ( e1d && e2d ) // fire, doesn't matter if they are the same
      {
         changed |= endpoint1.setVoltage(2); endpoint1.setIdrive(false);
         changed |= endpoint2.setVoltage(2); endpoint2.setIdrive(false);
         if (bug) { System.out.println("    fire");}
      }
      else if ( e1d )
      {
         int v = endpoint1.getBuddy().getVoltage();
         changed |= endpoint1.setVoltage(v); endpoint1.setIdrive(false);
         changed |= endpoint2.setVoltage(v); endpoint2.setIdrive(true);
         if (bug) { System.out.println("    endpoint1 being driven");}
      }
      else if ( e2d )
      {
         int v = endpoint2.getBuddy().getVoltage();
         changed |= endpoint1.setVoltage(v); endpoint1.setIdrive(true);
         changed |= endpoint2.setVoltage(v); endpoint2.setIdrive(false);
         if (bug) { System.out.println("    endpoint2 being driven");}
      }
      else // wires don't float, but we don't want to mistake it for battery 0
           // either, so let's just say it floats
      {
         changed |= endpoint1.setVoltage(3); endpoint1.setIdrive(false);
         changed |= endpoint2.setVoltage(3); endpoint2.setIdrive(false);
         if (bug) { System.out.println("    nada, everybody floating   ");}
      }
		
      //		System.out.println("wire - " + voltage);
      return changed;
	}
	 
	public static void setTheChippy(Chippy x){theChippy = x;}

   public boolean grab( int x, int y ) { return contains( x, y ); }
   //--------------------------------------------------------------------------
	// determines if x,y are within the bounds of this shape
	//--------------------------------------------------------------------------
   public boolean contains(int x, int y) 
	{
      boolean ret = false; // assume not contains unless found
       
		int x1 = endpoint1.getX();
		int y1 = endpoint1.getY();
		int x2 = endpoint2.getX();
		int y2 = endpoint2.getY();
      int tolerance = 5;  // give x,y coordinate a little more room to find a match
      
     
	  	if (((x >= x1-tolerance) && (x <= x1 + tolerance)) && 
			 ((y >= y1-tolerance) && (y <= y1 + tolerance)))
	   {
			pinSelected = 1;
			ret = true; // return true;
		}
	  	else if (((x >= x2-tolerance) && (x <= x2 + tolerance)) && 
				   ((y >= y2-tolerance) && (y <= y2 + tolerance ))) 
		{
      	pinSelected = 2;
		 	ret = true; // return true;
		} 
		// else {return false;}
      
      //if ( ret ) { System.out.println("hi "); }
      
      return ret;
   }

   // move.  move the (previously) selected end pin to given xy
   // Note: most move() operations are the same, but wires move
   // differently.
   @Override
	public void move(int x, int y)
	{
		super.move(x,y);
      		
		if (pinSelected == 1)
		{
			endpoint1.setX(gridify(x));
			endpoint1.setY(gridify(y));
			//endpoint1.findAHole();
		}
		
		else if (pinSelected == 2)
		{
			endpoint2.setX(gridify(x));
			endpoint2.setY(gridify(y));
			//endpoint2.findAHole();
     	}
	}

   // pins on wires don't troll, they stay at absolute coordinates.
   @Override
   public void troll() {}

	//--------------------------------------------------------------------------
   // draws the wire
	//--------------------------------------------------------------------------	
   @Override
   public void draw( Graphics g )
	{
		Graphics2D g2 = (Graphics2D)g;
      g2.setColor(c);
		
		// change color is selected
		if (getSelected())
		{
			g2.setColor(c.darker());
		}
/*		if (voltage == 10)
		{
			g2.setColor(Color.orange);
		}
*/
		g2.setStroke(new BasicStroke(3));
	   g2.drawLine( endpoint1.getX()+2, endpoint1.getY()+2,
                   endpoint2.getX()+2, endpoint2.getY()+2
                 ); 
		endpoint1.draw(g);
		endpoint2.draw(g);
		g2.setStroke(new BasicStroke(1));
		
		//reset color
		g2.setColor(c);
   }
   
   // access
   public static String[] getWireNames() { return wireNames; }
}