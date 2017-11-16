import java.io.*;
import java.util.*;

public class AuthenticationFile {

//this uses buffered readers
	String fileName;
	
	public AuthenticationFile(String fileName) {
		this.fileName= fileName;
	}
	
	public static ArrayList<Record> read(String fileName) {
		try {
			final ArrayList <Record> loginInfo = new ArrayList <Record>();
			FileReader fr= new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			
			String lines="";
			String ID, Pass;
			
			lines = br.readLine();
			while(lines != null ) {
				StringTokenizer st = new StringTokenizer (lines," ");
				ID= st.nextToken();
				Pass=st.nextToken();
				Record authorisedUser = new Record (ID,Pass);
				loginInfo.add(authorisedUser);
				lines = br.readLine();
			}
			
			br.close();
			return loginInfo;
			//System.out.println(loginInfo);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;


	}

}
	
	