package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.Box;
import javax.swing.JComboBox;

import Configuration.ConfigurationHandler;
import Configuration.IPAdressValidator;

import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;

import java.awt.Font;
import java.awt.Component;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



@SuppressWarnings("serial")
public class ConfigurationView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField URLfield;
	private JLabel lblLienDuServer;
	private JLabel lblPort;
	private ConfigurationHandler sonCH;
	private JSpinner PortField;

	private JSpinner portLeap;
	private JSpinner portRsp;
	private JSpinner varHauteur;
	private JSpinner varLarger;
	/*
	 * LD_LIBRARY_PATH=/usr/local/lib mjpg_streamer -i "/usr/local/lib/input_uvc.so -d /dev/video0 -f 29 -r 1280x720" -o "/usr/local/lib/output_http.so -w /usr/local/www -p 8080"
	 * 
	 */

	private mainWindow saVueP;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField ip_part1;
	private JTextField ip_part2;
	private JTextField ip_part3;
	private JTextField ip_part4;

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ConfigurationView(mainWindow mainWindow) {



		saVueP = mainWindow ;
		setResizable(false);
		getContentPane().setLayout(null);
		setBounds(100, 100, 502, 490);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.sonCH = new ConfigurationHandler();
		contentPanel.setLayout(null);



		//Remplissage port com

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 492, 429);
		contentPanel.add(tabbedPane);

		Box verticalBox = Box.createVerticalBox();
		tabbedPane.addTab("Partie Communcation et IHM", null, verticalBox, null);
		verticalBox.setBorder(null);

		JPanel panel = new JPanel();
		verticalBox.add(panel);
		panel.setLayout(null);
		{
			lblLienDuServer = new JLabel("Lien du server : ");
			lblLienDuServer.setBounds(6, 29, 100, 16);
			panel.add(lblLienDuServer);
		}
		{
			JLabel lblPartieCamra = new JLabel("Partie caméra :        ");
			lblPartieCamra.setBounds(6, 6, 132, 16);
			panel.add(lblPartieCamra);
			lblPartieCamra.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblPartieCamra.setFont(lblPartieCamra.getFont().deriveFont(lblPartieCamra.getFont().getStyle() | Font.BOLD));
		}
		{
			URLfield = new JTextField();
			URLfield.setToolTipText("http://....");
			URLfield.setBounds(118, 18, 349, 38);
			panel.add(URLfield);
			URLfield.setColumns(10);
			URLfield.setMaximumSize(new Dimension(400,100));

		}
		{
			PortField = new JSpinner();
			PortField.setBounds(118, 68, 94, 38);
			panel.add(PortField);
			PortField.setMaximumSize(new Dimension(100,100));

		}
		{
			lblPort = new JLabel("Port :");
			lblPort.setBounds(6, 79, 33, 16);
			panel.add(lblPort);
		}

		JLabel lblTypeEcran = new JLabel("Format :");
		lblTypeEcran.setBounds(6, 164, 52, 16);
		panel.add(lblTypeEcran);

		final JComboBox varFormat = new JComboBox();
		varFormat.setBounds(64, 145, 94, 56);
		panel.add(varFormat);
		varFormat.setModel(new DefaultComboBoxModel(new String[] {"4/3", "16/9", "16/10"}));

		JLabel lblLargeur_1 = new JLabel("Hauteur");
		lblLargeur_1.setBounds(157, 164, 55, 16);
		panel.add(lblLargeur_1);

		varHauteur = new JSpinner();
		varHauteur.setBounds(213, 147, 82, 50);
		panel.add(varHauteur);
		varHauteur.setMaximumSize(new Dimension(100,50));

		JLabel lblLargeur = new JLabel("Largeur");
		lblLargeur.setBounds(298, 164, 63, 16);
		panel.add(lblLargeur);

		varLarger = new JSpinner();
		varLarger.setBounds(373, 147, 82, 50);
		panel.add(varLarger);
		varLarger.setMaximumSize(new Dimension(100,50));

		JLabel lblRsolutionCamra = new JLabel("Résolution caméra : ");
		lblRsolutionCamra.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		lblRsolutionCamra.setBounds(6, 136, 128, 16);
		panel.add(lblRsolutionCamra);
		lblRsolutionCamra.setAlignmentX(Component.CENTER_ALIGNMENT);
		{
			JLabel lblPartieContrle = new JLabel("Partie contrôle ( Raspberry ) :");
			lblPartieContrle.setBounds(6, 210, 197, 16);
			panel.add(lblPartieContrle);
			lblPartieContrle.setAlignmentX(Component.LEFT_ALIGNMENT);
			lblPartieContrle.setAlignmentX(Component.LEFT_ALIGNMENT);
			lblPartieContrle.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		}

		portRsp = new JSpinner();
		portRsp.setMaximumSize(new Dimension(100, 100));
		portRsp.setBounds(51, 238, 93, 38);
		panel.add(portRsp);

		JLabel label_2 = new JLabel("Port :");
		label_2.setBounds(6, 253, 33, 9);
		panel.add(label_2);

		JLabel lblPartieLeapmotion = new JLabel("Partie LeapMotion : ");
		lblPartieLeapmotion.setBounds(5, 307, 133, 16);
		panel.add(lblPartieLeapmotion);
		lblPartieLeapmotion.setAlignmentX(Component.LEFT_ALIGNMENT);
		lblPartieLeapmotion.setHorizontalAlignment(SwingConstants.LEFT);
		lblPartieLeapmotion.setFont(new Font("Lucida Grande", Font.BOLD, 13));

		JLabel lblAdresse = new JLabel("Adresse : ");
		lblAdresse.setBounds(6, 343, 62, 16);
		panel.add(lblAdresse);

		JLabel label_1 = new JLabel("Port :");
		label_1.setBounds(283, 347, 33, 9);
		panel.add(label_1);

		portLeap = new JSpinner();
		portLeap.setBounds(326, 332, 77, 38);
		panel.add(portLeap);
		portLeap.setMaximumSize(new Dimension(100,100));

		JButton btnTesterLeLien = new JButton("Test");
		btnTesterLeLien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URL u;
				try {

					//TODO
					u = new URL("http://www.example.com/");
					HttpURLConnection huc = null;
					huc.setRequestMethod("GET"); 
					huc.connect() ; 
					OutputStream os = huc.getOutputStream(); 
					int code = huc.getResponseCode(); 

					System.out.println(code);

					huc = (HttpURLConnection)u.openConnection();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 


			}
		});
		btnTesterLeLien.setBounds(412, 77, 55, 29);
		panel.add(btnTesterLeLien);

		JButton button_1 = new JButton("Test");
		button_1.setBounds(415, 342, 52, 29);
		panel.add(button_1);

		NumberFormat longFormat = NumberFormat.getIntegerInstance();

		NumberFormatter numberFormatter = new NumberFormatter(longFormat);

		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setMinimum(0); //Optional
		numberFormatter.setMaximum(255);



		ip_part1 = new JFormattedTextField(numberFormatter);
		ip_part1.setBounds(75, 339, 41, 23);
		panel.add(ip_part1);
		ip_part1.setColumns(10);

		ip_part2 = new JFormattedTextField(numberFormatter);
		ip_part2.setColumns(10);
		ip_part2.setBounds(75+40, 339, 41, 23);
		panel.add(ip_part2);

		ip_part3 = new JFormattedTextField(numberFormatter);
		ip_part3.setColumns(10);
		ip_part3.setBounds(75+40*2, 339, 41, 23);
		panel.add(ip_part3);

		ip_part4 = new JFormattedTextField(numberFormatter);
		ip_part4.setColumns(10);
		ip_part4.setBounds(75+40*3, 339, 41, 23);
		panel.add(ip_part4);

		JLabel label = new JLabel(".");
		label.setBounds(114, 345, 9, 16);
		panel.add(label);

		JLabel label_3 = new JLabel(".");
		label_3.setBounds(154, 345, 9, 16);
		panel.add(label_3);

		JLabel label_4 = new JLabel(".");
		label_4.setBounds(193, 345, 9, 16);
		panel.add(label_4);

		Box TabRobot = Box.createVerticalBox();
		tabbedPane.addTab("Configuration Voiture", null, TabRobot, null);

		JPanel TabComIhm = new JPanel();
		TabRobot.add(TabComIhm);
		TabComIhm.setLayout(null);

		JLabel lblVitesseMaximal = new JLabel("Vitesse maximal (m) : ");
		lblVitesseMaximal.setBounds(35, 133, 154, 16);
		TabComIhm.add(lblVitesseMaximal);

		JLabel lblDistanceDarrt = new JLabel("Distance d'arrêt (m) : ");
		lblDistanceDarrt.setBounds(35, 231, 154, 16);
		TabComIhm.add(lblDistanceDarrt);

		textField = new JTextField();
		textField.setBounds(201, 127, 134, 28);
		TabComIhm.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(201, 225, 134, 28);
		TabComIhm.add(textField_1);

		varHauteur.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				switch(varFormat.getSelectedItem().toString())
				{
				case "4/3":
				{

					float lon= Float.parseFloat(  varHauteur.getValue().toString() ) ;

					lon = lon*(float)(3.0/4.0);

					varLarger.setValue(Math.round( lon)  );

					break;
				}
				case "16/9":
				{
					float lon= Float.parseFloat(  varHauteur.getValue().toString() ) ;

					lon = lon*(float)(9.0/16.0);

					varLarger.setValue(Math.round( lon)  );
					break;
				}
				case "16/10":
				{
					float lon= Float.parseFloat(  varHauteur.getValue().toString() ) ;

					lon = lon*(float)(10.0/16.0);

					varLarger.setValue(Math.round( lon)  );
					break;
				}
				}
			}
		});

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton Sauvegarder = new JButton("Sauvegarder");
				Sauvegarder.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						IPAdressValidator un = new IPAdressValidator();
							if(!un.validate(ip_part1.getText()+"."+ip_part2.getText()+"."+ip_part3.getText()+"."+ip_part4.getText() ))
						{
							JOptionPane.showMessageDialog(null, "Adresse IP non valide Raspberry", "Adresse IP Raspberry",JOptionPane.WARNING_MESSAGE);	
							return;
						}


						List<String> lesParams = new ArrayList<String>();
						lesParams.add(URLfield.getText());
						lesParams.add(PortField.getValue().toString());

						//TODO
						lesParams.add(ip_part1.getText()+"."+ip_part2.getText()+"."+ip_part3.getText()+"."+ip_part4.getText()); // Ancien serial port
						lesParams.add( portLeap.getValue().toString()  );
						lesParams.add(varHauteur.getValue().toString() ); 
						lesParams.add(varLarger.getValue().toString());
						lesParams.add(portRsp.getValue().toString());
						saVueP.changeP((int)varHauteur.getValue(), (int)varLarger.getValue());
						sonCH.sauvegarde(lesParams);

						saVueP.changeVisibility(true);
						dispose();
					}
				});
				Sauvegarder.setActionCommand("Sauvegarder");
				buttonPane.add(Sauvegarder);
				getRootPane().setDefaultButton(Sauvegarder);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						saVueP.changeVisibility(true);
						dispose();

					}
				});
				cancelButton.setActionCommand("Annuler");
				buttonPane.add(cancelButton);
			}
		}
		List<String> lesOrigins;
		try {
			lesOrigins = this.sonCH.getSesparams();
			if(!lesOrigins.isEmpty() && lesOrigins.size() == 7)
			{
				this.URLfield.setText(lesOrigins.get(0));
				this.PortField.setValue( Integer.parseInt(lesOrigins.get(1)) );
				String ip = lesOrigins.get(2);
				int index1dot =  ip.indexOf(".");
				this.ip_part1.setText(ip.substring(0,   index1dot   ));
				int index2dot = ip.indexOf(".",  ip.indexOf(".") +1);
				this.ip_part2.setText(ip.substring(index1dot,  index2dot    ));
				int index3dot = ip.indexOf(".",  index2dot +1);
				this.ip_part3.setText(ip.substring(index2dot,  index3dot    ));
				
				this.ip_part4.setText(ip.substring(index3dot,  ip.length()    ));
				
				this.portLeap.setValue(Integer.parseInt(lesOrigins.get(3)) );
				this.varHauteur.setValue( Integer.parseInt(lesOrigins.get(4))  );
				this.varLarger.setValue( Integer.parseInt(lesOrigins.get(5))  );
				this.portRsp.setValue( Integer.parseInt(lesOrigins.get(6))  );
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
}
