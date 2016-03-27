// DoSave.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class DoSave extends Put
{
   public DoSave()
   {
      makeButton("save");
   }
   
   // saves the circuit to a .chpy file (text).
   // FIX so far I think the user must type the ".chpy".  
   @Override
   public void actionPerformed( ActionEvent e )
   {
      JFileChooser fc;
      
      // create a file chooser for saving and loading files
	  fc = new JFileChooser();
      Chippy.ChippyFilter cf = new Chippy.ChippyFilter(); 
      fc.setFileFilter(cf);

      
      int result = fc.showSaveDialog(this); 
      
      if (result == JFileChooser.APPROVE_OPTION)
      {
        File file = fc.getSelectedFile();
        try
        {
           FileWriter fw = new FileWriter ( file );
          // ObjectOutputStream oos = new ObjectOutputStream ( fos );
           
           Iterator <Chippy.Piece> i = theChippy.getCktList().iterator();
           while ( i.hasNext() )
           {
              Chippy.Piece p = i.next();
              fw.write ( p.saveMe() );
           }
           fw.flush();
           fw.close();
        }
        catch ( Exception ee )
        {
           System.out.println( ee.toString() );
        }
      }
   }	
}
