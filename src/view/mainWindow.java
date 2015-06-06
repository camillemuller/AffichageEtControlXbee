package view;

import javax.swing.JFrame;

import Configuration.ConfigurationHandler;
import TCPCONTROLER.ClientRaspberryPI;
import TCPCONTROLER.ClientRaspberryPiListener;
import TCPCONTROLER.ServerControlHandler;

import com.charliemouse.cambozola.Viewer;

import javax.swing.JLabel;

import java.awt.EventQueue;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class mainWindow {

	private JFrame gestionFenetre;
	private JFrame PanelVoiture;
	private Viewer leViewer;
	private ConfigurationHandler saConfig;

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
		gestionFenetre.setTitle("Affichage caméra et retransmission xBee");
		gestionFenetre.setResizable(false);
		gestionFenetre.setAlwaysOnTop(true);

		gestionFenetre.setBounds(100, 100, 414, 171 );
		gestionFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final mainWindow pt = this;

		gestionFenetre.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Configuration");
		btnNewButton.setBounds(6, 6, 131, 29);
		gestionFenetre.getContentPane().add(btnNewButton);

		final JButton btnConnect = new JButton("CONNECT");
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

		JLabel lblNa = new JLabel("N/A");
		lblNa.setBounds(340, 43, 26, 16);
		gestionFenetre.getContentPane().add(lblNa);

		JLabel lblServeurTcp = new JLabel("Robot : ");
		lblServeurTcp.setBounds(235, 43, 87, 16);
		gestionFenetre.getContentPane().add(lblServeurTcp);

		JLabel lblContrleTcp = new JLabel("Contrôle :");
		lblContrleTcp.setBounds(235, 73, 87, 16);
		gestionFenetre.getContentPane().add(lblContrleTcp);

		JLabel label_1 = new JLabel("N/A");
		label_1.setBounds(340, 73, 26, 16);
		gestionFenetre.getContentPane().add(label_1);

		JButton btnNewButton_2 = new JButton("Affichage caméra ");
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(6, 101, 192, 42);
		gestionFenetre.getContentPane().add(btnNewButton_2);

		JButton btnAffichageVoiture = new JButton("Affichage voiture");
		btnAffichageVoiture.setEnabled(false);
		btnAffichageVoiture.setBounds(210, 101, 192, 42);
		gestionFenetre.getContentPane().add(btnAffichageVoiture);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnConnect.getText() == "CONNECT")
				{
					connection();
					btnConnect.setText("DISCONNECT");
				}
				else
				{
					disconnect();
					btnConnect.setText("CONNECT");
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
			//Lancement du serveur TCP (Contrôle du robot)
			ServerControl.start();
			// Lancement du thread TCP
			(new Thread(RaspTcp)).start();
			final VoiturePanel VoitureP = afficheVoiture();
			/*Recupération d'informations venant du rasp*/
			//{"informations":{"distance":30}}
			ClientRaspberryPiListener lstRsp = new ClientRaspberryPiListener(){

				public void onReceive(String userInput)
				{

					System.out.println("User input "+userInput);

					//{"informations":{"distance":30}}

					JSONParser parser = new JSONParser();
					JSONParser parserArr = new JSONParser();

					KeyFinder finderAvant = new KeyFinder();
					KeyFinder finderArriere = new KeyFinder();

					String avant= "";
					String arriere = "";

					finderAvant.setMatchKey("distance_avant");
					try{
						while(!finderAvant.isEnd()){
							parser.parse(userInput, finderAvant, true);
							if(finderAvant.isFound()){
								finderAvant.setFound(false);
								System.out.println("found id:");
								avant =  Long.toString((long) finderAvant.getValue());
							}
						}           
					}
					catch(ParseException pe){
						pe.printStackTrace();
					}
					finderArriere.setMatchKey("distance_arriere");
					try{
						while(!finderArriere.isEnd()){
							parserArr.parse(userInput, finderArriere, true);
							if(finderArriere.isFound()){
								finderArriere.setFound(false);
								System.out.println("found id:");
								arriere =Long.toString((long) finderArriere.getValue());
							}
						}           
					}
					catch(ParseException pe){
						pe.printStackTrace();
					}
					VoitureP.changeDistance(avant,arriere);


				}

				@Override
				public void stats(int stats) {
					// TODO Auto-generated method stub

				}


			};

			RaspTcp.setOnTcpControlHandlerListener(lstRsp);

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

		
		this.PanelVoiture.setVisible(visible);
		leViewer.setVisible(visible);
		leViewer.changevisibility(visible);

	}
}
