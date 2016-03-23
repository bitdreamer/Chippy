// PutWire.java
// makes a combobox that lets user add a wire to the circuit

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class PutWire extends Put
{
   JComboBox <Chippy.Wire> cb; 
   
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
   public Chippy.Wire makeWire( String colorName )
   {
      Chippy.Wire w=null;
      
      if       ( colorName.equals("black" ) ) { w = new Chippy.Wire( 200, 100, Color.black  ); }
      else  if ( colorName.equals("red"   ) ) { w = new Chippy.Wire( 220, 100, Color.red    ); }
      else  if ( colorName.equals("yellow") ) { w = new Chippy.Wire( 240, 100, Color.yellow ); }
      else  if ( colorName.equals("green" ) ) { w = new Chippy.Wire( 260, 100, Color.green  ); }
      else  if ( colorName.equals("blue"  ) ) { w = new Chippy.Wire( 280, 100, Color.blue   ); }
      else                                    { w = new Chippy.Wire( 300,100, Color.pink ); }
      
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
            Chippy.Wire w = makeWire( colorName);
            theChippy.addToCktList(w); // also does repaint
      }
   }	
}
