// Hole.java					Susan Hwang and Samah Kattan
//	 CSC 407 2005
// Edited by Samah and Emily   .... and  Barrett Koster 2005 ...

package Chippy;
import java.awt.*;
import java.io.*;

public class Hole extends Connectic implements Serializable
{
	//Pin myPin; // pin in hole if there is one, null otherwise
   static final int tol = 3; // pixel tollerance to find this hole with pin.
   Cluster clusty = null; // the cluster that this hole belongs to.
                   // null if there is none, like the battery.

   public Hole( Piece p, int xr, int yr )
   {
      super(p,xr,yr);
      //setXYab(); redundant, Connectic call this
   }

   // return true iff this Hole is pretty close to given xy
   public boolean zatyou( int x, int y )
   {
      return (   (xab-tol<x)
           &&(xab+tol>x)
           &&(yab-tol<y)
           &&(yab+tol>y)
         );
   }

	//--------------------------------------------------------------------------
   // sets and gets
	//--------------------------------------------------------------------------
	public void setX(int x) {xab = x;}
	public void setY(int y) {yab = y;}
	public int  getX() 	  	{return xab;}
	public int  getY() 		{return yab;}
	//public void setPin(Pin p) {myPin = p;}
	//public Pin  getPin() 	{return myPin;}
	public String getName() {return "Hole";}
	//public void   setVoltage(int v) {  lastVolt = voltage; voltage = v;}
	//public int    getVoltage()      {return voltage;}
   //public boolean getIdrive() { return idrive; }
	
   //public void setIdrive( boolean b ) { idrive = b; }
   // Note: we will eventually want to know when changes occur.

	
   public void draw(Graphics g )
	{
	   int gzover2 = CKTPanel.gz / 2;
	
		if (voltage == 2  )
		{g.setColor(Color.orange);}
		else
		{g.setColor(Color.gray);}
		g.drawOval( xab-gzover2, yab-gzover2, gzover2, gzover2 );
   }
}