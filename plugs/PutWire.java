// PutWire.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class PutWire extends Put
{
   JComboBox <Chippy.Wire> cb;
   
   private static String[] wireNames =   {"Choose a Wire", "black", "red"
                                   ,"yellow","green","blue"
                                  }; 

   
   public PutWire()
   {
      cb = makeComboBox(wireNames);
      Chippy.Wire.getChoiceBox();
   }
   
   // return a Wire of this color
   public Chippy.Wire makeWire( String colorName )
   {
      Chippy.Wire w=null;
      
      if       ( colorName.equals("black" ) ) { w = new Chippy.Wire( 200, 100, Color.black  ); }
      else  if ( colorName.equals("red"   ) ) { w = new Chippy.Wire( 200, 100, Color.red    ); }
      else  if ( colorName.equals("yellow") ) { w = new Chippy.Wire( 200, 100, Color.yellow ); }
      else  if ( colorName.equals("green" ) ) { w = new Chippy.Wire( 200, 100, Color.green  ); }
      else  if ( colorName.equals("blue"  ) ) { w = new Chippy.Wire( 200, 100, Color.blue   ); }
      else { w = new Chippy.Wire(200,100, Color.pink ); }
      
      return w;
   }
   
   // get the chip name from the combo box, make a new one of
   // that type, and add it to the circuit in Chippy
   @Override
   public void actionPerformed( ActionEvent e )
   {

       // if ( theChippy.numBoards >0 )
        {
            // Color c = Chippy.Wire.whatColor(  (String)(cb.getSelectedItem()) );
            String colorName = (String) (cb.getSelectedItem());
            cb.setSelectedIndex(0);
            //Chippy.Wire w = new Chippy.Wire( 190, 90, c );
            Chippy.Wire w = makeWire( colorName);
            theChippy.addToCktList(w);
        }

   }	
}
