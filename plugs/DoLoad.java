// DoLoad.java

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
