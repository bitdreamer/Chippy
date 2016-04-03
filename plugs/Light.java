// Chippy.java					Susan Hwang and Samah Kattan 2005
// also Samah and Emily  .... and Barry

package plugs;
import java.awt.*;
import java.io.*;

import Chippy.Chippy;
import Chippy.Piece;
import Chippy.Pin;

public class Light extends Piece implements Serializable
{
	private Color c;
	protected Pin pin1;
	protected Pin pin2;
	protected boolean lit = false;

   // names of available lights.  MUST agree with makeLight method below.
   public static String[] lightNames =
      {"light color", // 0
       "green", // 1
       "yellow", // 2
       "red",  // 3
       "blue" // 4
      };
      
   public static String getLightName( int i ) { return lightNames[i]; }


   // return a Light object of type t1 from list above
   public static Light makeLight( int t1 )
   {
      //System.out.println("Light.makeLight: entering ...");
      Light litey = null;
      switch ( t1 )
      {
          case 0: break;
          case 1: litey = new Light( Color.green ); break;
          case 2: litey = new Light( Color.yellow ); break;
          case 3: litey = new Light( Color.red ); break;
          case 4: litey = new Light( new Color(200,200,250) ); break; // blue
      }
      return litey;
   }

   public Light( Color nooc )
   {
      width = height = (int)(1.0*getGridSize()); // gridSize = 10;
      c = nooc;
      xanchor = gridify(100); yanchor = gridify(100);
		pin1 = new Pin(this,0,0); // Pin(x1,y1,"1",this);
		pin2 = new Pin(this,0,gridSize); // Pin(x1,y1+10,"2",this);
      connectix.add(pin1);
      connectix.add(pin2);
   }
   
   public String saveMe()
   {
      return "light "+c.getRed()+" "+c.getGreen()+" "+c.getBlue()+" "+xanchor+" "+yanchor+"\n";
   }

   //
   @Override
	public boolean charge()
	{
      // System.out.println("   light.charge: lit="+lit);
      int v1 = pin1.getDrivenV();
      int v2 = pin2.getDrivenV();

      lit = ( v2==0 && v1>=4 );
      return false; // it may actually have changed, but a lit light  
      // doesn't propogate to anything else, so ... don't worry about it.
	}
	

	@Override
   // draw the light bulb
   public void draw(Graphics g )
	{
		if (lit)
		{
//			System.out.println("light is lit...WOO HOO!");
			g.setColor(c);
		}
		else
		{g.setColor(c.darker());}
		
		g.fillOval( getX(), getY(), width, height );
		
		if (lit)
		{
		g.setColor( Color.white );
		g.fillOval( getX()+2, getY()+4,4,4 );
		}

		pin1.draw(g);
		pin2.draw(g);
		
	}

   public static void main(String args[])
	{
		Chippy ch = new Chippy();
  	}

}