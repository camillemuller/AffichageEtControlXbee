package TCPCONTROLER;

public interface ClientRaspberryPiListener {
	
	public void onReceive(String userInput);
	public void stats(int stats);

}
