package XBEE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jssc.SerialPortList;
import com.digi.xbee.api.Raw802Device;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.XBee16BitAddress;
import Exception.ErrorSnd;
import Exception.ErrorConnec;

public class XbeeSender {
	       	
Raw802Device myLocalXBeeDevice;

public static List<String> listSerialPort2()
{
    String[] portNames = SerialPortList.getPortNames();
    for(int i = 0; i < portNames.length; i++){
        System.out.println(portNames[i]);
    }
	

    List<String> s = new ArrayList<String>();
    Collections.addAll(s, portNames);
    return s;

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