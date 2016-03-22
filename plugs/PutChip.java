// PutChip.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class PutChip extends Put
{
   JComboBox <plugs.Chip> cb;
   
   public PutChip()
   {
      cb = makeComboBox(plugs.Chip.chipNames);
   }
   
   // get the chip name from the combo box, make a new one of
   // that type, and add it to the circuit in Chippy
   @Override
   public void actionPerformed( ActionEvent e )
   {

            //Board b = new Board( 590-numBoards*140,180 );
            plugs.Chip c;
            
                  String chipType = plugs.Chip.getChipName(cb.getSelectedIndex());
       c = plugs.Chip.makeChip( new StringTokenizer( chipType+" 150 100 "));
       theChippy.addToCktList( c );
      cb.setSelectedIndex(0);

       
   }	
}
