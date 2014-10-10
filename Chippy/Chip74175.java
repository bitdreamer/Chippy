// Chip74175.java
// Barrett Koster 2005
// This is a quad flip flop chip.
// This has 4 positive-edge triggered flip flops with Q and Qbar outputs.
// The Clear singnal has to be high for it to work. (Low resets all Qs low 
// independent of inputs or clock.) 

package Chippy;

import java.io.*;

public class Chip74175 extends Chip implements Serializable
{
   boolean clock; // true if the clock was up on last charge
   FF[] theFFs;
   int clockPin = 9;
   int clearPin = 1;
    
   public Chip74175()
   {
      super(16); // 74175s have 16 pins.
      height = 75;
       chip74 = "74175";
       theFFs = new FF[4];
       theFFs[0] = new FF( pinArray[4],  pinArray[2] );
       theFFs[1] = new FF( pinArray[5],  pinArray[7] );
       theFFs[2] = new FF( pinArray[12], pinArray[10] );
       theFFs[3] = new FF( pinArray[13], pinArray[15] );
       clock = false;
   }


   // Here is the no-return one if you need it
   @Override
   public boolean charge()
   {
      /*
      chargeb();
      return true;
   }

   // charge.  if there is power to the chip and the clock has gone up
   // clock each flip flop. 
   @Override
   public boolean chargeb()
   {
      */
      boolean changed = false;
      boolean ch;
      if ( checkPower() )
      {
         //int v = pinArray[11].getDrivenV();
         Pin pk = pinArray[clockPin];
         if (pk.needs) // needs will be true if value driving clock has changed
         {
            Hole drv = (Hole)(pk.buddy);
            if ( drv.voltage>=3 && drv.lastVolt<3 ) // detects pos edge trigger
            {
               for ( int j=0; j<4; j++ )
               {
                   ch = theFFs[j].clockIt();
                   changed |= ch;
               }
            }
         }
      }
      else { ch = setAllFloating(); changed |= ch; }
      return changed;
   }
   
   public void report()
   {
       System.out.println("ChipFlop.report ... clockWasUp=" + clock );
       System.out.print("  clock pin: "); pinArray[11].report();
       theFFs[3].report(); 
   }
}
