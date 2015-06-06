package Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.charliemouse.cambozola.Viewer;

public class ConfigurationHandler {


	static Element racine = new Element("Configuration");

	Viewer sonViewer;
	//On crée un nouveau Document JDOM basé sur la racine que l'on vient de créer
	static Document document = new Document(racine);


	public void sauvegarde(List<String> sesParams)
	{
		File ee = new File("Configuration.xml");
		ee.delete();
		Element racine = new Element("Configuration");
		Document document = new Document(racine);
		//On crée un nouvel Element etudiant et on l'ajoute
		//en tant qu'Element de racine
		Element URL = new Element("URL");
		URL.setText(sesParams.get(0));
		racine.addContent(URL);
		//On crée un nouvel Element etudiant et on l'ajoute
		//en tant qu'Element de racine
		Element PORT = new Element("PORT");
		PORT.setText(sesParams.get(1));


		racine.addContent(PORT);
		Element ADRRSPI = new Element("ADRRSPI");
		ADRRSPI.setText(sesParams.get(2));
		racine.addContent(ADRRSPI);


		Element PORTLEAP = new Element("PORTLEAP");
		PORTLEAP.setText(sesParams.get(3));
		racine.addContent(PORTLEAP);


		Element VARHAUTEUR = new Element("VARHAUTEUR");
		VARHAUTEUR.setText(sesParams.get(4));
		racine.addContent(VARHAUTEUR);


		Element VARLARGEUR = new Element("VARLARGEUR");
		VARLARGEUR.setText(sesParams.get(5));
		racine.addContent(VARLARGEUR);

		Element PORTRSPI = new Element("PORTRSPI");
		PORTRSPI.setText(sesParams.get(6));
		racine.addContent(PORTRSPI);
		
		Element DISTANCEARRET = new Element("DISTANCEARRET");
		DISTANCEARRET.setText(sesParams.get(7));
		racine.addContent(DISTANCEARRET);
		
		Element VITESSEMAX = new Element("VITESSEMAX");
		VITESSEMAX.setText(sesParams.get(8));
		racine.addContent(VITESSEMAX);


		try
		{
			//On utilise ici un affichage classique avec getPrettyFormat()
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			//Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
			//avec en argument le nom du fichier pour effectuer la sérialisation.
			sortie.output(document, new FileOutputStream("Configuration.xml"));
		}
		catch (java.io.IOException e)
		{e.printStackTrace();
		}


	}

	public String getUrlCamera()
	{
		try {
			return getSesparams().get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Fichier xml n'existe pas");
			return "http://192.168.1.1:8080/?action=stream";

		}
	}

	public int getPortCamera()
	{
		try {
			return  Integer.parseInt(getSesparams().get(1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Fichier xml n'existe pas");
			return 8080;

		}
	}

	public String getIpRasp()
	{
		try {
			return getSesparams().get(2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Fichier xml n'existe pas");
			return "192.168.1.1";

		}
	}

	public int getPortLeap()
	{
		try {
			return Integer.parseInt(getSesparams().get(3));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Fichier xml n'existe pas");
			return 5005;

		}
	}

	public int getHauteurCamera()
	{
		try {
			return Integer.parseInt(getSesparams().get(4));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Fichier xml n'existe pas");
			return 640;

		}
	}

	public int getLargeurCamera()
	{
		try {
			return Integer.parseInt(getSesparams().get(5));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Fichier xml n'existe pas");
			return 460;

		}
	}

	public int getPortRsp()
	{
		try {
			return Integer.parseInt(getSesparams().get(6));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Fichier xml n'existe pas");
			return 10200;

		}
	}
	
	public int getDistanceArret()
	{
		try {
			return Integer.parseInt(getSesparams().get(7));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Fichier xml n'existe pas");
			return 10;

		}
	}

	public int getVitesseMax()
	{
		try {
			return Integer.parseInt(getSesparams().get(8));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Fichier xml n'existe pas");
			return 10;

		}
	}


	private List<String> getSesparams() throws Exception
	{
		List<String> laConfig = new ArrayList<String>();


		//On crée une instance de SAXBuilder

		SAXBuilder sxb = new SAXBuilder();
		//On crée un nouveau document JDOM avec en argument le fichier XML
		//Le parsing est terminé ;)
		Document document = sxb.build(new File("Configuration.xml"));
		//On initialise un nouvel élément racine avec l'élément racine du document.
		racine = document.getRootElement();

		//On crée une List contenant tous les noeuds "etudiant" de l'Element racine
		Element URL = racine.getChild("URL");
		laConfig.add(URL.getValue());
		//DEBUG
		System.out.println("URL" + URL.getValue());
		//On crée une List contenant tous les noeuds "etudiant" de l'Element racine
		Element PORT = racine.getChild("PORT");
		laConfig.add(PORT.getValue());

		Element ADRRSPI = racine.getChild("ADRRSPI");
		laConfig.add(ADRRSPI.getValue());

		Element PORTLEAP = racine.getChild("PORTLEAP");
		laConfig.add(PORTLEAP.getValue());

		Element VARHAUTEUR = racine.getChild("VARHAUTEUR");
		laConfig.add(VARHAUTEUR.getValue());

		Element VARLARGEUR = racine.getChild("VARLARGEUR");
		laConfig.add(VARLARGEUR.getValue());		

		Element PORTRSPI = racine.getChild("PORTRSPI");
		laConfig.add(PORTRSPI.getValue());

		Element DISTANCEARRET = racine.getChild("DISTANCEARRET");
		laConfig.add(DISTANCEARRET.getValue());
		
		Element VITESSEMAX = racine.getChild("VITESSEMAX");
		laConfig.add(VITESSEMAX.getValue());
		
		
		return laConfig;



	}
}
