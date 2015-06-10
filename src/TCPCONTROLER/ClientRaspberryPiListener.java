package TCPCONTROLER;

/**
 * Listener qui vas permettre d'avoir des retours d'inforamtions sur l'instance 
 * ClientRaspberryPi lors :
 * onReceive : De la reception d'un message
 * stats     : D'un changement d'état de la connexion
 * @author camillemuller
 *
 */
public interface ClientRaspberryPiListener {
	/**	 
	 * S'active lors de la reception d'un message sur la socket
	 * @param userInput
	 */
	public void onReceive(String userInput);
	/**
	 * S'active lors de la reception d'un changement d'état
	 * @param string
	 */
	public void stats(String string);

}
