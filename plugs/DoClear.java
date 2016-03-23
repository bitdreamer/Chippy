// DoClear.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DoClear extends Put
{
   public DoClear()
   {
      makeButton("clear all");
   }
   
   // erase the board
   @Override
   public void actionPerformed( ActionEvent e )
   {
      theChippy.clearCktList( );
      theChippy.repaint();
   }	
}
