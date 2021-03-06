package simulator;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Shop {
	private static List<Client> customers = new ArrayList<Client>();
	private static List<Queue> queues = new ArrayList<Queue>();
	private static int time = 0;
	private static int nrQ;
	private static int nrC;
	private static int minA, maxA, minS, maxS;
	private static FileWriter output;
	private static double waitingTime = 0;
	private static boolean ok = true;

	public static void main(String[] args) throws Exception {

		File fin = new File(args[0]);
		readData(fin);
		addCustomers();
		addQueues();
		output = new FileWriter(args[1]);
	
		while (!customers.isEmpty()) {
			Client c = customers.get(0);
			here: for (Queue itemQ : queues) {
				if (c.getTarrival() < itemQ.getCurrentSec()) {
					if (itemQ.getStatus() == "closed") {
						itemQ.addClient(c);
						waitingTime = waitingTime + ((itemQ.getCurrentSec() - 1) - c.getTarrival() + c.getTservice());
						customers.remove(c);
						if (customers.isEmpty() || customers.get(0).getTarrival() != c.getTarrival()|| customers.get(0).getTarrival()>=itemQ.getCurrentSec()) {
							break;
						} else {
							c = customers.get(0);
							continue here;
						}
					}
				}
			}
			writeDataInFile();
		}
		while (ok) {
			ok = false;
			for (Queue itemQ : queues) {
				if (itemQ.getStatus() == "busy")
					ok = true;
			}
			writeDataInFile();
		}
		writeData("\nAverage Time:" + (double) waitingTime / nrC);
		output.close();
	}

	private static void addCustomers() {
		Random rand = new Random();
		for (int i = 0; i < nrC; i++) {
			int randomIntA = rand.nextInt(maxA - minA + 1) + minA;
			int randomIntS = rand.nextInt(maxS - minS + 1) + minS;
			Client c = new Client(i + 1, randomIntA, randomIntS);
			customers.add(c);
		}
		Collections.sort(customers, new Comparator<Client>() {

			public int compare(Client c1, Client c2) {
				return (c1.getTarrival() - c2.getTarrival());
			}
		});
	}

	private static void addQueues() {

		for (int i = 1; i <= nrQ; i++) {
			Queue q = new Queue(i, "Queue" + i);
			queues.add(q);
			q.start();
		}
	}

	private static void printClients() throws IOException {
		writeData("Waiting clients: ");
		for (Client c : customers) {
			writeData("(" + c.getid() + "," + c.getTarrival() + "," + c.getTservice() + ");");
		}
		writeData("\n");
	}

	private static void printQueues() throws IOException {
		for (Queue q : queues) {
			if (q.getStatus() == "closed") {
				writeData("Queue" + q.getId() + ": closed");
				writeData("\n");
			} else {
				writeData("Queue" + q.getId() + ": (" + q.getC().getid() + "," + q.getC().getTarrival() + ","
						+ q.getC().getTservice() + ");");
				writeData("\n");
			}
		}
	}

	private static void readData(File fin) throws NumberFormatException, IOException {
	
		BufferedReader b = new BufferedReader(new FileReader(fin));
		String seq;
		nrC = Integer.parseInt(b.readLine());
		nrQ = Integer.parseInt(b.readLine());
		int totalTime = Integer.parseInt(b.readLine());
		Queue.setTotalTime(totalTime);
		seq = b.readLine();
		String[] sir = seq.split(",");
		minA = Integer.parseInt(sir[0]);
		maxA = Integer.parseInt(sir[1]);
		seq = b.readLine();
		sir = seq.split(",");
		minS = Integer.parseInt(sir[0]);
		maxS = Integer.parseInt(sir[1]);
	}

	public static void writeData(String dataToWrite) throws IOException {
		try {
			output.write(dataToWrite);
		} catch (Exception e) {
			System.out.println("Exception");
		}

	}

	public static void writeDataInFile() throws IOException {
		writeData("\nTime " + time++ + "\n");
		printClients();
		printQueues();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

	}

}
