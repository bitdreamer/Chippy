// PutBattery.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PutBattery extends Put
{
   public PutBattery()
   {
      makeButton("battery");
   }
   
   // add a new battery to the circuit list in Chippy
   @Override
   public void actionPerformed( ActionEvent e )
   {
      theChippy.addToCktList( new Battery() ); // has repaint in it
   }	
}
