// ChipNot.java
// Edited by Samah and Emily and Susan and Barry
// This class does a 7404 hex inverter.

package Chippy;
import java.io.*;

public class ChipNot extends Chip implements Serializable
{
   static final long serialVersionUID = 0; // makes the compiler happy
   
   public ChipNot()
   {
      super(14); // 7404s have 14 pins.
      chip74 = "7404";
      pinArray[2].setIdrive(true);
      pinArray[4].setIdrive(true);
      pinArray[6].setIdrive(true);
      pinArray[8].setIdrive(true);
      pinArray[10].setIdrive(true);
      pinArray[12].setIdrive(true);
   }

   // charge.  do the thing with the gates
   @Override
   public boolean charge()
   {
      boolean changed = false;
      if ( checkPower() )
        {
            changed |= doNotGate( 1, 2 );
            changed |= doNotGate( 3, 4 );
            changed |= doNotGate( 5, 6 );
            changed |= doNotGate( 13, 12 );
            changed |= doNotGate( 11, 10 );
            changed |= doNotGate( 9, 8 );
        }
      else { setAllFloating(); }
        return changed;
    }
   
   // doNotGate.  Given one input pin and one output pin, sets voltage on 
   // output pin as NOT of input pin's voltage
   public boolean doNotGate( Pin in1, Pin out1 )
   {
      boolean changed = false;
      
      int v1 = in1.getDrivenV();

      // out1.setIdrive(true);

      if ( v1>=3 ) { changed |= out1.setVoltage(1); }
      else         { changed |= out1.setVoltage(4); }
      
      return changed;
   }
   // same, but uses ints, gets the pins by that number
   public boolean doNotGate( int n1, int o1 )
   {
       return doNotGate( pinArray[n1], pinArray[o1] );
   }
}