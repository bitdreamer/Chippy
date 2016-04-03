
// Chippy.java
// This is the top file for Chippy, a TTL breadboard simulator.
/*
 * Created 2005 in Software Engineering course (Barrett Koster)
 * @author2 - Susan Hwang, Samah Kattan, Emily Mitchell,  
*/

package Chippy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;
import java.util.*;

import plugs.*;

public class Chippy extends JFrame //implements ActionListener 
{
   boolean bug = true;
   private int numBoards = 0;             // number of boards - for now
   private LinkedList <Piece> cktList;            // the components ....boards
   private boolean itemSelected = false;// does a piece in the circuit window
                                      // have focus?
   private Piece selectedPiece;           // piece in focus, null=none
		  
   Controller boss; // has all the buttons to add components to the circuit
   CKTPanel theCP; // where all of the parts go, the circuit
    
   protected Doer theDoer;
	
   private JPanel controlies;
   //private Insets inset;   			// this is the unusable area of the frame
  
   public static void main(String args[]) 
   {
      Chippy ch = new Chippy();
   } 
	
	// Creates a new instance of Chippy 
   public Chippy() 
   {
      super("Chippy");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      
      // inform some classes where the main program is 		
      plugs.Put.setTheChippy(this);
      Connectic.setTheChippy(this);
      Wire.setTheChippy(this);
      
      theDoer = new Doer(this);
      
      theCP = new CKTPanel( this );
      add( theCP );
      cktList = theCP.getCktList();

      boss = new Controller( this );

      setSize( 900,900 );	//size of frame
	  setLocation( 10,10 );
      setVisible( true );		
   } // end of Chippy constructor


   // findAHole. returns Hole at given xy
   // how: goes through list of components, asks each if it has a hole at xy
	public Hole findAHole (int x, int y)
	{
		Hole h = null;
		Iterator i = cktList.iterator();
	   while ( (h==null) && (i.hasNext()) ) 
		{
      	Piece p = (Piece)i.next();
         h = p.findAHole (x, y);
		}
	 	return h;
	}
	
   // call charge 100 times or until nothing changes
   public void charge100()
   {
      int i=0;
      while ( (++i)<100 && charge() )
      {}
   }

	// call charge() on all pieces in cktList
   public boolean charge()
   {
      //if (bug) { System.out.println("Chippy.charge: entering ...");}
      boolean somethingChanged = false;
      Iterator <Piece> i = cktList.iterator();
	  while (i.hasNext())
      {
			Piece p = i.next();
			//if (bug) { System.out.println("   about to call charge on piece "+p.name ); }
			boolean ch = p.charge();
			somethingChanged = somethingChanged || ch;
      }
	   
	   return somethingChanged;
	}
/*
   // tell whatever you can about the circuit
   public void report()
   {
		Iterator i = cktList.iterator();
	   while (i.hasNext())
		{
			Piece p = (Piece) i.next();
			p.report();
		}
   }
*/
	// unselect all parts
   public void unSelectAll()
	{
		Iterator i = cktList.iterator();
	   while (i.hasNext())
		{
			Piece p = (Piece) i.next();
		 	p.setSelected(false);
		}

	}

	
	public void trash(int x, int y, Piece p)
	{
		if ((x >= 50 && x <= 100) &&
		    (y >= 60 && y <= 110) &&
          (p!=null)
         )
		{
			cktList.remove(p);
         System.out.println("trash needs some code to clean up selection");
		}
	}
	
	public void drawTrash(Graphics g)
	{
		g.drawRect(50, 60, 50, 50);
		g.drawString("TRASH", 52, 75);
	}

	 
   
   	// count the boards in the list and reset numBoards var
	public void countBoards()
	{
	   numBoards = 0;  //reset
		Iterator <Piece> i = cktList.iterator();
	   while (i.hasNext())
		{
			Piece p =   i.next();
		 	//if(p.getName().equals("boards"))
         if ( p instanceof Board )
         { numBoards++; }
		}
	}
   
   // access
   public LinkedList <Piece> getCktList() { return cktList; }
   public void addToCktList( Piece p ) { cktList.add(p); repaint(); }
   public void clearCktList() { cktList = new LinkedList <Piece> (); }
   public int incNumBoards() { numBoards++; return numBoards; }
   public Doer getTheDoer() { return theDoer; }
   public int getNumBoards() { return numBoards; }
   public boolean getBug() { return bug; }
}