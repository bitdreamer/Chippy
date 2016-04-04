// Doer.java
// 2014 Barrett Koster

// This class does command-line type stuff, reads and writes from files to save
// or load the circuit.

package Chippy;

import java.awt.*;

import java.util.StringTokenizer;

import plugs.Battery;
import plugs.Board;
import plugs.Chip;
import plugs.Light;
import plugs.Wire;

public class Doer
{
   Chippy theChippy;

   
   public Doer( Chippy tc )
   {
      theChippy = tc;

   }

   // does whatever is in the command in s
   public void doCom( String s )
   {
      StringTokenizer st = new StringTokenizer(s);
      String key = st.nextToken();
      if      ( key.equals("battery"            ) ) { doBattery( st ); }
      else if ( key.equals("board"              ) ) { doBoard( st ); }
      else if ( key.equals("TTL"                ) ) { doTTL( st ); }
      else if ( key.equals("light"              ) ) { doLight( st ); }
      else if ( key.equals("PushButtonSwitch"   ) ) { doPushButtonSwitch( st ); }
      else if ( key.equals("ToggleSwitch"       ) ) { doToggleSwitch( st ); }
      else if ( key.equals("wire"               ) ) { doWire( st ); }
      
   }

   public Piece doBattery( StringTokenizer st )
   {
      int x = Integer.parseInt( st.nextToken() );
      int y = Integer.parseInt( st.nextToken() );
      Piece p = new Battery(x,y);
      theChippy.getCktList().add( p );
      return p;
   }
   
   public Piece doBoard( StringTokenizer st )
   {
      boolean bug = theChippy.getBug();
      if (bug) { System.out.println("Doer.doBoard: entering ...."); }
   
      int x = Integer.parseInt( st.nextToken() );
      int y = Integer.parseInt( st.nextToken() );
      Piece p = new Board(x,y);
      theChippy.getCktList().add( p );
      theChippy.incNumBoards();
      if (bug) { System.out.println("Doer.doBoard: exiting, numBoards="+theChippy.getNumBoards() ); }
      return p;
   }
   
   public Piece doTTL( StringTokenizer st )
   {
      Piece p = null;
      
      p =  Chip.makeChip( st );
      theChippy.getCktList().add( p );
      p.dropIn();
      
      return p;
   }
   public Piece doLight( StringTokenizer st )
   {
      Piece p = null;
      
      int cred = Integer.parseInt( st.nextToken() );
      int cgreen = Integer.parseInt( st.nextToken() );
      int cblue = Integer.parseInt( st.nextToken() );
      
      p =  new Light( new Color(cred, cgreen, cblue) );
      int x = Integer.parseInt( st.nextToken() );
      int y = Integer.parseInt( st.nextToken() );
      p.move(x,y);      
      theChippy.getCktList().add( p );
      p.dropIn();
      
      return p;
   }
   
   public Piece doPushButtonSwitch( StringTokenizer st )
   {
      Piece p = null;
      
      int x = Integer.parseInt( st.nextToken() );
      int y = Integer.parseInt( st.nextToken() );
      p =  new PushButtonSwitch(x,y );    
      theChippy.getCktList().add( p );
      p.dropIn();

      return p;
   }
   public Piece doToggleSwitch( StringTokenizer st )
   {
      Piece p = null;
      
      int x = Integer.parseInt( st.nextToken() );
      int y = Integer.parseInt( st.nextToken() );
      p =  new ToggleSwitch(x,y );    
      theChippy.getCktList().add( p );
      p.dropIn();
      
      return p;

   }
   
   
   public Piece doWire( StringTokenizer st )
   {
      Piece p = null;
      
      
      int cred = Integer.parseInt( st.nextToken() );
      int cgreen = Integer.parseInt( st.nextToken() );
      int cblue = Integer.parseInt( st.nextToken() );
      
      int x1 = Integer.parseInt( st.nextToken() );
      int y1 = Integer.parseInt( st.nextToken() );
      int x2 = Integer.parseInt( st.nextToken() );
      int y2 = Integer.parseInt( st.nextToken() );
      
      p =  new Wire(x1,y1, x2, y2, new  Color(cred,cgreen,cblue)  );    
      theChippy.getCktList().add( p );
      p.dropIn();
      
      return p;
   }
   
   

}
