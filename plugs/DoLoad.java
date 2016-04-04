// DoLoad.java

/*
OK, so.  The load has errors.  Batteries, boards, wires ... many are
out of place.  Battery a lot, the rest just a little.  My guess is 
that some sort of the input-from-string contructors don't have the
'bigger' fix that I just did.  So stuff gets rounded to the old grid
and is off by one (which is what I saw, except the battery, which
has no parameters, duh, needs fixing).  The makeWhatever() functions
seem to be key ... in at least some cases, when we press the button,
we specify it as a string and then use the string-parser to do it.
Well, you will have to check them type by type.  Not that many.

In other news ... once the chips get on the board, there's a whole
problem of possibly putting two wires in one hole?  And the problem
with that is, even after the user puts it right, I'm not sure the
software ever recovers.  We need a reset button, somehow.  
In particular we need to check or re-do all of the dropIn 
things, make sure everyone has the right buddies.  This will 
take some care.

*/

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class DoLoad extends Put
{
   public DoLoad()
   {
      makeButton("load");
   }
   
     	// this is for loading from a text file.
	// It asks the user to pick a file, then loads it with 
	// commands to record all of the Pieces.
   @Override
   public void actionPerformed( ActionEvent e )
   {
      JFileChooser fc;
      
      // create a file chooser for saving and loading files
	  fc = new JFileChooser();
      Chippy.ChippyFilter cf = new Chippy.ChippyFilter(); 
      fc.setFileFilter(cf);

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
                    catch (EOFException ee) { done = true; } // doesn't work
                    catch (IOException ee) 
                    { System.out.println("Cmd.cmd: read error="+ee); done = true; }
                    
                    boolean bug = theChippy.getBug();
                    if (bug)
                    {
                       System.out.println("DoLoad:actionPerformed: line="+line);
                    }
                    
                    // detect end of file (this one works)
                    if ( line ==null ) { done = true; }
                    
                    if ( !done )
                    {
                        theChippy.getTheDoer().doCom( line );
                    }
                    if (bug)
                    {
                       System.out.println("    after processing numBoards="+theChippy.getNumBoards());
                    }
                }
            }
         }
      }
	   catch (Exception ee ) { System.out.println( ee.toString() );}
	   //theChippy.countBoards();
	   
	   if (theChippy.getBug() )
	   { System.out.println("DoLoad: numBoards="+theChippy.getNumBoards());}
	   theChippy.repaint();
   }	
}
