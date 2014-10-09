// Piece.java
// Piece.java	Susan Hwang and Samah Kattan
// Edited by Samah and Emily

package Chippy;

import java.awt.*;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class Piece implements Serializable
{
    protected String name; // what kind of piece it is
    protected int xanchor; // upper left of piece in pixels rel to big window
    protected int yanchor;
    protected int height;   //height of piece in pixels
    protected int width;   //width of piece in pixels
    protected LinkedList <Connectic> connectix; // list of pins or holes that go with this piece.
    protected boolean isSelected = false;   //does this piece have focus?
    protected int gridSize = 10; // what is this for?

    public Piece()
    {
      name = "thingy";
		xanchor = 130;
		yanchor = 130;
		height = 20;
		width = 20;
      connectix = new LinkedList();
	}

   public void report()
   {
      System.out.println("---- piece "+name+" at "+xanchor+" "+yanchor);
      Iterator <Connectic> i = connectix.iterator();
      while ( i.hasNext() )
      {
         Connectic c = i.next();
         c.report();
      }
   }

   // Aligns all the pins or holes absolute positions to this piece.
   // Used when moving the piece.
   public void troll()
   {
      Iterator <Connectic> i = connectix.iterator();
      while ( i.hasNext() )
      {
         Connectic c = i.next();
         c.setXYab();
      }
   }

   // adjust an integer to grid
	final public int gridify(int i)
	{
	  	//float temp = i/gridSize;
	  	//return Math.round(temp)* gridSize;
      return (i/gridSize)*gridSize;
	}
	
   // move.  set coordintes to newX, newY .... adjusted to grid
	public void move(int newX, int newY)
	{
		xanchor = gridify(newX);
		yanchor = gridify(newY);
      troll();
	}
   
   // grab.  true iff the given xy is near the grab point for this piece
   // grab point is upper left corner of piece, xCoordinate,yCoordinate
   public boolean grab( int x, int y )
   {
       boolean ret = false;
       
		int tolerance = 5;
	   if (    (x >= xanchor - tolerance)
           && (x <= xanchor + tolerance)
           && (y >= yanchor - tolerance)
           && (y <= yanchor + tolerance)
         )
	   { ret = true;}

      return ret;
   }
   
   // This piece just arrived at this location.  Disconnect old associations
   // and establish new associations, i.e., pins in holes.
   // Note: this presently only works right with pins  moved to holes.
   // It does not work if holes are moved under pins.
   public void dropIn()
   {
      Iterator <Connectic> i = connectix.iterator();
      while ( i.hasNext() )
      {
         Connectic c = i.next();
         c.unplug();
      }
      i = connectix.iterator();
      while ( i.hasNext() )
      {
         Connectic c = i.next();
         if ( c instanceof Pin )
         { Pin p = (Pin) c; p.findAHole(); }
      }
   }

   // set all pins/holes so that they say they are not driving.
   // (use this when chip is not powered)
   public void noDrive()
   {
      Iterator <Connectic> i = connectix.iterator();
      while ( i.hasNext() )
      {
         Connectic c = i.next();
         c.idrive=false;
      }
   }
   
   // return true iff coords are within the tolerance of the anchor
   public boolean contains(int x, int y)
   {
      int x1 = getX();
      int y1 = getY();
      int tolerance = 50;
      if (((x >= x1- tolerance) && (x <= x1+ tolerance)) && 
          ((y >= y1-tolerance) && (y <= y1 + tolerance)))
      {return true;}
      else
      {return false;}
   }
   
   // do what needs to be done if these coords are near press point for this piece
   // return value says if there was change
   public boolean press( int x1, int y1 ) { return false; }
   public boolean release( ) { return false; }

   public String saveMe()
   {
      return "Error: some Piece doesn't have a saveMe() function.";
   }

	//--------------------------------------------------------------------------
   // gets and sets
	//--------------------------------------------------------------------------
   public int getX() {return xanchor;}
   public int getY() {return yanchor;}
	//public void setName (String n) {name = n;}
	//public String getName() {return name;}
	public void setSelected(boolean b) {isSelected = b;}
	public boolean getSelected() {return isSelected;}

	// charge() recomputes the state of this piece if 'needs' says it needs to.
	// (may be pin-wise or as a whole)
   // It looks at all of the connections this piece has (for input),
   // figures out what this piece's state is, sets the voltage and
   // idrive states of all the pins.
   // Should report true if there are any changes to the state.
	public abstract boolean charge();// { return true; }
   public boolean chargeb() { return false; }

	public void setVoltage(int v){};
   public void setHeight(int h){height = h;}
	public int getHeight() 		 {return height;}
	public void setWidth(int w) {width = w;};
	public int getWidth() 		 {return width;}

	
	//public abstract boolean contains(int x, int y);
	
	//public abstract boolean hasXY(int x, int y);
   public void draw(Graphics g ){}	
	public void locatePins(Piece p){}	
	public void locateHoles(Piece p){}

   // Return a Hole if this piece has one near given x y.
   // Most pieces have no Holes at all, so default is to return null.
	public Hole findAHole(int x, int y) {return null;}

   public static void main(String args[])
	{
		Chippy ch = new Chippy();
  	}

} 