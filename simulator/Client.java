 package simulator;
public class Client {
 private int id;
 private int tArrival;
 private int tService;
 private int ttotal;
 public Client (int id, int tarrival, int tservice)
 {
	 this.id=id;
	 this.tArrival=tarrival;
	 this.tService=tservice;
	 this.ttotal = this.tArrival+this.tService;
 }
public int getid() {
	return id;
}
public void setid(int id) {
	this.id = id;
}
public int getTarrival() {
	return tArrival;
}
public void setTarrival(int tarrival) {
	this.tArrival = tarrival;
}
public int getTservice() {
	return tService;
}
public void setTservice(int tservice) {
	this.tService = tservice;
}
public int getTtotal() {
	return ttotal;
}

}
