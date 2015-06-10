package TCPCONTROLER;

/**
 * Listener de classe ServerControlHandler
 * Permet de recevoir les informations sur le nb de client connecté...
 * Et peut-être un jour bien plus !
 * @author camillemuller
 *
 */
public interface ServerControlListener {
	
	public void nbClient(int nbClient);

}
