
// Chippy.java
// This is the top file for Chippy.
/*
 * Created 2005 in Software Engineering course (Barrett Koster)
 * @author - Susan Hwang and Samah Kattan
   Edited by Emily and Samah (and Barry)
 *
 * This program needs to be tested.  It also needs a more chips.
 * In particular we need a chip that is an oscillator, generates
 * pulses.  I hope there is such a chip.
 * 
 * 2014 Chippy being revised (Barrett Koster) to have charging work
 * and lots of other things.
 *
*/

package Chippy;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.Graphics;
import java.util.*;
import java.io.*;

public class Chippy extends JFrame implements ActionListener 
{
   boolean bug = true;
   private int numBoards = 0;             // number of boards - for now
   private LinkedList <Piece> cktList;            // the components ....boards
   private boolean itemSelected = false;// does a piece in the circuit window
                                      // have focus?
   private Piece selectedPiece;           // piece in focus, null=none
   private String[] wireNames =   {"Choose a Wire", "Black", "Red"
                                   ,"yellow","green","blue"
                                  }; 
   private String[] switchNames = {"Choose a Switch", "Toggle", "Push Button"};
	final JFileChooser fc;
		  
   Controller boss;

	// GUI elements
   //private JButton boardButton;	 	// displays a board
   //private JButton batteryButton;	// diplays a battery
	private JButton removeButton;   	// clear all the object on the circuit
	private JButton saveButton;	   // saves the objects displayed on the circuit
	private JButton loadButton;	   // loads the saved objects from a file
   //private JComboBox<Chip> chips;      // user selects which chip (type)
   private JComboBox wires;      // selects which wire the user wants
   private JComboBox switches;   // selects which switch the user wants
   private JComboBox lights;     // selects which light the user wants
	private JButton aboutButton;     // about the program
   private JButton chargeOne;       // call charge just once
	   
    private BufferedReader bReader;
    private PrintWriter pWriter;
    
    protected Doer theDoer;
	
	private JPanel controlies;
	private Insets inset;   			// this is the unusable area of the frame
  
   private int wireColor;
	private int lightColor;
	
   public static void main(String args[]) 
   {
      Chippy ch = new Chippy();
   } 

	
	// Creates a new instance of Chippy 
   public Chippy() 
	{
 		super("Chippy");
 		
 		plugs.Put.setTheChippy(this);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
		Connectic.setTheChippy(this);
      Wire.setTheChippy(this);
      theDoer = new Doer(this);
      
      boss = new Controller( this );
		
		Container container = getContentPane();
			       
		cktList = new LinkedList<Piece>();
		
		// create a file chooser for saving and loading files
		fc = new JFileChooser();
		ChippyFilter cf = new ChippyFilter(); 
		fc.setFileFilter(cf);
		
		//int xq86oo4 = 354; // junk statment

        //create panel to build circuit
	 	controlies = new JPanel();
	 	controlies.setPreferredSize( new Dimension(800,200) );
	   //controlies.setBackground( new Color(200,230,240) );
	 	controlies.setBackground( Color.white);
	 	// This panel is actually taking over the whole screen.  We just
	 	// want it to be the place where the buttons are.  Fix it.

      //boardButton = makeButton("New Board");
      //batteryButton = makeButton("Battery");
		saveButton 	  = makeButton("Save"); //new JButton   ("Save");
		removeButton  = new JButton   ("Clear All");
		loadButton    = new JButton   ("Load");
      //chips = makeComboBox( Chip.chipNames );
		//wires         = new JComboBox (wireNames);  //wires.
      //wires = Wire.getChoiceBox();
		switches      = new JComboBox (switchNames);
      lights = makeComboBox( Light.lightNames );
		aboutButton   = new JButton   ("About");
		chargeOne     = new JButton   ("charge 1");

      //fix - anything left below needs to be converted to the
      // way we do Chip and Light, or boardButton and batteryButton.
      // And then it will disappear from this section.
//		controlies.add( wires );
      controlies.add( wires = (Wire.getChoiceBox()) );
		controlies.add( switches );
	   controlies.add( removeButton );
	   controlies.add( loadButton );
		controlies.add( aboutButton );
      controlies.add( chargeOne );
	
		container.add( controlies );
		
		//=====================================================================
	   // add listeners to buttons and ComboBoxes so they'll do something
      // fix: this list will disappear when all are done like Chip.
		//=====================================================================
		removeButton.addActionListener ( this );
		loadButton.addActionListener   ( this );	
		wires.addActionListener        ( this );
		switches.addActionListener     ( this );
		aboutButton.addActionListener  ( this );
      chargeOne.addActionListener    ( this );
			            
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
  
    @Override
   // actionPerformed.  dispatch for all the buttons
 	public void actionPerformed(ActionEvent e) 
	{
		try 
		{ /*
		    if (e.getSource() == chips)         { makeChip(); }
			else 
			*/
			if (e.getSource() == wires)         { makeWire(); }
			else if (e.getSource() == lights) 
               {makeLight(lights.getSelectedIndex());}
			else if (e.getSource() == switches) { makeSwitch(); }
			else if (e.getSource() == removeButton) {removeAllParts();}
			else if (e.getSource() == saveButton) {save();}
			else if (e.getSource() == loadButton) {load();}
			else if (e.getSource() == aboutButton) { aboutBox();}
         else if (e.getSource() == chargeOne) { charge(); /*repo();*/ }
		  	repaint();
		}
		catch (Exception exc) 
		{		
			JOptionPane.showMessageDialog (this,exc.toString(),"what did you do?!",
						                      JOptionPane.ERROR_MESSAGE);
		}
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


   // saves the circuit to a .chpy file (text).
   // FIX so far I think the user must type the ".chpy".  
   public void save()
   {
      int result = fc.showSaveDialog(this); 
      
      if (result == JFileChooser.APPROVE_OPTION)
      {
        File file = fc.getSelectedFile();
        try
        {
           FileWriter fw = new FileWriter ( file );
          // ObjectOutputStream oos = new ObjectOutputStream ( fos );
           
           Iterator i = cktList.iterator();
           while ( i.hasNext() )
           {
              Piece p = ( Piece ) i.next();
              fw.write ( p.saveMe() );
           }
           fw.flush();
           fw.close();
        }
        catch ( Exception e )
        {
           System.out.println( e.toString() );
        }
      }
   }
   
	// this is for loading from a text file.
	// It asks the user to pick a file, then loads it with 
	// commands to record all of the Pieces.
	public void load()
	{
	   try
	   {
         int result = fc.showOpenDialog(this); // this is where the user picks the file
      
         if (result == JFileChooser.APPROVE_OPTION)
         {
            File file = fc.getSelectedFile(); // ok, so what was that file?
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader( fr );
            
            if ( bfr != null )
            {
                String line;
                boolean done=false;
                while (!done)
                {
                    line = null;
                    try{ line = bfr.readLine(); }
                    catch (EOFException e) { done = true; } // doesn't work
                    catch (IOException e) 
                    { System.out.println("Cmd.cmd: read error="+e); done = true; }
                    
                    // detect end of file (this one works)
                    if ( line ==null ) { done = true; }
                    
                    if ( !done )
                    {
                        theDoer.doCom( line );
                    }
                }
            }

         }
	   }
	   catch (Exception e ) { System.out.println( e.toString() );}
	   countBoards();
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
	
	// count the boards in the list and reset numBoards var
	public void countBoards()
	{
	   numBoards = 0;  //reset
		Iterator i = cktList.iterator();
	   while (i.hasNext())
		{
			Piece p = (Piece) i.next();
		 	//if(p.getName().equals("boards"))
         if ( p instanceof Board )
         { numBoards++; }
		}
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
/*
   public void makeChip()
   {
      String chipType = Chip.getChipName(chips.getSelectedIndex());
      if (bug) { System.out.println("Chippy.makeChip: about to make type "+chipType ); }
      Chip c = Chip.makeChip( new StringTokenizer( chipType+" 150 100 "));
      cktList.add(c);
      chips.setSelectedIndex(0);
   }
 */  
	public void makeLight (int color)
   {
      cktList.add( Light.makeLight(lights.getSelectedIndex()));
      lights.setSelectedIndex(0);
	}
  
    // makeWire.  creates new wire (if there's a board to put it on)
    // and adds it to the cktList.  Pulls color from menu and 
    // resets it menu.
    public void makeWire()
    {
        if (numBoards >0 )
        {
            //int colorNum = wires.getSelectedIndex();
           Color c = Wire.whatColor(  (String)(wires.getSelectedItem()) );
            wires.setSelectedIndex(0);
            // Wire w = new Wire( 190, 90, colorNum );
            Wire w = new Wire( 190, 90, c );
            cktList.add(w);
        }
    }
	
	// makes a switch (if there is a board to put it on)
    public void makeSwitch()
    {
        if ( numBoards>0 )
        {
            int swtype = switches.getSelectedIndex();
            switches.setSelectedIndex(0);
            Piece s;
            if      ( swtype==1 ) { s = new ToggleSwitch(190,90); }
            else                  { s = new PushButtonSwitch( 190,90); }
            cktList.add(s);
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


	//-----------------------------------------------------------------
	// removes all components on the screen
	//-----------------------------------------------------------------
	public void removeAllParts()
	{
		cktList.clear();
		repaint();
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
   
   public LinkedList <Piece> getCktList() { return cktList; }
   
   public void addToCktList( Piece p ) { cktList.add(p); repaint(); }
}