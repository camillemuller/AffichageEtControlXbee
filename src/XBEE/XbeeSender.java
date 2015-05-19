package XBEE;



import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.digi.xbee.api.Raw802Device;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.XBee16BitAddress;

import Exception.ErrorSnd;
import Exception.ErrorConnec;





public class XbeeSender {
	       
/*XBee xb;
String port;
XBeeAddress16 dst = new XBeeAddress16(0x00,0x01);
*/
	
Raw802Device myLocalXBeeDevice;


public static List<String> listSerialPort()
{
	List<String> lesPorts = new ArrayList<String>();
	
	  Enumeration<?> pList = CommPortIdentifier.getPortIdentifiers();

	    // Process the list.
	    while (pList.hasMoreElements()) {
	      CommPortIdentifier cpi = (CommPortIdentifier) pList.nextElement();
	      System.out.print("Port " + cpi.getName() + " ");
	      if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	        System.out.println("is a Serial Port: " + cpi);
	        lesPorts.add(cpi.getName());
	      } else if (cpi.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
	        System.out.println("is a Parallel Port: " + cpi);
	      } else {
	        System.out.println("is an Unknown Port: " + cpi);
	      }
	    }
	    
	return lesPorts;
}

public XbeeSender(String serialP)
{
		myLocalXBeeDevice = new Raw802Device(serialP, 115200);
}
	
public void connection() throws ErrorConnec
{
	try {
		myLocalXBeeDevice.open();
		
	} catch (XBeeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		throw new ErrorConnec();
	}
}


public void SendCmd(String cmd) throws ErrorConnec, ErrorSnd
{
	try {
		
		XBee16BitAddress myDest = new XBee16BitAddress("0002");
		// Send data using the remote object
		myLocalXBeeDevice.sendDataAsync(myDest, cmd.getBytes());
	} catch (XBeeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}