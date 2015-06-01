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
			image = ImageIO.read(new File("voiture.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void changeDistance(String distanceAvant,String distanceArriere)
	{
		this.distanceAvant = distanceAvant;
		this.distanceArriere = distanceArriere;
		this.repaint();
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		System.out.println(this.getSize().getHeight());
		System.out.println(this.getSize().getWidth());

		g.drawImage(image,  
				 (int)Math.round(this.getHeight()*0.2),(int) Math.round(this.getWidth()*0.25), this); 
		

		
		System.out.println("Debug avant : "+distanceAvant+"\n");
		System.out.println("Debug avant : "+distanceArriere+"\n");

        g.setColor(Color.red);
		g.drawString(distanceAvant+" cm", (int) (this.getSize().getHeight()*0.28), (int) (this.getSize().getHeight()*0.15));
		
		g.drawString(distanceArriere+" cm", (int) (this.getSize().getHeight()*0.28), (int) (this.getSize().getHeight()*0.90));
	}

}
