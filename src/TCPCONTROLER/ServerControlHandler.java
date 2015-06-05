package TCPCONTROLER;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


public class ServerControlHandler  extends Thread
{
	private ClientRaspberryPI tcpClient;

	private List<Client> sesClients;
	private int sonNumeroport;
	private ServerSocket welcomeSocket;

	public ServerControlHandler( int i, ClientRaspberryPI tcpClient)
	{
		this.sonNumeroport =i;
		this.tcpClient = tcpClient;
		this.sesClients = new ArrayList<Client>();
		
		
	}


	public void sendToclients(String message)
	{
		for(Client unClient : this.sesClients)
		{
			unClient.send(message+'\n');
		}
	}


	@SuppressWarnings("resource")
	public void run()
	{

		System.out.println("Lanchement serveur TCP ");
		try{
			welcomeSocket = new ServerSocket(sonNumeroport);
			
			while(true)
			{
				Socket connectionSocket = welcomeSocket.accept();
				if (connectionSocket != null)
				{
					Client client = new Client(connectionSocket,tcpClient);
					sesClients.add(client);
					client.start();
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		
		}
	}


	public void arret() {
		// TODO Auto-generated method stub
		
		System.out.println("start");
		for(Client unClient : this.sesClients)
		{			
			unClient.arret();
			unClient.stop();
		}
		this.stop();
		System.out.println("end");
		
		try {
			welcomeSocket.close();
			;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class Client extends Thread
{
	private Socket connectionSocket;
	private String clientSentence;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private ClientRaspberryPI tcpClient;
	
	public void arret()
	{
		
		try {
			this.outToClient.close();
			this.inFromClient.close();
			this.connectionSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public Client(Socket c, ClientRaspberryPI tcpClient) throws IOException
	{
		c.setTcpNoDelay(true);
		c.setPerformancePreferences(MAX_PRIORITY, MAX_PRIORITY, MAX_PRIORITY);
		connectionSocket = c;
		this.tcpClient = tcpClient;
		
	}

	public void send(String message) {
		// TODO Auto-generated method stub
		try {
			System.out.println("test");
			outToClient.writeBytes(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() 
	{
		try
		{    
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			
			while((clientSentence = inFromClient.readLine()) != null)
			{
			tcpClient.send(clientSentence);
			}
		}
		catch(IOException e)
		{
			System.out.println("Erreur: " + e);
		}
	}
}