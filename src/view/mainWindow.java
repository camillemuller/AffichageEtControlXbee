package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Configuration.ConfigurationHandler;
import Exception.ErrorConnec;
import Exception.ErrorSnd;
import TCP.TcpControlHandler;
import TCP.TcpControlListener;
import XBEE.XbeeSender;

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

import java.awt.BorderLayout;


public class mainWindow {

	private JFrame frmAffichageCamraEt;
	private Viewer leViewer;

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
		frmAffichageCamraEt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAffichageCamraEt.getContentPane().setLayout(new BorderLayout(0, 0));
		


		final JPanel panel_cam = new JPanel();
		panel_cam.setLayout(new GridLayout(1, 0, 0, 0));
		frmAffichageCamraEt.getContentPane().add(panel_cam);
		
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
		JRadioButton rdbtnWifiTcp = new JRadioButton("Wifi tcp");
		verticalBox.add(rdbtnWifiTcp);
		
		JRadioButton rdbtnXbee = new JRadioButton("Xbee");
		verticalBox.add(rdbtnXbee);
		
		final JRadioButton rdbtnCamera = new JRadioButton("Camera");
		verticalBox.add(rdbtnCamera);
		
		JRadioButton rdbtnLeapMotion = new JRadioButton("Leap motion");
		verticalBox.add(rdbtnLeapMotion);
		
		
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
				leViewer = new Viewer(panel_cam);
				leViewer.test(lesParams, panel_cam);
				rdbtnCamera.setSelected(true);
				
				String leP2 = lesP.get(2);
				final XbeeSender leSender = new XbeeSender(leP2);
				
				TcpControlHandler trd = new TcpControlHandler();
				// Envoie des commandes vers le xBee pas encore dans un autre thread a corriger 
				TcpControlListener leListener = new TcpControlListener()
				{

					@Override
					public void onReceive(String userInput) {
						// TODO Auto-generated method stu
							try {
								leSender.SendCmd(userInput);
							} catch (ErrorConnec e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ErrorSnd e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

					}
					
				};
				
				trd.setOnTcpControlHandlerListener(leListener);
				// Lancement du thread TCP
				(new Thread(trd)).start();

				leSender.connection();
				
				}catch(Exception ee){
					rdbtnCamera.setSelected(false);
					ee.printStackTrace();
				}
				//Debug affichage cam
				panel_cam.setVisible(false);
				panel_cam.setVisible(true);
						
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
