package TCP;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


class TcpServer  extends Thread
{
	private TcpControlHandlerClient tcpClient;

	private List<Client> sesClients;

	public TcpServer( TcpControlHandlerClient tcpClient)
	{
		this.tcpClient = tcpClient;
		this.sesClients = new ArrayList<Client>();
	}


	public void sendToclients(String message)
	{
		for(Client unClient : this.sesClients)
		{
			unClient.send(message);
		}
	}


	public void run()
	{

		try{
			ServerSocket welcomeSocket = new ServerSocket(5005);

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
}

class Client extends Thread
{
	private Socket connectionSocket;
	private String clientSentence;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private TcpControlHandlerClient tcpClient;


	public Client(Socket c, TcpControlHandlerClient tcpClient) throws IOException
	{
		connectionSocket = c;
		this.tcpClient = tcpClient;
	}

	public void send(String message) {
		// TODO Auto-generated method stub
		try {
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
			clientSentence = inFromClient.readLine();
			tcpClient.send(clientSentence);
		}
		catch(IOException e)
		{
			System.out.println("Erreur: " + e);
		}
	}
}