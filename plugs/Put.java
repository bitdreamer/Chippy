// Put.java
// Barrett Koster
// superclass for classes that put specific items on the
// bread board.

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Put extends JPanel implements ActionListener
{
   static protected Chippy.Chippy theChippy;

   public JButton makeButton( String s1 )
   {
      JButton jb = new JButton(s1);
      add(jb);
      jb.addActionListener(this);
      return jb;
   }
   // same thing for combo boxes
   public JComboBox makeComboBox( String[] choices )
   {
      JComboBox jcb = new JComboBox( choices );
      add(jcb);
      jcb.addActionListener(this);
      return jcb;
   }
   
   public void actionPerformed( ActionEvent e )
   {
      System.out.println("Put: error. This is the general actionPerformed.  ");
   }

   public static void setTheChippy( Chippy.Chippy ch ) { theChippy = ch; }
}
