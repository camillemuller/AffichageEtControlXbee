package view;

import javax.swing.JFrame;

import Configuration.ConfigurationHandler;
import TCP.TcpControlHandlerClient;
import TCP.TcpControlListener;

import com.charliemouse.cambozola.Viewer;

import javax.swing.JLabel;

import java.awt.Color;
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

		final ConfigurationHandler leCh = new ConfigurationHandler();
		List<String> lesP =new ArrayList<String>();
		try {
			lesP = leCh.getSesparams();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//Default parameter 
			lesP.add("http://192.168.1.1/?action=stream");
			lesP.add("8080");
			lesP.add("/dev/USB0");
			lesP.add("192.168.1.1");
			lesP.add("80");
			lesP.add("800");
			lesP.add("600");
		}

		frmAffichageCamraEt = new JFrame();
		frmAffichageCamraEt.setTitle("Affichage caméra et retransmission xBee");
		frmAffichageCamraEt.setResizable(false);
		frmAffichageCamraEt.setBounds(100, 100, Integer.parseInt( lesP.get(5)), Integer.parseInt( lesP.get(6)) );

		//frmAffichageCamraEt.setBounds(100, 100, 564, 498 );
		frmAffichageCamraEt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAffichageCamraEt.getContentPane().setLayout(new BorderLayout(0, 0));



		final Viewer leViewer = new Viewer();
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

		
		
		
		//rdbtnWifiTcp.seti
		//JRadioButton rdbtnLeapMotion = new JRadioButton("Leap motion");
		//verticalBox.add(rdbtnLeapMotion);

	//	JLabel lblVoiture = new JLabel("Voiture : ");
	//	verticalBox_1.add(lblVoiture);


		PanelVoiture = new JFrame();
		PanelVoiture.setTitle("Capteur voiture");
		final VoiturePanel VoitureP = new VoiturePanel();

		PanelVoiture.add(VoitureP);
		PanelVoiture.setVisible(true);



		PanelVoiture.setBounds(frmAffichageCamraEt.getX()+frmAffichageCamraEt.getHeight()+100,frmAffichageCamraEt.getY()+frmAffichageCamraEt.getWidth()-350, 200, 280);
		PanelVoiture.setAlwaysOnTop(true);
		PanelVoiture.setResizable(false);
		PanelVoiture.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);



		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Caméra


				try{
					List<String> lesP =null;
					try {
						lesP = leCh.getSesparams();

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						//Default parameter 
						lesP.add("http://192.168.1.1/?action=stream");
						lesP.add("8080");
						lesP.add("/dev/USB0");
						lesP.add("192.168.1.1");
						lesP.add("80");
						lesP.add("800");
						lesP.add("600");
					}

					String[] lesParams = {lesP.get(0),lesP.get(1)};
					leViewer.test(lesParams, leViewer);
					rdbtnCamera.setSelected(true);


				//	final TcpControlHandlerClient LeapTcp = new TcpControlHandlerClient(lesP.get(3),));
					// Envoie des commandes vers le xBee pas encore dans un autre thread a corriger 

					final TcpControlHandlerClient RaspTcp = new TcpControlHandlerClient(lesP.get(2),Integer.parseInt( lesP.get(7)));

					
					final TcpServer ServerControl = new TcpServer(Integer.parseInt( lesP.get(4)),RaspTcp);
					
					ServerControl.start();
					// Lancement du thread TCP
					(new Thread(RaspTcp)).start();


					/*Recupération d'informations venant du rasp*/
					//{"informations":{"distance":30}}
					TcpControlListener lstRsp = new TcpControlListener(){


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


						//	VoitureP.changeDistance(avant,arriere);


						}

						@Override
						public void stats(int stats) {
							// TODO Auto-generated method stub
							
						}


					};

					RaspTcp.setOnTcpControlHandlerListener(lstRsp);

				}catch(Exception ee){
					rdbtnCamera.setSelected(false);
					ee.printStackTrace();
				}
				//Debug affichage cam


				btnConnect.setEnabled(false);

			}
		});
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
				sonConfigView.show();

			}
		});

	}
}
