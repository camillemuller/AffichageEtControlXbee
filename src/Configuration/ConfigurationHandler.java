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

public class ConfigurationHandler {


	static Element racine = new Element("Configuration");

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
		Element PORTCOM = new Element("PORTCOM");
		PORTCOM.setText(sesParams.get(2));
		racine.addContent(PORTCOM);


		Element ADRLEAP =  new Element("ADRLEAP");
		ADRLEAP.setText(sesParams.get(3));
		racine.addContent(ADRLEAP);


		Element PORTLEAP = new Element("PORTLEAP");
		PORTLEAP.setText(sesParams.get(4));
		racine.addContent(PORTLEAP);


		Element VARLARG = new Element("VARLARG");
		VARLARG.setText(sesParams.get(5));
		racine.addContent(VARLARG);


		Element VARLONG = new Element("VARLONG");
		VARLONG.setText(sesParams.get(6));
		racine.addContent(VARLONG);


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


	public List<String> getSesparams() throws Exception
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


			Element PORTCOM = racine.getChild("PORTCOM");
			laConfig.add(PORTCOM.getValue());

			Element ADRLEAP = racine.getChild("ADRLEAP");
			laConfig.add(ADRLEAP.getValue());

			Element PORTLEAP = racine.getChild("PORTLEAP");
			laConfig.add(PORTLEAP.getValue());

			Element VARLARG = racine.getChild("VARLARG");
			laConfig.add(VARLARG.getValue());

			Element VARLONG = racine.getChild("VARLONG");
			laConfig.add(VARLONG.getValue());
			return laConfig;

	

	}
}
