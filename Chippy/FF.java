package Chippy;
// FF.java
// Barrett Koster 2005
// This is for one D flip flop.
// It stores 1 bit when the clocked, moving it from the input to the
// output (and then staying there until clocked again.

import java.io.*;

public class FF implements Serializable
{
    boolean theBit; // This is the stored bit, true=high.
    Pin pinin;
    Pin pinout;
    static int count=0; // fix - what is this for?
    
    public FF( Pin p1, Pin p2 )
    {
        theBit = true;
        pinin = p1;
        pinout = p2;
        pinout.setIdrive(true);
    }
    
    // clockIt.  propogate the input to the output
    // return: if this changes the output, return true, else return false
    public boolean clockIt()
    {
        boolean changed=false;

        int vin = pinin.getDrivenV(); // the input voltage
        if ( vin>=3 ) { changed |= pinout.setVoltage(4); }
        else          { changed |= pinout.setVoltage(1); }

        return changed;
    }

    public void report()
    {
        System.out.println("FF.report: bitIsHigh="+theBit );
        pinin.report();
        pinout.report();
    }
}


