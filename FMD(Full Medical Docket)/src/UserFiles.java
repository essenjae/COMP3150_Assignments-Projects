/*
 * This is used simply to create file of authorized users capable of using the FMD (pre-loads data)
 * Its meant to simply simulate the pre-existence of a file that would be present on the systems server
 */
import java.io.*;
import java.lang.*;
import java.util.*;

public class UserFiles {
	private Formatter format;
	private Formatter format2;
	
	public void openFile() {//gives the file Test.txt to use
		try {
			format = new Formatter ("StaffAuth.txt");
			format2 = new Formatter ("PatientAuth.txt");
			
		
		}catch (Exception e) {
			System.out.println("Error detected");
			
		}
	}
	
	public void addRecords() {
		//missing the referencing to other database file with actual records eg format.format("%s %s %s\n", "m0004",Encryption.SHA1("Password4"),"56");
		//where 56 is the line in the record data base to read from
		format.format("%s %s\n", "m0001",Encryption.SHA1("Password1"));
		format.format("%s %s\n", "m0002",Encryption.SHA1("Password2"));
		format.format("%s %s\n", "m0003",Encryption.SHA1("Password3"));
		format.format("%s %s\n", "m0004",Encryption.SHA1("Password4"));
		format.format("%s %s\n", "m0005",Encryption.SHA1("Password5"));
		
		format2.format("%s %s\n", "p0001",Encryption.SHA1("password1"));
		format2.format("%s %s\n", "p0002",Encryption.SHA1("password2"));
		format2.format("%s %s\n", "p0003",Encryption.SHA1("password3"));
		format2.format("%s %s\n", "p0004",Encryption.SHA1("password4"));
		format2.format("%s %s\n", "P0005",Encryption.SHA1("password5"));
		
	}
	
	
	public void closeFile() {
		format.close();
		format2.close();
	}
	
}
