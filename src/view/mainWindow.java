package view;

import javax.swing.JFrame;
import javax.swing.UIManager;

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
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


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
					
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // For use Ubuntu theme
					
		              // String                lookAndFeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
		              //  UIManager.setLookAndFeel(lookAndFeel);
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
		saConfig = new ConfigurationHandler();
		initialize();

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

		gestionFenetre.setBounds(100, 100, 525, 204 );
		gestionFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final mainWindow pt = this;

		gestionFenetre.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Configuration");
		btnNewButton.setBounds(6, 11, 131, 50);
		gestionFenetre.getContentPane().add(btnNewButton);

		btnConnect = new JButton("Connection");
		btnConnect.setBounds(6, 73, 131, 46);
		gestionFenetre.getContentPane().add(btnConnect);

		JButton btnNewButton_1 = new JButton("Quitter");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(6, 131, 131, 44);
		gestionFenetre.getContentPane().add(btnNewButton_1);

		JLabel lblEtatServeur = new JLabel("Etat des connexions ");
		lblEtatServeur.setBounds(149, 11, 131, 16);
		gestionFenetre.getContentPane().add(lblEtatServeur);

		 lblNa = new JLabel("N/A");
		lblNa.setBounds(248, 43, 159, 16);
		gestionFenetre.getContentPane().add(lblNa);

		JLabel lblServeurTcp = new JLabel("Robot : ");
		lblServeurTcp.setBounds(149, 43, 87, 16);
		gestionFenetre.getContentPane().add(lblServeurTcp);

		JLabel lblContrleTcp = new JLabel("Contrôle :");
		lblContrleTcp.setBounds(149, 73, 87, 16);
		gestionFenetre.getContentPane().add(lblContrleTcp);

		lblNbClientServer = new JLabel("N/A");
		lblNbClientServer.setBounds(248, 73, 159, 16);
		gestionFenetre.getContentPane().add(lblNbClientServer);
		
		slider_Vitesse = new JSlider();
		slider_Vitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
			    JSlider source = (JSlider)e.getSource();
				 if (!source.getValueIsAdjusting()) {
					 int value = (int)source.getValue();
					 lbl_VitesseMax.setText(Integer.toString(value)+"%");
					 saConfig.setVitesseMax(value);
					 if(RaspTcp != null)
					 RaspTcp.parametrage();
					 
				 }
			}
		});
		slider_Vitesse.setPaintLabels(true);
		slider_Vitesse.setBounds(286, 100, 190, 29);
		gestionFenetre.getContentPane().add(slider_Vitesse);
		
		slider_Arret = new JSlider();
		slider_Arret.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
			    JSlider source = (JSlider)e.getSource();
				 if (!source.getValueIsAdjusting()) {
					 int value = (int)source.getValue();
					lbl_Arret.setText(Integer.toString(value)+"cm");
					 saConfig.setDistanceArret(value);
					 if(RaspTcp != null)
					 RaspTcp.parametrage();
					 
				 }
			}
		});
		slider_Arret.setPaintLabels(true);
		slider_Arret.setBounds(286, 131, 190, 29);
		gestionFenetre.getContentPane().add(slider_Arret);
		
		JLabel lblVitesseMax = new JLabel("Vitesse maximal (%) :");
		lblVitesseMax.setBounds(144, 103, 136, 16);
		gestionFenetre.getContentPane().add(lblVitesseMax);
		
		JLabel lblArretcm = new JLabel("Distance d'arrêt (cm) : ");
		lblArretcm.setBounds(144, 132, 152, 16);
		gestionFenetre.getContentPane().add(lblArretcm);
		
		lbl_VitesseMax = new JLabel(this.saConfig.getVitesseMax()+"%");
		lbl_VitesseMax.setBounds(466, 103, 61, 16);
		gestionFenetre.getContentPane().add(lbl_VitesseMax);
		
		lbl_Arret = new JLabel(this.saConfig.getDistanceArret()+"cm");
		lbl_Arret.setBounds(466, 131, 61, 16);
		gestionFenetre.getContentPane().add(lbl_Arret);
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
	private JSlider slider_Vitesse;
	private JSlider slider_Arret;
	private JLabel lbl_VitesseMax;
	private JLabel lbl_Arret;


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
