// Connectic.java
// Barry 2010
// This class is the super class for Pin and Hole, which
// move alike in certian ways we group here.

package Chippy;

import java.io.Serializable;

public class Connectic implements Serializable
{
   protected static Chippy theChippy; // needed so we can find them globally
   protected static int maxid=0; //ids are used for debugging, when we have to
   protected int id;             // track down exactly what is connected to what.
   protected int xab, yab; // absolute position
   protected int xrel, yrel; // relative to Piece
   protected Piece boat; // the piece this Hole or Pin is attached to
   protected Connectic buddy; // Hole or Pin this is plugged into
   protected boolean idrive; // this piece will push its voltage to buddy.
      // idrive is true iff this is a pin that is the output of a gate
      // or a pin on the other end of a wire being driven or a hole
      // on the other end of a cluster being driven (through some
      // other Hole).   If both this and buddy have idrive,
      // that's a fire.
	protected int voltage;
   // possible values ....
   // 0 = ground from battery or direct wire chain, also works as logical 0
   // 1 = logical 0 (but cannot ground a chip)
   // 4 = logical 1 (but cannot power a chip)
   // 5 = power from the battery or direct wire chain, also works as logical 1.
   // 10 is old, becoming taken over by driver stuff in cluster
   // 2 = drivers fighting (new)
   // 3 = floating.  lights should not light up if either end
   //     is floating.  Inputs on gate, however, float to logical 1
   //     (but we still write voltage = 3 )
	
	protected int lastVolt; // previous voltage, see charging procedure below
	protected boolean needs; // needs attention, something has changed around it.
	                         // See charge operation below.
	
   /*
    First we need to talk about how connections are established.
    When you move a piece, use piece.move().  This redraws the chip as
    it goes, but does not connect it.  When you let go of it, Chippy
    runs piece.dropIn().  This should disconnect all of the pins from
    their old holes (if any) and then make new connection in
    their new holes (if any).  To disconnect, set to null myPin
    and myHole and note that both sides need attention (needs=true).  
    To connect, set myPin and myHole to each other and again, needs=true.
    
    charging being revised .... 
    OK, now we need to describe how the voltage proppagation works.  
    At any instant in time, all Pins and Holes will have a voltage
    and a value of needs.  The charge() call from to top will tell
    each piece to evaluate it's state and if anything 'needs' attention,
    it will adjust its voltages by looking beyond its border to
    see what new inputs there might be.      If this process results in
    changing current values in this piece, it will set 'needs=true' 
    on any attached Connectics
    with idrive==false.  Thus, we READ form other pieces, but we
    do not WRITE to them, except to point out that they need
    to re-evaluate their state.  
    
    Because this is done iteratively we should avoid infinite 
    recursion.  The "needs" flag carries the sense of change
    forward, and the "idrive" flag keeps it going only in one 
    direction.    
    
   */
	
	// setVoltage is used for outputs.  I'm not sure about inputs.
	public boolean setVoltage(int v)
	{
	   boolean changed = false; // start with no change
	   
	   lastVolt = voltage; voltage = v; 
	   changed = (lastVolt!=voltage);
	   if ( changed ) { if (buddy!=null) { buddy.setNeeds(); } }
	   return changed;
	}


   // constructor.  set from piece and relative coords to piece
   public Connectic( Piece p, int x, int y )
   {
      id = maxid++;
      boat = p;
      xrel = x;
      yrel = y;
      setXYab();
      idrive = false;
      voltage = 3; lastVolt = 3;
      buddy = null;
      needs = true;
   }

   public void report()
   {
      if ( buddy!=null && (voltage!=3 || buddy.voltage!=3) )
      {
         if ( this instanceof Pin) { System.out.print("    Pin "); }
         else if ( this instanceof Hole ) { System.out.print("    Hole "); }
         else { System.out.println(" Connectic.report: error *****");}
         System.out.println("    id="+id+" at "+xab+" "+yab
              +" has idrive="+idrive+" v="+voltage);
         System.out.print("   buddy id="+buddy.id);
         if ( buddy.buddy == this)
         {
            System.out.println(" and it checks");
         }
         else { System.out.println(" and it does not check."); }
      }
   }

   // set absolute xy from piece coords and relative xy of this Connectic.
   // Note: the piece has to be defined (and it should be).
   final void setXYab()
   {
      xab = boat.xanchor + xrel;
      yab = boat.yanchor + yrel;
   }

   // disconnect from other pin/hole (both directions) if there is one
   public void unplug()
   {
      if ( buddy!=null )
      {  setNeeds();
         buddy.setNeeds();
         buddy.buddy = null;
         buddy = null;
      }
   }


   // Gets the voltage that the buddy is driving.
   // If there is no buddy or buddy is not driving, return floating v=3.
   public int getDrivenV()
   {
      int v=3; // starts floating
      if ( buddy!=null )
      {
         if ( buddy.idrive ) { v = buddy.voltage; }
      }

      return v;
   }

   // just does the one way ...
   public void setBuddy( Connectic b ) { buddy = b; }
   public Connectic getBuddy() { return buddy; }

   //public boolean getIdrive() { return idrive; }
   
   // set idrive to given value and return true if this is a change.
   // See idrive declaration 
   public boolean setIdrive( boolean b )
   {
      boolean changed = false;
      if ( idrive != b ) { changed = true; }
      idrive = b;
      
      return changed;
   }
   // Note: we will eventually want to know when changes occur.
   
   public int getVoltage() { return voltage; }

   public static void setTheChippy(Chippy x){theChippy = x; }
   
   // connectics that are drivers never need attention (needs). 
   
   public void setNeeds( ) { if (!idrive) { needs=true; } }
   
   public void setNeeds( boolean tf ) { needs = tf; }
   public boolean getNeeds() { return needs; }
   public boolean getIdrive() { return idrive; }
   public int getLastVolt() { return lastVolt; }
}
