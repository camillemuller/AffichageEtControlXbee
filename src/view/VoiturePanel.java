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
	private String distanceAvant= "XXX"; 
	private String distanceArriere= "XXX"; 

	public VoiturePanel() {
		try {
			image = ImageIO.read(new File("img/voiture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void changeDistance(String distanceAvant,String distanceArriere)
	{

		if(Integer.parseInt(distanceAvant) >= 0)
			this.distanceAvant = distanceAvant;
		else
			this.distanceAvant = "0";
		
		if(Integer.parseInt(distanceArriere) >= 0)
		this.distanceArriere = distanceArriere;
		else
		this.distanceArriere = "0";
		
		this.repaint();

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(image,  
				(int)Math.round(this.getHeight()*0.2),(int) Math.round(this.getWidth()*0.25), this); 

		g.setColor(Color.red);
		g.drawString(distanceAvant+" cm", (int) (this.getSize().getHeight()*0.28), (int) (this.getSize().getHeight()*0.15));

		g.drawString(distanceArriere+" cm", (int) (this.getSize().getHeight()*0.28), (int) (this.getSize().getHeight()*0.90));
	}

}
