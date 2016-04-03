package Chippy;
//ToggleSwitch.java
//04/04/05

// This Piece represents a toggle switch.  There are three pins aligned
// vertically.  Either the top two or the bottom two are connected.
// pressing on the switch changes the state.  Releasing it does not.

import java.awt.*;
import java.io.*;

import plugs.Wire;

public class ToggleSwitch extends RectangularPiece implements Serializable
{
	private boolean up = true; // true means top and center are connected,
                              // false means bottom and center are connected.
	private Color color;
	protected Pin topPin; 		//top pin
	protected Pin centerPin; 	//center pin
	protected Pin bottomPin;  	//bottom pin
	//private int voltage = 99;

   public ToggleSwitch(int x, int y)
   {
  		super(x,y);
    	color = Color.yellow;
    	height = 3*gridSize; // switch is a vertical rectangle covering 3 holes.
	 	width = gridSize; 
	 	topPin = new Pin( this, 0, 0 ); // Pin(x,y,"topPin",this);
		centerPin = new Pin( this, 0, gridSize ); // Pin(x,y+10, "centerPin", this);
	 	bottomPin = new Pin( this, 0, 2*gridSize ); // Pin(x,y+20,"bottomPin",this);
      connectix.add(topPin);
      connectix.add(centerPin);
      connectix.add(bottomPin);
   }
  	
   public String saveMe()
   {
      return "ToggleSwitch " +xanchor+" "+yanchor+"\n";
   }



   // fix- this code is a mess. Between grab and contain I
   // can't tell which is used for what
   // grab.  returns true if this xy is near the grab point
   // also toggles the switch if xy is within bounds
   public boolean grab( int x, int y )
   {
       boolean ret = super.grab( x, y );
       //contains( x, y ); // if ( ret) { up = !up; }
       return ret;
   }

   // iff coords are on the switch toggle it (return true if changed)
   public boolean press( int x, int y) 
	{
      boolean changed = false;
      
		int x1 = getX();
	  	int y1 = getY();
	  	if (((x >= x1) && (x <= x1 + width)) && 
		    ((y >= y1) && (y <= y1 + height)))
	   {
	  	   up = !up;

			changed = true;
		}
      return changed;
   }

   @Override
   // charge.
   public boolean charge()
   {
      boolean changed = false;
      // report();
      if ( up ) // top and center are a wire, bottom floats
      {
         changed |= Wire.likeWire( topPin, centerPin );
         bottomPin.setIdrive(false); //bottomPin.setVoltage(3);
      }
      else // bottom and center are a wire, top floats
      {
         changed |= Wire.likeWire( bottomPin, centerPin );
         topPin.setIdrive(false); topPin.setVoltage(3);
      }
      return changed;
	}
   
   public void report()
   {
      System.out.println("ToggleSwitch.report - from top to bottom ...");
      topPin.report();
      centerPin.report();
      bottomPin.report();
   }


  
   // fix- the colored rectangle on this is sideways or something.
  	public void draw(Graphics g )
  	{
		Graphics2D g2 = (Graphics2D)g; // ??
		
		if ( isSelected ) { g.setColor(color.darker()); }
		else              { g.setColor(color); }

	 	g.fillRect((int)(xanchor-0.1*gridSize),(int)(yanchor-0.4*gridSize),width,height);
	 	if (up)
		{
			g.setColor(Color.darkGray);
			g2.setStroke(new BasicStroke(gridSize/5));
			g.drawLine(topPin.getX()+5, topPin.getY()+2, centerPin.getX()+5, centerPin.getY()+2);
		}
		else
		{
			g.setColor(Color.darkGray);
			g2.setStroke(new BasicStroke(gridSize/5));
			g.drawLine(bottomPin.getX()+5, bottomPin.getY()+2, centerPin.getX()+5, centerPin.getY()+2);
		}
		g2.setStroke(new BasicStroke(1));
		topPin.draw(g);
		centerPin.draw(g);
		bottomPin.draw(g);	
	}
}