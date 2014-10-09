// Chip7408.java
// Edited by Samah and Emily and Susan
// ... and Barry
// this is an AND chip

package Chippy;
import java.io.*;

public class Chip7408 extends Chip implements Serializable
{
    public Chip7408()
    {
       super(14); // 7408s have 14 pins.
       chip74 = "7408";
       
       pinArray[3].setIdrive(true); // the output pins are set to drive; "I drive".
       pinArray[6].setIdrive(true);
       pinArray[11].setIdrive(true);
       pinArray[8].setIdrive(true);
    }
    /*
    public String saveMe()
    {
       return "TTL "+ chip74 +xanchor+" "+yanchor+"\n";
    }
   */
   @Override
   // charge.  do the thing with the gates
   // This is for use with 1-14 pin numbering
   public boolean charge()
   {
      boolean changed = false;
      
      if ( checkPower() )
      {
           changed |= doAndGate( 1, 2, 3 );
           changed |= doAndGate( 4, 5, 6 );
           changed |= doAndGate( 13, 12, 11 );
           changed |= doAndGate( 10, 9, 8 );
      }
      else { setAllFloating(); }
      return changed;
   }
   
   // doAndGate.  Given 2 input pins and one output pin, sets voltage on 
   // output pin as AND of input pins' voltages
   public boolean doAndGate( Pin in1, Pin in2, Pin out1 )
   {
      boolean changed = false; 
      
      int v1 = in1.getDrivenV(); // effective voltage on pin1
      int v2 = in2.getDrivenV();

      // out1.setIdrive(true); is set permanently in constructor 
      if ( v1>=3 && v2>=3 ) { changed |= out1.setVoltage(4); } // here is the AND
      else                  { changed |= out1.setVoltage(1); }
      return changed;
   }
   // same, but uses ints, gets the pins by that number
   public boolean doAndGate( int n1, int n2, int o1 )
   {
       return doAndGate( pinArray[n1], pinArray[n2], pinArray[o1] );
   }
}