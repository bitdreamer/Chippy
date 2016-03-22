// Cluster.java	2005 CSC-407 class (Software Engineering)
// Samah Kattan , also Emily and Barry

// Cluster is a group of holes that are electrically connected.

package Chippy;

import java.awt.*;
import java.io.*;

import plugs.Board;

public class Cluster extends Piece implements Serializable
{
   protected Board myBoard; // have to be in a Board
   protected int xrel, yrel;
	private Hole holes[]; // the Holes in this cluster of Holes

	private int numHoles;
	private int voltage = 3;

   // constructor setting relative coordinates and number of holes
	public Cluster( Board b, int x, int y, int n )
	{
      myBoard = b;
      xrel = x;
      yrel = y;
      xanchor = myBoard.xanchor + xrel;
      yanchor = myBoard.yanchor + yrel;
		numHoles = n;
		holes = new Hole[numHoles];

		int inc = 0;
		for(int i = 0; i<numHoles;i++) 
		{
			holes[i] = new Hole(this,inc,0);
         connectix.add(holes[i]);
		 	inc +=10;
	   }
 	}

   @Override
   public void report()
   {
      System.out.println("Cluster.report: xrel="+xrel+" yrel="+yrel);
      boolean active = false;
		for(int i = 0; i<numHoles; i++)
		{
         if ( holes[i].buddy  != null )
         { active = true; }
	   }

      if (active)
      {
         System.out.println("   cluster ...");
         for(int i = 0; i<numHoles; i++)
         {
            holes[i].report();
         }
      }
   }

   public void moveAndTroll()
   {
      //System.out.println("   Cluster:moveAndTroll: ");
      xanchor = myBoard.xanchor + xrel;
      yanchor = myBoard.yanchor + yrel;
      troll();
   }

	//--------------------------------------------------------------------------
   // set x coordinate relative to calling object
	//--------------------------------------------------------------------------
	public void setX(int x) 
	{
		int inc = 0;
		for(int i = 0; i<numHoles;i++) 
		{
			holes[i].setX(x + inc);
		   inc +=10;
	   }
	}
  
	//--------------------------------------------------------------------------
   // set y coordinate relative to calling object
	//--------------------------------------------------------------------------
  	public void setY(int y) 
	{
	  	for(int i = 0; i<numHoles;i++) 
		{
			holes[i].setY(y);
		}
   }
	
  	public int getNumHoles() { return numHoles; }
	
	// return the ith Hole of this Cluster --------------------------------------------------------------------------
  	public Hole getHole(int i)	{ return holes[i]; }
   
	//--------------------------------------------------------------------------
	// goes through holes to see if  a hole with the x and y
	// values, if so it returns that hole to be linked to the pin with the same
	// x and y values.	
	//--------------------------------------------------------------------------	
	@Override
   public Hole findAHole(int x, int y)
	{
		Hole h = null;
		for(int z = 0; z < numHoles; z++)
		{
			int u1 = holes[z].getX();
			int u2 = holes[z].getY();
			if (u1 == x && u2 == y)
			{
				h = holes[z];
				return h;
			}
		}
		return h;
	}
	
	/*
	public void setVoltage(int v)
	{
		for(int i=0; i<numHoles; i++)
		{
			voltage = v;
			holes[i].setVoltage(voltage);
		}
	}
	*/

   // This is very similar to a Wire, except pins and holes have opposite
   // roles.
   // Look at inputs from pins and figure out output state of holes.
   // If there are two or more drivers, voltage = 2 (fire).
   // Else, propagate whatever is driven from it to the others.
   // If no drivers, float.
   @Override
	public boolean charge()
	{
      boolean changed = false;
      boolean anyNeeds = false;
      
      // count drivers, set theDriver to i if the ith one is driving
      int driverCount = 0;
      int theDriver = -1;
		for(int i=0; i<numHoles; i++)
      {
		   Hole h = holes[i];
		   anyNeeds |= h.needs;
         Pin p = (Pin) (h.getBuddy());
         if ( p!=null )
         {
            if ( p.getIdrive() )
            {
               driverCount++;
               theDriver = i;
            }
         }
      }

      if ( driverCount > 1 ) // fire, set all holes to fire state
      {
         for(int i=0; i<numHoles; i++)
         {
            Hole h = holes[i];
            changed |= h.setVoltage(2);
            changed |= h.setIdrive(false);
         }
      }
      else if ( driverCount==1 )
      {
         int v = holes[theDriver].getBuddy().getVoltage();
         for(int i=0; i<numHoles; i++)
         {
            Hole h = holes[i];
            changed |= h.setVoltage(v);
            changed |= h.setIdrive(true); // all holes propagate driven voltage ....
         }
         holes[theDriver].setIdrive(false); // except the one for driver pin
      }
      else // if ( driverCount==0 )
      {
         // no inputs, make it all float
         for(int i=0; i<numHoles; i++)
         {
            Hole h = holes[i];
            changed |= h.setVoltage(3);
            changed |= h.setIdrive(false);
            
         }
      }

      return true;
	}
	
	
	// draws the cluster, bascially the hole in it
	@Override
   public void draw(Graphics g )
	{
		for(int i = 0; i<numHoles;i++) 
		{
			holes[i].draw(g);
		}
   }


   // These are to make the Piece inheritance work.  We shouldn't
   // even ever really call them.
   @Override
   public boolean contains(int x, int y) { return false; }
   //@Override
	//public boolean hasXY(int x, int y) { return false; }

   public static void main(String args[])
	{
		Chippy ch = new Chippy();
  	}

}