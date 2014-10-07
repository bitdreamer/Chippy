// RectangularPiece.java

package Chippy;
//===================================
// Edited Samah and Emily
//===================================

//import javax.swing.*;
//import java.awt.Graphics;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.*;
import java.io.*;

public abstract class RectangularPiece extends Piece implements Serializable
{
	//protected int height;
  	//protected int width;

   /*
  	public RectangularPiece() 
  	{
	 	height = 0;
	 	width =0;
  	}
	*/
  	//--------------------------------------------------------------------------
  	// constructor setting x,y only
  	//--------------------------------------------------------------------------
  	public RectangularPiece(int x, int y) 
  	{
      xanchor = x; yanchor = y; name = "rp";
    	//super(x,y);
	 	//height = 0;
	 	//width = 0;
	}

 	//--------------------------------------------------------------------------
 	// constructor
  	//--------------------------------------------------------------------------
   public RectangularPiece(int x, int y, int h, int w) 
	{
      xanchor = x; yanchor = y; name = "rp";
    	//super(x,y);
	 	height = h;
	 	width = w;
   }

   public RectangularPiece() {}

	//--------------------------------------------------------------------------
	// accessor and mutator methods for private variables
	//--------------------------------------------------------------------------
//	public void setHeight(int h){height = h;}
//	public int getHeight() 		 {return height;}
//	public void setWidth(int w) {width = w;};
//	public int getWidth() 		 {return width;}


   /*
    * //--------------------------------------------------------------------------
	// determines if x,y are within the bounds of this shape
	//--------------------------------------------------------------------------
	public boolean contains(int x, int y) 
	{
		int x1 = getX();
	  	int y1 = getY();
	  	if (((x >= x1) && (x <= x1 + width)) || 
			 ((y >= y1) && (y <= y1 + height)))
	   {return true;}
	  	else
	   {return false;}
   }
*/
	//--------------------------------------------------------------------------
	// determines if x,y are the same as x,y for this piece
	//--------------------------------------------------------------------------
	public boolean hasXY(int x, int y) 
	{
		int x1 = getX();
	  	int y1 = getY();
	  	if ((x1 == x)  || (y1 == y))
	   {
			return true;
		}
	   else 
		{
			return false;
		}
	}   
}