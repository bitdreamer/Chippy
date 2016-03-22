// Battery.java  
// Samah and Emily 2005

package plugs;
import java.awt.*;
import java.io.*;

import Chippy.Hole;
import Chippy.RectangularPiece;

public class Battery extends RectangularPiece implements Serializable 
{
	protected Hole groundHole;  //ground 
	protected Hole posHole;     //positive voltage
	
	// fix - moving the battery should move any attached wires with.
	// Note - presently, if you move the battery underneath a wire, it 
	// may look connected, but I don't think it is.
	
	// battery never changes, return false
	public boolean charge()
	{ return false; }
  
	public Battery() 
	{
      xanchor = 78; yanchor = 120;
      width = 40; height = 20;
		   
		groundHole = new Hole(this,2,20);
		groundHole.setVoltage(0);  	//ground
      groundHole.setIdrive(true);
      groundHole.setNeeds(false);//groundHole.needs = false;
      
      connectix.add(groundHole);
		posHole = new Hole(this, 2, 10);
		posHole.setVoltage(5); 			//postive voltage
      posHole.setIdrive(true);
      posHole.setNeeds(false); //posHole.needs = false;
      
      connectix.add(posHole);
      name="battery";
	}
	
	//returns 
	public String saveMe()
	{
	   return "battery\n";
	}

   // Return Hole if this piece has one near given xy
   @Override
	public Hole findAHole(int x, int y)
	{
      Hole found = null;
      if ( groundHole.zatyou(x,y) ) { found = groundHole; }
      if ( posHole   .zatyou(x,y) ) { found = posHole;    }
	   return found;
	}

   // fix - battery is currently nailed down.  Make it a moveable piece.
   // To do so we should make the marking all relative to corner.
   // Also, make holes in line vertically ... works better with boards.
	@Override
	public void draw( Graphics g )
   {
   	super.draw(g);        
		// clears the window to white
      g.setColor( new Color(150,150,150) );
      //g.fillRect(738, 180, 20, 40 );	// draws the actual battery
      g.drawRect(xanchor,yanchor,20,40);
		groundHole.draw(g);
		posHole.draw(g);
	 		  
		// draws plus and minus
		g.setColor(Color.red);
      g.drawLine( xanchor+11, yanchor+10, xanchor+18, yanchor+10 );
      g.drawLine( xanchor+14, yanchor+6, xanchor+14, yanchor+13 );
      g.drawLine( xanchor+11, yanchor+20, xanchor+18, yanchor+20 );
	}
}
