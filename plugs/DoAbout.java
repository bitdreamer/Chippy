// DoAbout.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DoAbout extends Put
{
   public DoAbout()
   {
      makeButton("about");
   }
   
   // erase the board
   @Override
   public void actionPerformed( ActionEvent e )
   {
       JOptionPane.showMessageDialog(null,
                       "This program was written by Susan Hwang & " +
					        "Emily Mitchell \n as part of a Software " +
                       "Engineering class at Meredith College"
                       +"\n with professor Barrett Koster"
                                            );

      theChippy.repaint();
   }	
}
