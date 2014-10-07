// Doer.java
// 2014 Barrett Koster

// This class does command-line type stuff, reads and writes from files to save
// or load the circuit.

package Chippy;

import java.util.StringTokenizer;

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
      if      ( key.equals("battery" ) ) { doBattery( st ); }
      else if ( key.equals("board"   ) ) { doBoard( st ); }
      
   }

   public void doBattery( StringTokenizer st )
   {
      Piece p = new Battery();
      theChippy.getCktList().add( p );
   }
   
   public void doBoard( StringTokenizer st )
   {
      int x = Integer.parseInt( st.nextToken() );
      int y = Integer.parseInt( st.nextToken() );
      theChippy.getCktList().add( new Board(x,y) );
   }

}
