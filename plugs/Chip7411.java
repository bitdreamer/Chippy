// Chip7411.java

// ... and Barry
// this is a tripple 3-input AND chip

package plugs;
import java.io.*;

import Chippy.Pin;

public class Chip7411 extends Chip implements Serializable
{
    public Chip7411()
    {
       super(14); // 7408s have 14 pins.
       chip74 = "7411";
       
       pinArray[6].setIdrive(true); // the output pins are set to drive; "I drive".
       pinArray[8].setIdrive(true);
       pinArray[12].setIdrive(true);
    }
   @Override
   // charge.  do the thing with the gates
   public boolean charge()
   {
      boolean changed = false;
      
      if ( checkPower() )
      {
           changed |= doAnd3Gate( 1, 2, 13, 12 );
           changed |= doAnd3Gate( 3, 4, 5, 6 );
           changed |= doAnd3Gate( 9, 10, 11, 8 );

      }
      else { setAllFloating(); }
      return changed;
   }
   
   // doAndGate.  Given 2 input pins and one output pin, sets voltage on 
   // output pin as AND of input pins' voltages
   public boolean doAnd3Gate( Pin in1, Pin in2, Pin in3, Pin out1 )
   {
      boolean changed = false; 
      
      int v1 = in1.getDrivenV(); // effective voltage on pin1
      int v2 = in2.getDrivenV();
      int v3 = in3.getDrivenV();

      // out1.setIdrive(true); is set permanently in constructor 
      if ( v1>=3 && v2>=3 && v3>=3 ) 
      { changed |= out1.setVoltage(4); } // here is the AND
      else                  
      { changed |= out1.setVoltage(1); }
      return changed;
   }
   // same, but uses ints, gets the pins by that number
   public boolean doAnd3Gate( int n1, int n2, int n3, int o1 )
   {
       return doAnd3Gate( pinArray[n1], pinArray[n2], pinArray[n3], 
             pinArray[o1] );
   }
}