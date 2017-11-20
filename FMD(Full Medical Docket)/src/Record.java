
public class Record {
	private String ID;
	private String hash;
	
	public Record(String ID, String hash) {
		this.ID = ID;
		this.hash = hash;
	}
	

	public String getID(){
		return ID;
	}
	
	public String getHashPass() {
		return hash;
	}
	
	public String toString() {
		return "ID: "+ID+" PASS:"+hash;
	}

}
