package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpControlHandler implements Runnable {

	private TcpControlListener sonListenerTcp;
	private  int    _port;
	private  Socket _socket;
	private String ip;
	private PrintWriter _output;
	private InputStream _input;
	private BufferedReader _response;
	



	public void setOnTcpControlHandlerListener(TcpControlListener unListener)
	{
		this.sonListenerTcp = unListener;

	}

	public void close(){

		try {
			_response.close();
			_output.close();
			_socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public TcpControlHandler(String ip,int port)
	{

		try {
			this.ip = ip ; 
			this._port = port;
			_socket = new Socket(this.ip, this._port);
			_output = new PrintWriter( _socket.getOutputStream());


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void send(String a)
	{
		_output.println(a);
		_output.flush();

	}
	
	

	@Override
	public void run() {
		try
		{
			String userInput;
			
			

			// Open stream
			_input = _socket.getInputStream();
			_response = new BufferedReader(new InputStreamReader(_input));


			
			while ((userInput = _response.readLine()) != null) 
			{
				this.sonListenerTcp.onReceive(userInput);
			}


			System.out.println("Server message: " + _response);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	


}
