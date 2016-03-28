// Controller.java
// This should hold all of the buttons

package Chippy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controller extends JFrame 
{
   Chippy theChippy;
   
   public Controller( Chippy c )
   {
      theChippy = c;
      
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      setTitle("Controller");
      
      setSize(300,600);
      setLocation( new Point(800,60) );
      
      setLayout( new FlowLayout() );
      
      add( new plugs.PutBattery() );
      add( new plugs.PutBoard() );
      add( new plugs.PutChip() );
      add( new plugs.PutLight() );
      add( new plugs.DoClear() );
      add( new plugs.PutWire() );
      add( new plugs.PutSwitch() );
      add( new plugs.DoSave() );
      add( new plugs.DoLoad() );
      add( new plugs.DoAbout() );
      
      setVisible(true);
   }
}
