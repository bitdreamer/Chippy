// PutBattery.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Chippy.*;

public class PutBoard extends Put
{
   //int numBoards=0; 
   
   public PutBoard()
   {
      makeButton("board");
   }
   
   // add a new Board to the circuit layout in Chippy
   @Override
   public void actionPerformed( ActionEvent e )
   {
      if ( theChippy.getBug() )
      { System.out.println("PutBoard.actionPerformed: numBoards="+theChippy.getNumBoards() ); }
   
      if ( theChippy.getNumBoards()<5 )
        {
            //Board b = new Board( 590-numBoards*140,180 );
            theChippy.addToCktList( new plugs.Board( 20 + (theChippy.getNumBoards())*14*CKTPanel.gz,180 ) );
            theChippy.incNumBoards();
        }
   }	
}
