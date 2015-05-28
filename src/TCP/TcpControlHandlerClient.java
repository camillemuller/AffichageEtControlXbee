package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpControlHandlerClient implements Runnable {

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

	public TcpControlHandlerClient(String ip,int port)
	{

		this.ip = ip ; 
		this._port = port;
	}


	public void send(String a)
	{
	
		if(_socket == null)
		{
			this.connect();
		}
		if(!_socket.isConnected())
		{
			this.connect();
		}
		_output.println(a);
		_output.flush();

	}
	
	
	public void connect()
	{
		try {
			_socket = new Socket(this.ip, this._port);
			_output = new PrintWriter( _socket.getOutputStream());
			_input = _socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	@Override
	public void run() {
		try
		{
			String userInput;
			
			if(_socket == null)
			{
				this.connect();
			}
			if(!_socket.isConnected())
			{
				this.connect();
			}
			
			// Open stream
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
