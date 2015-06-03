package view;

import javax.swing.JFrame;

import Configuration.ConfigurationHandler;
import TCPCONTROLER.ClientRaspberryPI;
import TCPCONTROLER.ClientRaspberryPiListener;
import TCPCONTROLER.ServerControlHandler;

import com.charliemouse.cambozola.Viewer;

import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JRadioButton;
import javax.swing.Box;
import javax.swing.border.EtchedBorder;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.BorderLayout;


public class mainWindow {

	private JFrame frmAffichageCamraEt;
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
					window.frmAffichageCamraEt.setVisible(true);
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
		frmAffichageCamraEt.setBounds(100, 100, lar, lon);
		frmAffichageCamraEt.setVisible(false);
		frmAffichageCamraEt.setVisible(true);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {



		frmAffichageCamraEt = new JFrame();
		frmAffichageCamraEt.setTitle("Affichage caméra et retransmission xBee");
		frmAffichageCamraEt.setResizable(false);
		//frmAffichageCamraEt.setBounds(100, 100, Integer.parseInt( lesP.get(4)), Integer.parseInt( lesP.get(5)) );

		//Debug
		frmAffichageCamraEt.setBounds(100, 100, 564, 498 );
		frmAffichageCamraEt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAffichageCamraEt.getContentPane().setLayout(new BorderLayout(0, 0));
		leViewer = new Viewer();
		leViewer.setLayout(new GridLayout(1, 0, 0, 0));
		frmAffichageCamraEt.getContentPane().add(leViewer);

		Box verticalBox_1 = Box.createVerticalBox();
		frmAffichageCamraEt.getContentPane().add(verticalBox_1, BorderLayout.EAST);

		JButton btnNewButton = new JButton("Configuration");
		verticalBox_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Fermer");
		verticalBox_1.add(btnNewButton_1);

		final JButton btnConnect = new JButton("CONNECT");
		verticalBox_1.add(btnConnect);

		JLabel lblEtat = new JLabel("Etat :");
		verticalBox_1.add(lblEtat);


		Box verticalBox = Box.createVerticalBox();
		verticalBox_1.add(verticalBox);
		verticalBox.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		final JRadioButton rdbtnCamera = new JRadioButton("Camera");
		verticalBox.add(rdbtnCamera);
		JRadioButton rdbtnWifiTcp = new JRadioButton("Wifi tcp");
		verticalBox.add(rdbtnWifiTcp);

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
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		final mainWindow pt = this;
		btnNewButton.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfigurationView sonConfigView = new ConfigurationView(pt);
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
			leViewer.test(lesParams, leViewer);
			RaspTcp = new ClientRaspberryPI(saConfig.getIpRasp(),saConfig.getPortRsp());					
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
		PanelVoiture.setBounds(frmAffichageCamraEt.getX()+frmAffichageCamraEt.getHeight()+100,frmAffichageCamraEt.getY()+frmAffichageCamraEt.getWidth()-350, 200, 280);
		PanelVoiture.setAlwaysOnTop(true);
		PanelVoiture.setResizable(false);
		PanelVoiture.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		return VoitureP;
	}



	public void changeVisibility(boolean visible)
	{
		this.frmAffichageCamraEt.setVisible(visible);
	if(this.PanelVoiture != null)	
		this.PanelVoiture.setVisible(visible);
	}
}
