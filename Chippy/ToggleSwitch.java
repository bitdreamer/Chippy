package Chippy;
//ToggleSwitch.java
//04/04/05

// This Piece represents a toggle switch.  There are three pins aligned
// vertically.  Eithe the top two or the bottom two are connected.
// pressing on the switch changes the state.  Releasing it does not.

import java.awt.*;
import java.io.*;

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
    	height = 25; // switch is a vertical rectangle covering 3 holes.
	 	width = 10; 
	 	topPin = new Pin( this, 0, 0 ); // Pin(x,y,"topPin",this);
		centerPin = new Pin( this, 0, 10 ); // Pin(x,y+10, "centerPin", this);
	 	bottomPin = new Pin( this, 0, 20 ); // Pin(x,y+20,"bottomPin",this);
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
       contains( x, y ); // if ( ret) { up = !up; }
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
      if ( up ) // top and center are a wire, bottom floats
      {
         Wire.likeWire( topPin, centerPin );
         bottomPin.setIdrive(false); bottomPin.setVoltage(3);
      }
      else // bottom and center are a wire, top floats
      {
         Wire.likeWire( bottomPin, centerPin );
         topPin.setIdrive(false); topPin.setVoltage(3);
      }
      return true;
	}
   /*
   public void report()
   {
      System.out.println("ToggleSwitch.report - from top to bottom ...");
      topPin.report();
      centerPin.report();
      bottomPin.report();
   }
*/
/*
   //--------------------------------------------------------------------------
   // adjust an integer to grid
	//--------------------------------------------------------------------------
  // Whoever invented this translate stuff ... I really should have corrected.
   // fix - this whole move thing needs help.
  	public void move(int newX, int newY)
  	{
  	 	super.move(newX, newY);
		topPin.setX(gridify(newX));
		topPin.setY(gridify(newY));
		topPin.findAHole();
		
		centerPin.setX(gridify(newX));
		centerPin.setY(gridify(newY+10));
		centerPin.findAHole();
 
      bottomPin.setX(gridify(newX));
		bottomPin.setY(gridify(newY+20));
		bottomPin.findAHole();
  	}
*/
  
   // fix- the colored rectangle on this is sideways or something.
  	public void draw(Graphics g )
  	{
		Graphics2D g2 = (Graphics2D)g; // ??
		
		if ( isSelected ) { g.setColor(color.darker()); }
		else              { g.setColor(color); }

	 	g.fillRect(xanchor,yanchor,width,height);
	 	if (up)
		{
			g.setColor(Color.darkGray);
			g2.setStroke(new BasicStroke(2));
			g.drawLine(topPin.getX()+5, topPin.getY()+2, centerPin.getX()+5, centerPin.getY()+2);
		}
		else
		{
			g.setColor(Color.darkGray);
			g2.setStroke(new BasicStroke(2));
			g.drawLine(bottomPin.getX()+5, bottomPin.getY()+2, centerPin.getX()+5, centerPin.getY()+2);
		}
		g2.setStroke(new BasicStroke(1));
		topPin.draw(g);
		centerPin.draw(g);
		bottomPin.draw(g);	
	}
}