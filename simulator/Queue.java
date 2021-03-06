package simulator;
import java.util.List;
import java.util.ArrayList;

public class Queue implements Runnable {

	private Thread t;
	private String status = "closed";
	private Client c;
	private int id;
	private String nameQ;
	private int currentSec = 0;
	private static int totalTime;

	public Queue(int id, String nameQ) {
		this.id = id;
		this.nameQ = nameQ;
	}

	public void run() {
   if (currentSec <= totalTime) {
		while (currentSec <= totalTime) {

			try {
				++currentSec;
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (status != "closed") {
				serveClient();
			}
		}}
   else
		{Thread.currentThread().interrupt();}
	}

	public void start() {

		if (t == null) {
			t = new Thread(this, nameQ);
			t.start();
		}

	}

	private void serveClient() {

		int tservice = c.getTservice();
		if (tservice == 1) {
			removeClient();
		} else {
			tservice--;
			c.setTservice(tservice);
			
		}

	}

	public void addClient(Client c) {

		this.c = c;
		status = "busy";
		
	}

	private void removeClient() {
		
		status = "closed";
		//Thread.currentThread().interrupt();
		
	}

	public int getId() {
		
		return id;
		
	}

	public String getNameQ() {
		
		return nameQ;
		
	}

	public int getCurrentSec() {
		
		return currentSec;
		
	}

	public String getStatus() {
		
		return status;
		
	}

	public Client getC() {
		
		return c;
		
	}
	public static int getTotalTime() {
		return totalTime;
	}
	public static void setTotalTime(int totalTime) {
		Queue.totalTime = totalTime;
	}
}
