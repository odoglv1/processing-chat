package serverc;

import java.io.UnsupportedEncodingException;

import processing.core.PApplet;
import processing.net.*;

public class ServerC extends PApplet {
	
	//TODO: Comment this code.
	
	public int port = 10002;
	public boolean myServerRunning = true;
	public int bgColor = 0;
	public int direction = 1;
	public int textLine = 95;

	public Server myServer;
	
	public String allData = "[DATA START]";
	
	public String permanentMsg;

	public void setup()
	{
	  myServer = new Server(ServerC.this, port); // Starts a myServer on port 10002
	  background(0);
	}
	
	public void settings() {
		size(400, 400);
	}

	public void draw()
	{
	  if (myServerRunning == true)
	  {
		background(0);
	    text("[SERVER RUNNING]", 15, 45);
	    text("Connect to: " + Server.ip(), 15, 65);
	    int tCl = myServer.clientCount;
	    Client thisClient = myServer.available();
	    if (tCl > 0) {
	    	if (thisClient != null) {
	  	      if (thisClient.available() > 0) {
	  	    	try {
	  	    		String Amsg = new String(thisClient.readBytes(), "UTF-8");
	  	    		String name = Amsg.split(";")[0].toString();
	  	    		String Bmsg = name + " says: " + Amsg.substring(name.length() + 1);
		  	    	String msg = Bmsg;
		  	        allData = allData + "\n" + msg;
		  		    try {
		  				myServer.write(allData.getBytes("UTF-8"));
		  			} catch (UnsupportedEncodingException e) {
		  				e.printStackTrace();
		  			}
	  	    	} catch (Exception exception) {
	  	    		exception.printStackTrace();
	  	    	}
	  	      }
	  	    }
	    }
	    //TODO cause this section of code below to run, but only every 30th time or so. Use an int counter for this.
//	    try {
//			myServer.write(allData.getBytes("UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	    text(allData, 15, 95);
	  }
	  else 
	  {
		background(0);
	    text("[SERVER STOPPED]", 15, 45);
	  }
	}
	
	public void keyPressed() {
		if (key == 's') {
			myServer.stop();
			myServerRunning = false;
		}
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { serverc.ServerC.class.getName() });
	}
}