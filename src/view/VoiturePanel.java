package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class VoiturePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private String distance= "XXX"; 
	public VoiturePanel() {
		try {
			image = ImageIO.read(new File("voiture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void changeDistance(String distance)
	{
		this.distance = distance;
		this.repaint();
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 30, 50, null); 
		
		System.out.println(distance+"\n");
        g.setColor(Color.red);
		g.drawString(distance+" cm", 55, 40);
	}

}
