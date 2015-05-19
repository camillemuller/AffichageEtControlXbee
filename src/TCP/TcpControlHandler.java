package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpControlHandler implements Runnable {

	private TcpControlListener sonListenerTcp;
	private static int    _port;
	private static Socket _socket;
	
	
	public void setOnTcpControlHandlerListener(TcpControlListener unListener)
	{
		this.sonListenerTcp = unListener;
	}

	public TcpControlHandler()
	{

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		InputStream input   = null;

		try
		{
			_port   = 23;
			// si pas ip alors boucle local
			_socket = new Socket("192.168.0.15", _port);

			// Open stream
			input = _socket.getInputStream();

			// Show the server response
			BufferedReader response = new BufferedReader(new InputStreamReader(input));


			String userInput;

			while ((userInput = response.readLine()) != null) 
			{
				this.sonListenerTcp.onReceive(userInput);
			}


			System.out.println("Server message: " + response);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				input.close();
				_socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
