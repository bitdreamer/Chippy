// PutWire.java
// makes a combobox that lets user add a wire to the circuit

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class PutWire extends Put
{
   JComboBox <plugs.Wire> cb; 
   
   // allowable colors of wires.  Note: must match code in made wire below
   private static String[] wireNames =   {"Choose a Wire", "black", "red"
                                   ,"yellow","green","blue","pink"
                                  }; 

   // constructor
   public PutWire()
   {
      cb = makeComboBox(wireNames); // and puts it in the panel
   }
   
   // return a Wire of this color
   public plugs.Wire makeWire( String colorName )
   {
      plugs.Wire w=null;
      
      if       ( colorName.equals("black" ) ) { w = new plugs.Wire( 200, 20, Color.black  ); }
      else  if ( colorName.equals("red"   ) ) { w = new plugs.Wire( 220, 20, Color.red    ); }
      else  if ( colorName.equals("yellow") ) { w = new plugs.Wire( 240, 20, Color.yellow ); }
      else  if ( colorName.equals("green" ) ) { w = new plugs.Wire( 260, 20, Color.green  ); }
      else  if ( colorName.equals("blue"  ) ) { w = new plugs.Wire( 280, 20, Color.blue   ); }
      else                                    { w = new plugs.Wire( 300, 20, Color.pink ); }
      
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
            plugs.Wire w = makeWire( colorName);
            theChippy.addToCktList(w); // also does repaint
      }
   }	
}
