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
	
	private  int    _portS;
	private  Socket _socketS;
	private String ipS;
	private PrintWriter _outputS;



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

		System.out.print(a);
		_output.println(a);
		_output.flush();

	}
	
	public void sendS(String a)
	{

		System.out.print(a);
		_outputS.println(a);
		_outputS.flush();

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
				//this.sonListenerTcp.onReceive(userInput);
				this.sendS(userInput);
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
	
	
	public void connectSender()
	{
		try {
			this._socketS = new Socket(this.ipS, this._portS);
			this._outputS = new PrintWriter( _socketS.getOutputStream());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSender(String IP, int PORT) {
		// TODO Auto-generated method stub
		

			this.ipS = IP;
			this._portS = PORT;
			this.connectSender();

	}

}
