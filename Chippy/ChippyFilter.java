// ChippyFilter.java
// Susan Hwang 2005
//based on example from java.sun.com tutorial, FileChooserDemo2
// Does WHAT?  Ah, makes it so that acceptable file extension for
// Chippy files is 'chpy'.

package Chippy;
import java.io.File;
import javax.swing.filechooser.*;

public class ChippyFilter extends FileFilter {

  //acceptable file extension for Chippy files
  public final static String chpy = "chpy";

  @Override
  public boolean accept(File f) {
  
    //let user search beyond initial directory
    if (f.isDirectory()) {
	   return true;      
	 }
	 
	 //find the extension of the file name
	 String s = f.getName();
	 String ext = null;
	 int pos = s.lastIndexOf('.');   
	 if ((pos > 0) && (pos < s.length()-1)) {
	   ext = s.substring(pos+1).toLowerCase();
	 }  
	 	 
	 //only chpy extension
	 if (ext != null) {
	   if (ext.equals(chpy)){
		  return true;
		}
		else {
		  return false;
		}
	 }
	 
	 return false;
  }

  @Override
  public String getDescription() {
    return "chippy files";
  }
}


	  
	  
