// PutLight.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class PutLight extends Put
{
   JComboBox <plugs.Light> cb;
   
   public PutLight()
   {
      cb = makeComboBox(plugs.Light.lightNames);
   }
   
   // get the light color from the menu, make a new light of that
   // color, 
   @Override
   public void actionPerformed( ActionEvent e )
   {

            //Board b = new Board( 590-numBoards*140,180 );
            plugs.Light c;
            
       int lightType = cb.getSelectedIndex();
       c = plugs.Light.makeLight(  lightType );
       theChippy.addToCktList( c ); // has repaint in it
      cb.setSelectedIndex(0); 
   }	
}
