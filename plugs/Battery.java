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
  

   public Battery( int x, int y ) 
   {
      xanchor = gridify(x); // gridify(78); 
      yanchor = gridify(y); // gridify(120);
      width = 3*gridSize; height = 3*gridSize;
		   
	  groundHole = new Hole(this,gridSize,2*gridSize);
      groundHole.setVoltage(0);  	//ground
      groundHole.setIdrive(true);
      groundHole.setNeeds(false);//groundHole.needs = false;
      connectix.add(groundHole);
      
	  posHole = new Hole(this, gridSize, gridSize);
	  posHole.setVoltage(5); 			//positive voltage
      posHole.setIdrive(true);
      posHole.setNeeds(false); //posHole.needs = false;
      
      connectix.add(posHole);
      name="battery";
	}
	
	//returns 
	public String saveMe()
	{
	   return "battery "+xanchor+" "+yanchor+"\n";
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
	@Override
	public void draw( Graphics g )
   {
   	super.draw(g);        
		// clears the window to white
      g.setColor( new Color(150,150,150) );
      //g.fillRect(738, 180, 20, 40 );	// draws the actual battery
      g.drawRect(xanchor,yanchor,width,height);
		groundHole.draw(g);
		posHole.draw(g);
  
		// draws plus and minus
		g.setColor(Color.red);
		g.drawString("+", xanchor+2*gridSize, yanchor+gridSize);
		
		g.setColor( Color.black );
		g.drawString("-", xanchor+2*gridSize, yanchor+2*gridSize);
	}
}
