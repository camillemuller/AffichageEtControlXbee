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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.Box;
import javax.swing.border.EtchedBorder;
import javax.swing.JComboBox;

import Configuration.ConfigurationHandler;
import Configuration.IPAdressValidator;
import XBEE.XbeeSender;

import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;

import java.awt.Font;

import javax.swing.border.TitledBorder;

import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.text.MaskFormatter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;


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
	private JFormattedTextField adrLeap;

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
		setBounds(100, 100, 504, 549);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.sonCH = new ConfigurationHandler();
		contentPanel.setLayout(null);



		//Remplissage port com
		List<String> lesPortsCom = XbeeSender.listSerialPort2();

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 495, 490);
		contentPanel.add(tabbedPane);

		Box verticalBox = Box.createVerticalBox();
		tabbedPane.addTab("New tab", null, verticalBox, null);
		verticalBox.setBorder(null);

		Box verticalBox_3 = Box.createVerticalBox();
		verticalBox_3.setAlignmentX(CENTER_ALIGNMENT);

		verticalBox_3.setMaximumSize(new Dimension(450,200));

		verticalBox_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		verticalBox.add(verticalBox_3);
		{
			JLabel lblPartieCamra = new JLabel("Partie caméra :        ");
			lblPartieCamra.setAlignmentX(Component.CENTER_ALIGNMENT);
			verticalBox_3.add(lblPartieCamra);
			lblPartieCamra.setFont(lblPartieCamra.getFont().deriveFont(lblPartieCamra.getFont().getStyle() | Font.BOLD));
		}

		Box horizontalBox = Box.createHorizontalBox();
		verticalBox_3.add(horizontalBox);
		{
			lblLienDuServer = new JLabel("Lien du server : ");
			horizontalBox.add(lblLienDuServer);
		}
		{
			URLfield = new JTextField();
			horizontalBox.add(URLfield);
			URLfield.setColumns(10);
			URLfield.setMaximumSize(new Dimension(400,100));

		}

		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox_3.add(horizontalBox_1);
		{
			lblPort = new JLabel("Port :");
			horizontalBox_1.add(lblPort);
		}
		{
			PortField = new JSpinner();
			horizontalBox_1.add(PortField);
			PortField.setMaximumSize(new Dimension(100,100));

		}

		JLabel lblRsolutionCamra = new JLabel("Résolution caméra : ");
		lblRsolutionCamra.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox_3.add(lblRsolutionCamra);

		Box horizontalBox_6 = Box.createHorizontalBox();
		horizontalBox_6.setMaximumSize(new Dimension(450,100));
		verticalBox_3.add(horizontalBox_6);

		JLabel lblTypeEcran = new JLabel("Format :");
		horizontalBox_6.add(lblTypeEcran);

		final JComboBox varFormat = new JComboBox();
		varFormat.setModel(new DefaultComboBoxModel(new String[] {"4/3", "16/9", "16/10"}));
		horizontalBox_6.add(varFormat);

		JLabel lblLargeur_1 = new JLabel("Largeur :");
		horizontalBox_6.add(lblLargeur_1);

		varLar = new JSpinner();
		varLar.setMaximumSize(new Dimension(100,50));

		varLar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				switch(varFormat.getSelectedItem().toString())
				{
				case "4/3":
				{

					float lon= Float.parseFloat(  varLar.getValue().toString() ) ;

					lon = lon*(float)(3.0/4.0);

					varLong.setValue(Math.round( lon)  );

					break;
				}
				case "16/9":
				{
					float lon= Float.parseFloat(  varLar.getValue().toString() ) ;

					lon = lon*(float)(16.0/9.0);

					varLong.setValue(Math.round( lon)  );
					break;
				}
				case "16/10":
				{
					float lon= Float.parseFloat(  varLar.getValue().toString() ) ;

					lon = lon*(float)(16.0/10.0);

					varLong.setValue(Math.round( lon)  );
					break;
				}
				}
			}
		});


		horizontalBox_6.add(varLar);

		JLabel lblLargeur = new JLabel("Longeur : ");
		horizontalBox_6.add(lblLargeur);

		varLong = new JSpinner();
		horizontalBox_6.add(varLong);
		varLong.setMaximumSize(new Dimension(100,50));


		Box verticalBox_1 = Box.createVerticalBox();
		verticalBox_1.setAlignmentX(CENTER_ALIGNMENT);
		verticalBox_1.setMaximumSize(new Dimension(450,100));
		verticalBox_1.setMinimumSize(new Dimension(450,100));

		verticalBox.add(verticalBox_1);
		verticalBox_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		{
			JLabel lblPartieContrle = new JLabel("Partie contrôle ( Xbee) :");
			lblPartieContrle.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			verticalBox_1.add(lblPartieContrle);
			lblPartieContrle.setAlignmentX(Component.LEFT_ALIGNMENT);
			lblPartieContrle.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		}

		Box horizontalBox_2 = Box.createHorizontalBox();
		horizontalBox_2.setAlignmentX(Component.RIGHT_ALIGNMENT);

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
		horizontalBox_2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		verticalBox_1.add(horizontalBox_3);

		JLabel lblPortComActuel = new JLabel("Port com actuel  :");
		lblPortComActuel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPortComActuel.setHorizontalAlignment(SwingConstants.LEFT);
		horizontalBox_3.add(lblPortComActuel);


		lblPortComEnCours = new JLabel("XX");
		lblPortComEnCours.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPortComEnCours.setHorizontalAlignment(SwingConstants.LEFT);
		horizontalBox_3.add(lblPortComEnCours);
		this.PortComField.setModel(new DefaultComboBoxModel(lesPortsCom.toArray()));

		Box verticalBox_2 = Box.createVerticalBox();
		verticalBox_2.setAlignmentX(CENTER_ALIGNMENT);
		verticalBox_2.setMinimumSize(new Dimension(450,100));
		verticalBox_2.setMaximumSize(new Dimension(450,100));
		
		verticalBox.add(verticalBox_2);
		verticalBox_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel lblPartieLeapmotion = new JLabel("Partie LeapMotion : ");
		lblPartieLeapmotion.setAlignmentX(Component.LEFT_ALIGNMENT);
		lblPartieLeapmotion.setHorizontalAlignment(SwingConstants.LEFT);
		verticalBox_2.add(lblPartieLeapmotion);
		lblPartieLeapmotion.setFont(new Font("Lucida Grande", Font.BOLD, 13));

		Box horizontalBox_4 = Box.createHorizontalBox();
		horizontalBox_4.setAlignmentX(Component.LEFT_ALIGNMENT);

		verticalBox_2.add(horizontalBox_4);

		JLabel lblAdresse = new JLabel("Adresse : ");
		horizontalBox_4.add(lblAdresse);

	    try {
			MaskFormatter mf = new MaskFormatter("###.###.###.###");
			
			adrLeap = new JFormattedTextField(mf);
			adrLeap.setHorizontalAlignment(SwingConstants.CENTER);

			adrLeap.setColumns(1);
			adrLeap.setMaximumSize(new Dimension(150,100));
			horizontalBox_4.add(adrLeap);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		

		Box horizontalBox_5 = Box.createHorizontalBox();
		horizontalBox_5.setAlignmentX(Component.LEFT_ALIGNMENT);

		verticalBox_2.add(horizontalBox_5);

		JLabel label_1 = new JLabel("Port :");
		horizontalBox_5.add(label_1);

		portLeap = new JSpinner();
		portLeap.setMaximumSize(new Dimension(100,100));

		horizontalBox_5.add(portLeap);
		verticalBox_2.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblPartieLeapmotion, horizontalBox_4, lblAdresse, adrLeap, horizontalBox_5, label_1, portLeap}));

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
						if(!un.validate(adrLeap.getText()))
						{
							JOptionPane.showMessageDialog(null, "Adresse IP non valide", "Adresse IP",JOptionPane.WARNING_MESSAGE);	
							return;
						}

						List<String> lesParams = new ArrayList<String>();
						lesParams.add(URLfield.getText());
						lesParams.add(PortField.getValue().toString());


		

						try
						{
							// Pas de port comm dispo
							lesParams.add(PortComField.getSelectedItem().toString());
						}catch(Exception e1)
						{

							JOptionPane.showMessageDialog(null, "Attention, il n'y a aucun port serie configuré !", "Port comm abs",JOptionPane.WARNING_MESSAGE);
							

							
							lesParams.add("");
						}

					
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
