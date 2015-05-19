package view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.Box;
import javax.swing.border.EtchedBorder;
import javax.swing.JComboBox;

import Configuration.ConfigurationHandler;
import XBEE.XbeeSender;

import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class ConfigurationView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField URLfield;
	private JLabel lblLienDuServer;
	private JLabel lblPort;
	private ConfigurationHandler sonCH;
	private JSpinner PortField;
	private JComboBox<?> PortComField;
	private JLabel lblPortComEnCours;
	private JTextField adrLeap;

	private JSpinner portLeap;
	private JSpinner varLar;
	private JSpinner varLong;
	/*
	 * LD_LIBRARY_PATH=/usr/local/lib mjpg_streamer -i "/usr/local/lib/input_uvc.so -d /dev/video0 -f 29 -r 1280x720" -o "/usr/local/lib/output_http.so -w /usr/local/www -p 8080"
	 * 
	 */

	private mainWindow saVueP;

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ConfigurationView(mainWindow mainWindow) {
		
		saVueP = mainWindow ;
		setResizable(false);
		getContentPane().setLayout(null);
		setBounds(100, 100, 463, 560);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.sonCH = new ConfigurationHandler();
		contentPanel.setLayout(null);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		verticalBox.setBounds(21, 21, 400, 141);
		contentPanel.add(verticalBox);
		
		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		{
			lblLienDuServer = new JLabel("Lien du server : ");
			horizontalBox.add(lblLienDuServer);
		}
		{
			URLfield = new JTextField();
			horizontalBox.add(URLfield);
			URLfield.setColumns(10);
		}
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);
		{
			lblPort = new JLabel("Port :");
			horizontalBox_1.add(lblPort);
		}
		{
			PortField = new JSpinner();
			horizontalBox_1.add(PortField);
		}
		
		Box horizontalBox_6 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_6);
		
		JLabel lblLargeur_1 = new JLabel("Largeur :");
		horizontalBox_6.add(lblLargeur_1);
		
		 varLar = new JSpinner();
		horizontalBox_6.add(varLar);
		
		JLabel lblLargeur = new JLabel("Longeur : ");
		horizontalBox_6.add(lblLargeur);
		
		 varLong = new JSpinner();
		horizontalBox_6.add(varLong);
		{
			JLabel lblPartieCamra = new JLabel("Partie caméra :");
			lblPartieCamra.setBounds(21, 6, 109, 16);
			contentPanel.add(lblPartieCamra);
		}
		{
			JLabel lblPartieContrle = new JLabel("Partie contrôle ( Xbee) :");
			lblPartieContrle.setBounds(21, 186, 171, 16);
			contentPanel.add(lblPartieContrle);
		}
		
		Box verticalBox_1 = Box.createVerticalBox();
		verticalBox_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		verticalBox_1.setBounds(21, 213, 400, 87);
		contentPanel.add(verticalBox_1);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		verticalBox_1.add(horizontalBox_2);
		{
			JLabel lblPortCom = new JLabel("Port com :");
			horizontalBox_2.add(lblPortCom);
		}
		{
			 PortComField = new JComboBox();
			horizontalBox_2.add(PortComField);
		}
		
		Box horizontalBox_3 = Box.createHorizontalBox();
		verticalBox_1.add(horizontalBox_3);
		
		JLabel lblPortComActuel = new JLabel("Port com actuel  :    ");
		lblPortComActuel.setHorizontalAlignment(SwingConstants.LEFT);
		horizontalBox_3.add(lblPortComActuel);
		
		
		lblPortComEnCours = new JLabel("XX");
		lblPortComEnCours.setHorizontalAlignment(SwingConstants.LEFT);
		horizontalBox_3.add(lblPortComEnCours);
		

		
		//Remplissage port com
		List<String> lesPortsCom = XbeeSender.listSerialPort2();
		this.PortComField.setModel(new DefaultComboBoxModel(lesPortsCom.toArray()));
		
		JLabel lblPartieLeapmotion = new JLabel("Partie LeapMotion : ");
		lblPartieLeapmotion.setBounds(21, 312, 171, 16);
		contentPanel.add(lblPartieLeapmotion);
		
		Box verticalBox_2 = Box.createVerticalBox();
		verticalBox_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		verticalBox_2.setBounds(21, 336, 400, 141);
		contentPanel.add(verticalBox_2);
		
		Box horizontalBox_4 = Box.createHorizontalBox();
		verticalBox_2.add(horizontalBox_4);
		
		JLabel lblAdresse = new JLabel("Adresse : \n( XXX.XXX.XXX.XXX )");
		horizontalBox_4.add(lblAdresse);
		
		adrLeap = new JTextField();
		adrLeap.setColumns(10);
		horizontalBox_4.add(adrLeap);
		
		Box horizontalBox_5 = Box.createHorizontalBox();
		verticalBox_2.add(horizontalBox_5);
		
		JLabel label_1 = new JLabel("Port :");
		horizontalBox_5.add(label_1);
		
		 portLeap = new JSpinner();
		horizontalBox_5.add(portLeap);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton Sauvegarder = new JButton("Sauvegarder");
				Sauvegarder.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {

						List<String> lesParams = new ArrayList<String>();
						lesParams.add(URLfield.getText());
						lesParams.add(PortField.getValue().toString());
						lesParams.add(PortComField.getSelectedItem().toString());
						lesParams.add(adrLeap.getText());
						lesParams.add( portLeap.getValue().toString()  );
						lesParams.add(varLar.getValue().toString() ); 
						lesParams.add(varLong.getValue().toString());
						
						saVueP.changeP((int)varLar.getValue(), (int)varLong.getValue());
						
						
						sonCH.sauvegarde(lesParams);
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
				this.lblPortComEnCours.setText (lesOrigins.get(2));
				this.adrLeap.setText(lesOrigins.get(3));
				this.portLeap.setValue(Integer.parseInt(lesOrigins.get(4)) );
				this.varLar.setValue( Integer.parseInt(lesOrigins.get(5))  );
				this.varLong.setValue( Integer.parseInt(lesOrigins.get(6))  );

				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}
}
