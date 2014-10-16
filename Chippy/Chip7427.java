package Chippy;
// Chip7427.java

// this is a tripple 3-input NOR chip

import java.io.*;

public class Chip7427 extends Chip implements Serializable
{
   public Chip7427()
   {
      super(14); // 7427s have 14 pins.
      chip74 = "7427";
      pinArray[6].setIdrive(true); // the output pins are set to drive; "I drive".
      pinArray[8].setIdrive(true);
      pinArray[12].setIdrive(true);


   }

   @Override
   // charge.  do the thing with the gates
   // This is for use with 1-14 pin numbering
   public boolean charge()
   {
      boolean changed = false;
      
      if ( checkPower() )
      {
           changed |= doNOR3Gate( 1, 2, 13, 12 );
           changed |= doNOR3Gate( 3, 4, 5, 6 );
           changed |= doNOR3Gate( 9, 10, 11, 8 );
      }
      else { setAllFloating(); }
      return changed;
   }

   // doOrGate.  Given 2 input pins and one output pin, sets voltage on
   // output pin as OR of input pins' voltages
   public boolean doNOR3Gate( Pin in1, Pin in2, Pin in3, Pin out1 )
   {
      boolean changed = false; 
      
      int v1 = in1.getDrivenV(); // effective voltage on pin1
      int v2 = in2.getDrivenV();
      int v3 = in3.getDrivenV();

      // out1.setIdrive(true);
      if ( v1>=3 || v2>=3 || v3>=3 ) // if any input is high ... 
      { changed |= out1.setVoltage(1); } // set output to low
      else                  
      { changed |= out1.setVoltage(4); } // else output is high
      
      return changed;
   }

   // same, but uses ints, gets the pins by that number
   public boolean doNOR3Gate( int n1, int n2, int n3, int o1 )
   {
       return doNOR3Gate( pinArray[n1], pinArray[n2], pinArray[n3],
             pinArray[o1] );
   }
}