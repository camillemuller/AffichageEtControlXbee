package TCPCONTROLER;

public class HeartBit extends Thread {

	ClientRaspberryPI sonCp;
	boolean run= true;

	public boolean isRun() {
		return run;
	}
	public void setRun(boolean run) {
		this.run = run;
	}
	public HeartBit(ClientRaspberryPI Cp)
	{
		this.sonCp = Cp;	
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(run)
		{
			try {
				// Wait 200 ms
				this.wait(200);
				// Send heartBit
				this.sonCp.send("{\"heartbit\"=\"alive\"}");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
