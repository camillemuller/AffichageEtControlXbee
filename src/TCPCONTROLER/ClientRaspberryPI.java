package TCPCONTROLER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Configuration.ConfigurationHandler;

public class ClientRaspberryPI implements Runnable {

	private ClientRaspberryPiListener sonListenerTcp;
	private  Socket _socket;
	private PrintWriter _output;
	private InputStream _input;
	private BufferedReader _response;
	private ConfigurationHandler saConfig;


	public void setOnTcpControlHandlerListener(ClientRaspberryPiListener unListener)
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

	public ClientRaspberryPI(ConfigurationHandler saConfig)
	{
		this.saConfig = saConfig;
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
			_socket = new Socket(this.saConfig.getIpRasp(), this.saConfig.getPortRsp());
			this._socket.setTcpNoDelay(true);
			this._socket.setPerformancePreferences(10, 10,10);
			_output = new PrintWriter( _socket.getOutputStream());
			_input = _socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//Renvoie erreur connexion ihm
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

			//Init envoie des parametres vers le Rasp
			this.parametrage();

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

	public void parametrage()
	{
		this.send("{\"informations\":{\"distance_avant\":30,\"distance_arriere\":50}}");
	}

	public void arret() {
		// TODO Auto-generated method stub

	}




}
