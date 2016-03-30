
// Chippy.java
// This is the top file for Chippy, a TTL breadboard simulator.
/*
 * Created 2005 in Software Engineering course (Barrett Koster)
 * @author2 - Susan Hwang, Samah Kattan, Emily Mitchell,  

 *
 *
*/

package Chippy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;
import java.util.*;

import plugs.*;

public class Chippy extends JFrame implements ActionListener 
{
   boolean bug = true;
   private int numBoards = 0;             // number of boards - for now
   private LinkedList <Piece> cktList;            // the components ....boards
   private boolean itemSelected = false;// does a piece in the circuit window
                                      // have focus?
   private Piece selectedPiece;           // piece in focus, null=none
		  
   Controller boss;
    
   protected Doer theDoer;
	
   private JPanel controlies;
   private Insets inset;   			// this is the unusable area of the frame
  
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
      
      boss = new Controller( this );
		
		Container container = getContentPane();
			       
		cktList = new LinkedList<Piece>();

        //create panel to build circuit
	 	controlies = new JPanel();
	 	controlies.setPreferredSize( new Dimension(800,200) );
	 	controlies.setBackground( Color.white);

		setSize( 800,500 );	//size of frame
		setLocation( 10,10 );
      setVisible( true );
      
      inset = getInsets();
				      
      //restrict component to circuit panel
		controlies.
      addMouseListener( 
      new MouseAdapter()
		{
         // mouseClicked does nothing so far
         @Override
      	public void mouseClicked( MouseEvent mouse )
			{ 
				int x, y; 
				x = mouse.getX(); 
				y = mouse.getY();
            //System.out.println("mouse click at x="+x+" y="+y);
		 	}

         // select piece if there is one near
         @Override
		 	public void mousePressed( MouseEvent e ) 
		 	{ int curX, curY;
		   	curX = e.getX() + inset.left;
				curY = e.getY() + inset.top;
            doMousePressed( curX, curY );
	    	}	

         @Override
		 	public void mouseReleased(MouseEvent e) 
		 	{   int newX, newY;
		 		newX = e.getX() + inset.left;
				newY = e.getY() + inset.top;
				trash(newX, newY, selectedPiece);

            if ( itemSelected )
				{
				   selectedPiece.move( newX, newY );
               selectedPiece.dropIn();
					unSelectAll();  // probably unnecessary
					itemSelected = false;
					selectedPiece = null;
				}
            charge100();
            repaint();
			}
      });
		
		controlies.addMouseMotionListener(
	   new MouseMotionAdapter() 
		{
         @Override
			public void mouseDragged(MouseEvent e) 
			{   
                int newX, newY;
		   	    newX = e.getX() + inset.left;
			    newY = e.getY() + inset.top;
			  
			   if ( itemSelected ) 
				{
				   selectedPiece.move( newX, newY ); 
		   	}
   			repaint();
			}});
	} // end of Chippy constructor

   // Mouse was pressed at these coords, big window coords.
   // If there is a piece p close to the mouse coords ...
   // selectedPiece = p, p.selected=true, itemSelected=true
   public void doMousePressed( int x, int y )
   {
      Iterator <Piece> i = cktList.iterator();
      while ( i.hasNext() )
      {
         Piece p = i.next();
         if ( p.grab( x, y ))
         {
            p.setSelected( true );
            itemSelected = true;
            selectedPiece = p;
         }
         if ( p.press(x,y) ) // Also try to press this piece.
         {}
      }
   }
   
   // Mouse was released at these coords, big window coords.
   // If there is a piece p close to the mouse coords ...
   // selectedPiece = p, p.selected=true, itemSelected=true
   public void doMouseReleased( int x, int y )
   {
      Iterator <Piece> i = cktList.iterator();
      while ( i.hasNext() )
      {
         Piece p = i.next();
         // grabby stuff was handled before, could be moved to here 
         if ( p.release() ) // Also try to press this piece.
         {}
      }
   }
/*
   // return new button with label s1, add to controlies and add listener
   private JButton makeButton( String s1 )
   {
      JButton jb = new JButton(s1);
      controlies.add(jb);
      jb.addActionListener(this);
      return jb;
   }
   // same thing for combo boxes
   final public JComboBox makeComboBox( String[] choices )
   {
      JComboBox jcb = new JComboBox( choices );
      controlies.add(jcb);
      jcb.addActionListener(this);
      return jcb;
   }
 */ 
    @Override
   // actionPerformed.  dispatch for all the buttons
 	public void actionPerformed(ActionEvent e) 
	{
	/*
		try 
		{ 
			if 
			//(e.getSource() == aboutButton) { aboutBox();}
       //  else if 
         (e.getSource() == chargeOne) { charge(); / * repo(); * / }
		  	repaint();
		}
		catch (Exception exc) 
		{		
			JOptionPane.showMessageDialog (this,exc.toString(),"what did you do?!",
						                      JOptionPane.ERROR_MESSAGE);
		}
		*/
	}
	
   // This is a hack to do some debugging
   public void repo()
   {
       // set variable c to be the ChipFlop
       Chip74377 c=null;
       Iterator i = cktList.iterator();
       while ( i.hasNext() )
       {
           Object o1 = i.next();
           if ( o1 instanceof Chip74377 ) { c = (Chip74377) o1; }
       }
       
       // 
       c.report();
   }

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
   // fix: make this return true only if something changes
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
	   
	   // report();
	   
	   return somethingChanged;
	}

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
  /* 
   // aboutBox.  puts up a message about the program
   public void aboutBox()
   {
       JOptionPane.showMessageDialog(null,
                       "This program was written by Susan Hwang & " +
					        "Emily Mitchell \n as part of a Software " +
                       "Engineering class at Meredith College"
                       +"\n with professor Barrett Koster"
                                            );
   }
   */
	//-----------------------------------------------------------------
	// draws all the components on the component list
	//-----------------------------------------------------------------
   @Override
   public void paint (Graphics g) 
   {
	 	super.paint(g);
		drawTrash(g);
      
      g.setColor( Color.black );
      // g.fillRect( curX, curY, 3, 3 );
	        
      Iterator <Piece>  i = cktList.iterator();
	  while (i.hasNext()) 
	  {
         Piece p = i.next();
         p.draw(g);
	  }
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
   
   public LinkedList <Piece> getCktList() { return cktList; }
   
   public void addToCktList( Piece p ) { cktList.add(p); repaint(); }
   public void clearCktList() { cktList = new LinkedList <Piece> (); }
   public int incNumBoards() { numBoards++; return numBoards; }
   public Doer getTheDoer() { return theDoer; }
   public int getNumBoards() { return numBoards; }
   public boolean getBug() { return bug; }
}