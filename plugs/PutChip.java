// PutChip.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class PutChip extends Put
{
   JComboBox <Chippy.Chip> cb;
   
   public PutChip()
   {
      cb = makeComboBox(Chippy.Chip.chipNames);
   }
   
   // get the chip name from the combo box, make a new one of
   // that type, and add it to the circuit in Chippy
   @Override
   public void actionPerformed( ActionEvent e )
   {

            //Board b = new Board( 590-numBoards*140,180 );
            Chippy.Chip c;
            
                  String chipType = Chippy.Chip.getChipName(cb.getSelectedIndex());
       c = Chippy.Chip.makeChip( new StringTokenizer( chipType+" 150 100 "));
       theChippy.addToCktList( c );
      cb.setSelectedIndex(0);

       
   }	
}
