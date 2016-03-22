// PutBattery.java

package plugs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PutBoard extends Put
{
   int numBoards=0; 
   
   public PutBoard()
   {
      makeButton("board");
   }
   
   // add a new Board to the circuit layout in Chippy
   @Override
   public void actionPerformed( ActionEvent e )
   {
      if ( numBoards<5 )
        {
            //Board b = new Board( 590-numBoards*140,180 );
            theChippy.addToCktList( new Chippy.Board( 20 + (numBoards++)*140,180 ) );
        }
   }	
}
