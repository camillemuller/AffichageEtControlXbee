package TCPCONTROLER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import Configuration.ConfigurationHandler;

/**
 * Permet la gestion de la connexion vers le Raspberry Pi 
 * C'est une classe qui implémente Runnable, c'est a dire qu'elle est threadable ( run() )
 * 
 * @author camillemuller
 *
 */
public class ClientRaspberryPI implements Runnable {

	private ClientRaspberryPiListener sonListenerTcp;
	private  Socket _socket;
	private PrintWriter _output;
	private InputStream _input;
	private BufferedReader _response;
	private ConfigurationHandler saConfig;
	private HeartBit sonHt;
	
	
	public static boolean testConnexionClientTcp()
	{
		return true;
	}


	/**
	 * Permet de parametré le listener de la classe 
	 * @param unListener le listener de la classe ClientRaspberryPiListener 
	 */
	public void setOnTcpControlHandlerListener(ClientRaspberryPiListener unListener)
	{
		this.sonListenerTcp = unListener;

	}


	/**
	 * Fermeture de la connexion
	 */
	public void close(){

		try {

			_socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * Constructeur de la classe
	 * @param saConfig doit récupérer un pointeur vers une instance de ConfigurationHandler pour pouvoir ce configurer
	 */
	public ClientRaspberryPI(ConfigurationHandler saConfig)
	{
		this.saConfig = saConfig;
	}


	/**
	 * Envoie de données vers le Raspberry pi quand la connexion est effective. ( connect() ) 
	 * @param a
	 */
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


	/**
	 * Permet de créer une connexion vers le Raspberry Pi
	 */
	public void connect()
	{
		try {
			_socket = new Socket(this.saConfig.getIpRasp(), this.saConfig.getPortRsp());
			this._socket.setTcpNoDelay(true);
			this._socket.setPerformancePreferences(10, 10,10);
			_output = new PrintWriter( _socket.getOutputStream());
			_input = _socket.getInputStream();
			sonHt = new HeartBit(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			//Renvoie erreur connexion ihm
		}



	}

	@Override
	/**
	 * C'est le thread de la classe 
	 * A l'intérieur du thread sera gérer la récuperation des données envoyer par le Raspberry pi
	 */
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


			this.sonListenerTcp.stats("Connexion ouverte");	

			//Init envoie des parametres vers le Rasp
			this.parametrage();

			sonHt.start();

			// Open stream
			_response = new BufferedReader(new InputStreamReader(_input));
			while ( ((userInput = _response.readLine()) != null)) 
			{
				this.sonListenerTcp.onReceive(userInput);
			}


			sonHt.setRun(false);
			this.sonListenerTcp.stats("Connexion perdu");	

		}
		catch(SocketException e )
		{
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Permet d'envoyer au raspberry pi 
	 */
	public void parametrage()
	{
		if(this._socket != null) 
			if(this._socket.isConnected())
			{	
				String cmd  ="{\"configuration\":{\"distance_arret\":"+this.saConfig.getDistanceArret()+",\"vitesse_max\":"+this.saConfig.getVitesseMax()+"}}\n" ; 
				this.send(cmd);
			}

	}



	/**
	 * Arret de la connexion
	 */
	public void arret() {
		// TODO Auto-generated method stub

		this.close();
		this.sonListenerTcp.stats("Connexion fermée");	

	}




}
