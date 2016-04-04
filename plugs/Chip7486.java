// Chip7486.java
// Edited by Samah and Emily and Susan
// ... and Barry
// this is a Quad XOR

package plugs;
import java.io.*;

import Chippy.Pin;

public class Chip7486 extends Chip implements Serializable
{
   boolean bug = false;
   
    public Chip7486()
    {
       super(14); // 7486s have 14 pins.
       chip74 = "7486";
       
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
      if(bug) { System.out.print("Chip7408.charge ...");}
      boolean changed = false;
      
      if (bug) { System.out.println(" power="+checkPower()); }
      if ( checkPower() )
      {  boolean c;
           c = doXORGate( 1, 2, 3 );
           changed = changed || c;
           c= doXORGate( 4, 5, 6 );
           changed = changed || c;
           c= doXORGate( 13, 12, 11 );
           changed = changed || c;
           c= doXORGate( 10, 9, 8 );
           changed = changed || c;
      }
      else { setAllFloating(); }
      return changed;
   }
   
   // doXORGate.  Given 2 input pins and one output pin, sets voltage on 
   // output pin as AND of input pins' voltages
   public boolean doXORGate( Pin in1, Pin in2, Pin out1 )
   {
      if(bug){ System.out.print("gate "+in1.getIndex()+" "+in2.getIndex()
                 +" "+out1.getIndex()+ " :");}
      boolean changed = false; 
      
      int v1 = in1.getDrivenV(); // effective voltage on pin1
      int v2 = in2.getDrivenV();
      
      if(bug){ System.out.println("v1="+v1+" v2="+v2);}

      // out1.setIdrive(true); is set permanently in constructor 
      if ( (v1>=3 && v2<3) || (v1<3 && v2>=3) ) 
      { changed |= out1.setVoltage(4); } // here is the XOR
      else                  { changed |= out1.setVoltage(1); }
      return changed;
   }
   // same, but uses ints, gets the pins by that number
   public boolean doXORGate( int n1, int n2, int o1 )
   {
       return doXORGate( pinArray[n1], pinArray[n2], pinArray[o1] );
   }
}