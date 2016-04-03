package plugs;
// ChipFlop.java
// Barrett Koster 2005
// Chip that is a flip flop (D)
// positive edge trigger on pin 11

import java.io.*;

import Chippy.FF;
import Chippy.Hole;
import Chippy.Pin;

public class Chip74377 extends Chip implements Serializable
{
   boolean clock; // true if the clock was up on last charge
   FF[] theFFs;
   int clockpin = 11;
    
   public Chip74377()
   {
      super(20); // 74377s have 20 pins.
      // height = 95;
       chip74 = "74377";
       theFFs = new FF[8];
       theFFs[0] = new FF( pinArray[3], pinArray[2] );
       theFFs[1] = new FF( pinArray[4], pinArray[5] );
       theFFs[2] = new FF( pinArray[7], pinArray[6] );
       theFFs[3] = new FF( pinArray[8], pinArray[9] );
       theFFs[4] = new FF( pinArray[13], pinArray[12] );
       theFFs[5] = new FF( pinArray[14], pinArray[15] );
       theFFs[6] = new FF( pinArray[17], pinArray[16] );
       theFFs[7] = new FF( pinArray[18], pinArray[19] );
       clock = false;
   }


   // Here is the no-return one if you need it
   @Override
   public boolean charge()
   {
      chargeb();
      return true;
   }

   // charge.  if there is power to the chip and the clock has gone up
   // clock each flip flop. 
   @Override
   public boolean chargeb()
   {
      boolean changed = false;
      if ( checkPower() )
      {
         //int v = pinArray[11].getDrivenV();
         Pin pk = pinArray[clockpin];
         if (pk.getNeeds()) // will be set if value driving clock has changed
         {
            Hole drv = (Hole)(pk.getBuddy());
            if ( drv.getVoltage()>=3 && drv.getLastVolt()<3 ) // detects pos edge trigger
            {
               for ( int j=0; j<8; j++ )
               {
                   boolean ch = theFFs[j].clockIt();
                   changed = changed || ch;
               }
            }
         }
      }
      else { setAllFloating(); }
      return changed;
   }
   
   public void report()
   {
       System.out.println("ChipFlop.report ... clockWasUp=" + clock );
       System.out.print("  clock pin: "); pinArray[11].report();
       theFFs[3].report(); 
   }
}
