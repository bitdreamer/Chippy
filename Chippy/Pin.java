// Pin.java
// 2005 Software Engineering.   Started by Samah and Emily
// ... and Barry

package Chippy;

import java.awt.*;
import java.io.*;

public class Pin extends Connectic
        implements Serializable
{
	protected int height;    // vertical size of pin drawing in pixels
  	protected int width;     // horizontal size ...
  	protected Color c;			// the color of this pin
  	protected String name;
  	protected int index; // index of pin in a chip, works for some?
  	

   // constructor.  takes piece this is attached to and relative coords
   public Pin( Piece p, int xr, int yr )
   {
      super( p, xr, yr );
      //System.out.println("Pin.Pin: xr="+xr+" yr="+yr);
      height = 5;
      width = 5;
      c = Color.black;
   }
   
   // constructor.  takes piece this is attached to and relative coords
   // Also takes this pins index in this chip if any
   public Pin( Piece p, int xr, int yr, int index1 )
   {
      super( p, xr, yr );
      //System.out.println("Pin.Pin: xr="+xr+" yr="+yr);
      height = 5;
      width = 5;
      c = Color.black;
      index = index1;
   }

	//--------------------------------------------------------------------------
	// accessor and mutator methods for private variables
	//--------------------------------------------------------------------------
	public void  setHeight(int h) 	{height = h;}
	public int   getHeight() 			{return height;}
	public void  setWidth(int w) 		{width = w;};
	public int   getWidth()				{return width;}
 	public void  setColor(Color newC){c = newC;}
	public Color getColor()				{return c;}
   public int getX() {return xab;}
   public int getY() {return yab;}
	public void setX(int xx) {xab = xx;}
	public void setY(int yy) {yab = yy;}
   public void setXY( int x1, int y1 ) { xab = x1; yab = y1; }
   
   //public void setIdrive( boolean b ) { idrive = b; }

   // public boolean getIdrive() { return idrive; }

   public boolean isHigh() { return ( (voltage==3) || (voltage==5) ); }
   public boolean isLow () { return ( (voltage==0) || (voltage==1) ); }
   // Note: High should also result from non-connected input, but I can't
   // tell what voltage that's supposed to be.
	//public Hole getHole() {return myHole;}
    
   
   // return true if this pin is in a hole and the hole is driving.
   public boolean isDriven()
   {
      boolean driven = false;
      if ( buddy!=null )
      {
         driven = buddy.idrive; //if ( myHole.idrive ) { driven = true; }
      }
      return driven;
   }

  	//--------------------------------------------------------------------------
	// returns true iff x,y are within the bounds of this shape
	//--------------------------------------------------------------------------
	public boolean contains(int x, int y)
	{
		int x1 = getX();
	 	int y1 = getY();
	  	if (((x >= x1) && (x <= x1 + height)) && 
		    ((y >= y1) && (y <= y1 + width)))
	     {return true;}
	  	else
	     {return false;}
   }
	
	//--------------------------------------------------------------------------
	// determines if x,y are the same as x,y for this piece
	//--------------------------------------------------------------------------
	public boolean hasXY(int x, int y)
	{
		int x1 = getX();
	  	int y1 = getY();
	  	if ((x1 == x)  && (y1 == y))
	     {return true;}
	   else 
		  {return false;}
	}

   // find a hole and put this pin in it if there.
   // If there is no hole, clear
   public void findAHole()
	{
      // h is the hole you are supposed to be in
      Hole h = theChippy.findAHole(xab, yab);
      
      // disconnect from old buddy is it exists and is not h
      if( buddy!=null && buddy!=h ) { buddy.setBuddy(null); }
      
      // if h is not null, connect to it
		if (h != null)
		{
         buddy = h;
			buddy.setBuddy(this);
         //System.out.println("Pin.findAHole: match found and set");
		}
      else // h is null
      { buddy = null; // we've already cut the old pin loose
      }
	}

   //
   public void draw(Graphics g)
	{
	   int gzover2 = CKTPanel.gz / 2;
	
		g.setColor(Color.black);
      // if ( idrive ) { g.setColor(Color.blue ); }
		g.fillOval(xab-gzover2,yab-gzover2, gzover2, gzover2 );
		
		// debugging 
		if ( theChippy.bug )
		{
   		if ( voltage >= 4 ) { g.setColor( Color.red ); }
   		else                { g.setColor( Color.black ); }
         g.drawLine( xab, yab, xab+1, yab-2 );
		}
	}
	
	public int getIndex() { return index; }
}