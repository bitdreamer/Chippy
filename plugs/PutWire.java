// PutWire.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class PutWire extends Put
{
   JComboBox <Chippy.Wire> cb;
   
   public PutWire()
   {
      cb = makeComboBox(Chippy.Wire.getWireNames());
   }
   
   // get the chip name from the combo box, make a new one of
   // that type, and add it to the circuit in Chippy
   @Override
   public void actionPerformed( ActionEvent e )
   {

       // if ( theChippy.numBoards >0 )
        {
            Color c = Chippy.Wire.whatColor(  (String)(cb.getSelectedItem()) );
            cb.setSelectedIndex(0);
            Chippy.Wire w = new Chippy.Wire( 190, 90, c );
            theChippy.addToCktList(w);
        }

   }	
}
