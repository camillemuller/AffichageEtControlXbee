package view;

import javax.swing.JFrame;

import Configuration.ConfigurationHandler;
import Configuration.JSonRaspParser;
import TCPCONTROLER.ClientRaspberryPI;
import TCPCONTROLER.ClientRaspberryPiListener;
import TCPCONTROLER.ServerControlHandler;
import TCPCONTROLER.ServerControlListener;
import com.charliemouse.cambozola.Viewer;
import javax.swing.JLabel;
import java.awt.EventQueue;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class mainWindow {

	private JFrame gestionFenetre;
	private JFrame PanelVoiture;
	private Viewer leViewer;
	private ConfigurationHandler saConfig;
	private  JButton btnConnect;
	private JLabel lblNa;
	private JLabel lblNbClientServer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow window = new mainWindow();
					window.gestionFenetre.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainWindow() {
		initialize();
		saConfig = new ConfigurationHandler();

	}




	public void changeP(int lar,int lon)
	{
		gestionFenetre.setBounds(100, 100, lar, lon);
		gestionFenetre.setVisible(false);
		gestionFenetre.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		gestionFenetre = new JFrame();
		gestionFenetre.setTitle("Affichage caméra et retransmission RC");
		gestionFenetre.setResizable(false);
		gestionFenetre.setAlwaysOnTop(true);

		gestionFenetre.setBounds(100, 100, 538, 122 );
		gestionFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final mainWindow pt = this;

		gestionFenetre.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Configuration");
		btnNewButton.setBounds(6, 6, 131, 29);
		gestionFenetre.getContentPane().add(btnNewButton);

		btnConnect = new JButton("Connection");
		btnConnect.setBounds(6, 38, 131, 29);
		gestionFenetre.getContentPane().add(btnConnect);

		JButton btnNewButton_1 = new JButton("Quitter");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(6, 68, 131, 29);
		gestionFenetre.getContentPane().add(btnNewButton_1);

		JLabel lblEtatServeur = new JLabel("Etat des connexions ");
		lblEtatServeur.setBounds(235, 11, 131, 16);
		gestionFenetre.getContentPane().add(lblEtatServeur);

		 lblNa = new JLabel("N/A");
		lblNa.setBounds(340, 43, 159, 16);
		gestionFenetre.getContentPane().add(lblNa);

		JLabel lblServeurTcp = new JLabel("Robot : ");
		lblServeurTcp.setBounds(235, 43, 87, 16);
		gestionFenetre.getContentPane().add(lblServeurTcp);

		JLabel lblContrleTcp = new JLabel("Contrôle :");
		lblContrleTcp.setBounds(235, 73, 87, 16);
		gestionFenetre.getContentPane().add(lblContrleTcp);

		lblNbClientServer = new JLabel("N/A");
		lblNbClientServer.setBounds(340, 73, 159, 16);
		gestionFenetre.getContentPane().add(lblNbClientServer);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnConnect.getText() == "Connection")
				{
					connection();
					btnConnect.setText("Déconnection");
				}
				else
				{
					disconnect();
					btnConnect.setText("Connection");
				}
				//Connection
			}});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfigurationView sonConfigView = new ConfigurationView(pt,leViewer);
				changeVisibility(false);
				sonConfigView.setVisible(true);

			}
		});


	}

	ClientRaspberryPI RaspTcp;
	ServerControlHandler ServerControl;


	public void connection()
	{

		// Caméra
		try{

			String[] lesParams = {saConfig.getUrlCamera(),Integer.toString( saConfig.getPortCamera())};
			leViewer = new Viewer();
			leViewer.test(lesParams);
			leViewer.setSize(saConfig.getHauteurCamera(),saConfig.getLargeurCamera());
			leViewer.setAlwaysOnTop(true);
			RaspTcp = new ClientRaspberryPI(this.saConfig);					
			ServerControl = new ServerControlHandler(saConfig.getPortLeap(),RaspTcp);
			ServerControl.setSonListener(new ServerControlListener()
			{

				@Override
				public void nbClient(int nbClient) {
					// TODO Auto-generated method stub
					lblNbClientServer.setText(Integer.toString(nbClient)+" appareil(s) connecté(s)");
				}
				
			});
			
			//Lancement du serveur TCP (Contrôle du robot)
			ServerControl.start();
			// Lancement du thread TCP
			final VoiturePanel VoitureP = afficheVoiture();
			/*Recupération d'informations venant du rasp*/
			//{"informations":{"distance":30}}
			ClientRaspberryPiListener lstRsp = new ClientRaspberryPiListener(){

				public void onReceive(String userInput)
				{
					JSonRaspParser.getParamsDistanceArriereAvant(userInput);
					VoitureP.changeDistance(JSonRaspParser.getAvant(),JSonRaspParser.getArriere());
				}

				@Override
				public void stats(String string) {
					// TODO Auto-generated method stub
					//JOptionPane.showMessageDialog(null, "Perte de connexion", string,JOptionPane.ERROR_MESSAGE);	
					lblNa.setText(string);	
				}
			};

	
			RaspTcp.setOnTcpControlHandlerListener(lstRsp);
			(new Thread(RaspTcp)).start();
		}catch(Exception ee){
			ee.printStackTrace();
		}
		//Debug affichage cam

	}

	public void disconnect()
	{

		this.PanelVoiture.dispose();
		this.leViewer.dispose();
		try
		{

			RaspTcp.arret();
		}catch(Exception e)
		{
			System.out.println("Connection raspberry supprimer");
		}

		try
		{
			ServerControl.arret();

		}catch(Exception e)
		{

		}
	}

	public VoiturePanel afficheVoiture()
	{
		PanelVoiture = new JFrame();
		PanelVoiture.setTitle("Capteur voiture");
		final VoiturePanel VoitureP = new VoiturePanel();
		PanelVoiture.getContentPane().add(VoitureP);
		PanelVoiture.setVisible(true);
		PanelVoiture.setBounds(gestionFenetre.getX()+gestionFenetre.getHeight()+100,gestionFenetre.getY()+gestionFenetre.getWidth()-700, 200, 280);
		PanelVoiture.setAlwaysOnTop(true);
		PanelVoiture.setResizable(false);
		PanelVoiture.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		return VoitureP;
	}



	public void changeVisibility(boolean visible)
	{
		this.gestionFenetre.setVisible(visible);
		
		if(this.btnConnect.getText() == "Déconnection")
		{
		this.PanelVoiture.setVisible(visible);
		leViewer.setVisible(visible);
		leViewer.changevisibility(visible);
		}

	}
}
