// Board.java
//  Samah Kattan  2005 .. and Barry 2010

// fix? 2014 When you move a Board, it does not move the chips on it.  
// I suspect that it does not re-connect the pins of anything in the
// new location (so you can end up looking like the circuit is plugged in,
// but it's not -- bad.  Since this should be fixed anyway, I recommend that 
// when you move holes, anything plugged in should go with.  Wires stretch
// and I don't think anything else spans two board.  So this should be ok.

package plugs;

import java.awt.*;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

import Chippy.Cluster;
import Chippy.Hole;
import Chippy.Piece;

public class Board extends Piece implements Serializable
{
	private int clusterCount;   	// number of clusters
	private Color c = Color.white;	// default color for board
	
	//private Cluster[] clusterArray;  // all clusters (linked sets of Holes)
   LinkedList <Cluster> clusters; // the list of clusters on this Board
		
	public Board(int x, int y) 
	{
	   clusterCount = 52;
      xanchor = x; yanchor = y; name = "board";
      height = 300;
      width = 135;
	 
	  	//create an array of holes 	 
     	//clusterArray = new Cluster[clusterCount];
      clusters = new LinkedList();

      clusters.add( new Cluster( this, 10, 10, 12 ) );
      clusters.add( new Cluster( this, 10, 20, 12 ) );

      //create clusters for the main area
      int yy = 40;
	  	for( int n = 2; n < clusterCount; n+=2, yy+=10 )
		{
         clusters.add( new Cluster(this,10,yy,5) );
         clusters.add( new Cluster(this,80,yy,5) );
	  	} 	

      troll(); // position cluster relative to board (and thence the holes)
	}

   @Override
   public void report()
   {
      /*
      System.out.println("Board:report: ....");
      Iterator <Cluster> i = clusters.iterator();
      while ( i.hasNext() )
      {
         Cluster c2 = i.next();
         c2.report( );
      }
      */
   }
   
   public String saveMe()
   {
      return "board "+ xanchor +" "+yanchor+"\n";
   }

   @Override
	public boolean charge()
	{
      boolean somethingChanged = false;
      Iterator <Cluster> i = clusters.iterator();
      while ( i.hasNext() )
      {
         Cluster c2 = i.next();
         somethingChanged |= c2.charge( );
      }
      return somethingChanged;
	}

	//--------------------------------------------------------------------------
	// determines if x,y are within the bounds of this board
	//--------------------------------------------------------------------------
	public boolean contains(int x, int y) 
	{
		int x1 = getX();
	   int y1 = getY();
	   if (((x >= x1) && (x <= x1 + width)) && ((y >= y1) && (y <= y1 + height)))
	   {
			return true;
		}
	   else
	   {
			return false;
		}
	}

   // Troll usually drags the holes or pins with a piece.  Board
   // is different because it has clusters and those hold the
   // holes.  So we do troll here to drag the clusters, and then
   // they can do their own holes.
   @Override
   final public void troll()
   {
      // System.out.println("Board:troll xanchor="+xanchor+" =yanchor"+yanchor);
      Iterator <Cluster> i = clusters.iterator();
      while ( i.hasNext() )
      {
         Cluster c2 = i.next();
         c2.moveAndTroll( );
      }
   }

   // Find a hole on the board near the given xy.
   // Presumably someone is trying to stick a pin at this location.
   // Go through clusters and ask, to find the hole.
   @Override
	public Hole findAHole(int x, int y)
	{
		Hole h = null;	
      Iterator <Cluster> i = clusters.iterator();
      while ( h==null && i.hasNext() )
      {
         Cluster c2 = i.next();
         h = c2.findAHole(x,y );
      }

		return h;
	}

	@Override
   public void draw(Graphics g )
	{
		//draw the outline of the board
	   int x1 = super.getX();
	   int y1 = super.getY();
	   int x2 = x1 + width;
	   int y2 = y1 + height;
	  
	   g.setColor(Color.black);
      g.drawRect(  Math.min( x1, x2),
	       			 Math.min( y1, y2),
         		  	 Math.abs( x2- x1),
         			 Math.abs( y2- y1));  
						 	
		//reset position of holes
	   // resetHoles();
			
		//draw the holes

      Iterator <Cluster> i = clusters.iterator();
      while ( i.hasNext() )
      {
         Cluster c2 = i.next();
         c2.draw(g);
      }

		//reset color
		 g.setColor(Color.black); 	
	}
}