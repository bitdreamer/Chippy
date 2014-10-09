// PushButtonSwitch.java
// 2005 Soft E  class

// 2014 Barrett Koster  
// This class 

package Chippy;
import java.awt.*;
import java.io.*;

public class PushButtonSwitch extends RectangularPiece implements Serializable
{
	private boolean closed = true;
	private Color color;
	protected Pin topPin; 	  //top pin
	protected Pin bottomPin;  //bottom pin
	//private int voltage = 99;

  	public PushButtonSwitch(int x, int y)
  	{
  		super(x,y);
    	color = Color.yellow;
    	height = 10; //setHeight(10);
	 	width = 30; // setWidth(30);
	 	topPin = new Pin(this,0,0); // Pin(x,y,"topPin",this);
	 	bottomPin = new Pin(this,0,10); // Pin(x,y+10,"bottomPin",this);
      connectix.add(   topPin);
      connectix.add(bottomPin);

   }
  	
   public String saveMe()
   {
      return "PushButtonSwitch " +xanchor+" "+yanchor+"\n";
   }

   // grab.  returns true if this xy is near the grab point
   // also toggles the switch if xy is within bounds
   public boolean grab( int x, int y )
   {
       boolean ret = super.grab( x, y );
       contains( x, y ); // if ( ret) { pressed = !pressed; }
       return ret;
   }
 
   // iff coords are on the switch, sw is closed (return true if changed)
   public boolean press( int x, int y) 
   {
      boolean changed = false;
      
      int x1 = getX();
      int y1 = getY();
      if (((x >= x1) && (x <= x1 + width)) && 
          ((y >= y1) && (y <= y1 + height)))
      {
         if (!closed) { changed = true; }
         closed = true;
      }
      return changed;
   }

   // release the switch - note : we check to make sure the xy is on 
   // the switch when we press it, lest we get all switches all the time.
   // But we don't want to get it stuck, so we let it release when
   // the mouse goes up without needing it to still be on xy.
   public boolean release() 
   {
      boolean changed = false;
   
      if (closed) {changed = true;} // about to changed to open
      closed = false;
      
      return changed;
   }


   @Override
   // If it's pressed, it's a wire.  If not, then ends are no-drive floaters.
	public boolean charge()
	{
		//topPin.charge();
		//bottomPin.charge();
		if (closed) //
		{
         Wire.likeWire(topPin,bottomPin);
		}
		else 
		{
            topPin.setIdrive(false);    topPin.setVoltage(3);
         bottomPin.setIdrive(false); bottomPin.setVoltage(3);
		}
		return true;
	}

  	@Override
   public void draw(Graphics g )
  	{
	   if (getSelected())
      {
         g.setColor(color.darker());
      }
		else
		  g.setColor(color);
	 	g.fillRect(getX(),getY(),width,height);
	 	if (closed)
		{
			g.setColor(Color.darkGray);
			g.fillOval(getX()+4, getY()+4, 8, 8);
		}
		else
		{
			g.setColor(Color.darkGray);
			g.drawOval(getX()+4, getY()+4, 8, 8);
		}
		topPin.draw(g);
		bottomPin.draw(g);
	}
  	
  	public void report()
  	{
  	   System.out.println("PBS reports from pins, top and bottom : ");
  	   topPin.report();
  	   bottomPin.report();
  	}
}

