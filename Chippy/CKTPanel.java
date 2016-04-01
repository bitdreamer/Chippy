// CKTPanel.java
// Barrett Koster
// 2016 ... cleaning up 
// This is the whole circuit display, holds all of the pieces.


package Chippy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CKTPanel extends JPanel
{
   Chippy theChippy; // pointer to the main program
   int gz=10;  // grid size
  
   
   LinkedList <Piece> cktList;  // all the parts
   
   public CKTPanel( Chippy ch)
   {
      theChippy = ch;
      cktList = new LinkedList <Piece> ();
      setBackground( new Color( 255, 235, 215  ) );
   }
   
   // access
   public LinkedList <Piece> getCktList() { return cktList; }
}
