// DoCharge.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DoCharge extends Put
{
   public DoCharge()
   {
      makeButton("charge");
   }
   
   // erase the board
   @Override
   public void actionPerformed( ActionEvent e )
   {
      theChippy.charge();
      theChippy.repaint();
   }	
}
