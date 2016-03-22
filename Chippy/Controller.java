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
      
      setVisible(true);
   }
}