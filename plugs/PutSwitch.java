// PutSwitch.java
// makes a combobox that lets user add a switch to the circuit

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class PutSwitch extends Put
{
   JComboBox <String> cb; 
   
   // allowable colors of wires.  Note: must match code in made wire below
   private static String[] switchNames =   {"Choose a Switch", "push button", "toggle"  }; 

   // constructor
   public PutSwitch()
   {
      cb = makeComboBox(switchNames); // and puts it in the panel
   }
   
   // return a Wire of this color
   public Chippy.Piece makeSwitch( String typeName )
   {
      Chippy.Piece w=null;
      
      if       ( typeName.equals("push button" ) ) { w = new Chippy.PushButtonSwitch( 200, 100 ); }
      else  if ( typeName.equals("toggle"      ) ) { w = new Chippy.ToggleSwitch    ( 220, 100 ); }
      else                                         { w = new Chippy.ToggleSwitch    ( 220, 100 ); }
      
      return w;
   }
   
   // get the chip name from the combo box, make a new one of
   // that type, and add it to the circuit in Chippy
   @Override
   public void actionPerformed( ActionEvent e )
   {

      // if ( theChippy.numBoards >0 )
      {
            String colorName = (String) (cb.getSelectedItem());
            cb.setSelectedIndex(0);
            Chippy.Piece w = makeSwitch( colorName);
            theChippy.addToCktList(w); // also does repaint
      }
   }	
}
