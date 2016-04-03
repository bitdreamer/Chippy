// CKTPanel.java
// Barrett Koster
// 2016 ... cleaning up 
// This is the whole circuit display, holds all of the pieces.


package Chippy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CKTPanel extends JPanel implements MouseListener, MouseMotionListener
{
   boolean bug = true; // turn on for debugging
   Chippy theChippy; // pointer to the main program
   static int gz=20;  // grid size (this is the one you set by hand)
   LinkedList <Piece> cktList;  // all the parts
   private Piece selectedPiece = null;// piece in focus, null=none

   
   public CKTPanel( Chippy ch)
   {
      theChippy = ch;
      Piece.gridSize = gz;
      
      cktList = new LinkedList <Piece> ();
      setBackground( new Color( 255, 235, 215  ) );
      
      addMouseListener(this);
      addMouseMotionListener(this);
   }
   
   // mouse stuff.  Be able to drag stuff around.
   // Trash or dropIn when you release it.  
   public void mouseClicked ( MouseEvent m ) {}
   
   // fix - only select one piece, the first one
   public void mousePressed ( MouseEvent m )
   {
      int x = m.getX(); int y = m.getY();
      Iterator <Piece> i = cktList.iterator();
      while ( i.hasNext() )
      {
         Piece p = i.next();
         if ( p.grab( x, y ))
         {
            p.setSelected( true );
            //itemSelected = true;
            selectedPiece = p;
            //if (bug) { System.out.println(" piece found "+p); }
         }
         if ( p.press(x,y) ) // Also try to press this piece.
         { theChippy.charge100(); }
      }
      repaint();
   }
   public void mouseDragged( MouseEvent m )
   { 
      if ( selectedPiece!=null )
      {
         int x = m.getX();  int y = m.getY();
         selectedPiece.move( x, y );
         repaint();
      }
   }
   
   public void mouseReleased( MouseEvent m )
   {
      if ( selectedPiece!=null )
      {
         int x = m.getX();  int y = m.getY();
         theChippy.trash( x, y, selectedPiece );
         selectedPiece.move( x, y );
         selectedPiece.dropIn();
         selectedPiece = null;
         theChippy.charge100(); // this needs a look
         repaint();
      }
   }
   public void mouseEntered ( MouseEvent m ) {}
   public void mouseExited  ( MouseEvent m ) {}
   
   public void mouseMoved( MouseEvent m ) {}
   
      // tell whatever you can about the circuit
   public void report()
   {
		Iterator <Piece> i = cktList.iterator();
	   while (i.hasNext())
		{
			Piece p =  i.next();
			p.report();
		}
   }

   
   public void paint( Graphics g )
   {
      super.paint(g);
      
            Iterator <Piece>  i = cktList.iterator();
	  while (i.hasNext()) 
	  {
         Piece p = i.next();
         p.draw(g);
	  }
	  
	  // fix - this needs to be not so hard wired ...
      g.drawRect(50, 60, 50, 50);
      g.drawString("TRASH", 52, 75);
   }
   
   // access
   public LinkedList <Piece> getCktList() { return cktList; }
}
