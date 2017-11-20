	
//This program runs on one machine 

import java.io.*;
import java.util.*;
import java.net.*;

public class FMDServerTCP {
	
	public static void main(String args[]) {
		
		//declare a TCP socket object and initialize it to null
        ServerSocket welcomeSocket=null;
        //create the port number
        int port = 6790;
        
        try {
        	//Create the TCP server socket
        	welcomeSocket=new ServerSocket(port);
        	System.out.println("FMD-TCP server created on port = "+port);
        } catch (IOException ex) {
            //will be executed when the server cannot be created
            System.out.println("Error: The server with port="+port+" cannot be created");
        }
        
        System.out.print("/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */\n"
                + "\t\t\t\tFull_Medical_Docket\n"
                + "/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */\n");
       
        
        String clientResponse =null;
        
        while(true){//start an endless loop to listen for clients
        	Socket connectionSocket = null;
        	try {//wait for connection (listening socket) 
        		System.out.println("\nAwaiting connection...");
        		connectionSocket = welcomeSocket.accept();//passes the connection from listening the socket to an auxiliary socket
        		System.out.println("\nCLIENT CONNECTED...IP: "+connectionSocket.getInetAddress()+",PORT: "+connectionSocket.getPort());
        		
        	}
        	 catch (IOException e) {
                 System.out.println("Error: cannot accept client request. Exit program");
                 return;
             }
        	
        	try {//Guts of the system is built within here-----------------------------------------------------------------------------------------------
        		
        	    AuthenticateUsers();//creates necessary files to represent logins and hashes for medical stall and patients that were already in the system
        		PrintStream toClient = new PrintStream(connectionSocket.getOutputStream());//Print to client
                InputStreamReader inputStream = new InputStreamReader(connectionSocket.getInputStream()); 
                BufferedReader inFromClient = new BufferedReader(inputStream);//To read input from client  
                Login(connectionSocket);//Request for them to login
                
                //clientResponse = inFromClient.readLine();
                //System.out.println(clientResponse);//test to see what the client was sending
                
                
        	}catch (Exception e){
        		System.out.print("[FMD Server TCP] The server cannot send the message");
        		
        	}//GUTS of FDM ends here-------------------------------------------------------------------------------------------------------------
        	
        
        }//ends server loop 
        	
	}

	public static void Login(Socket connectionSocket) throws IOException{ //Simply asks the client to login
		PrintStream outToClient = new PrintStream(connectionSocket.getOutputStream());		
		String message=("Welcome to your Full Medical Docket\nPlease login to continue or press Q to quit\nLogin:\n#");
		outToClient.println(message);
		InputStreamReader inputStream = new InputStreamReader(connectionSocket.getInputStream()); 
        BufferedReader inFromClient = new BufferedReader(inputStream);//To read input from client
        
        int count=0;
        int maxTries=3;
        int flag=0; //use 1 for medical stall && use 2 for patient (optional route) ** I set it for a generic escape route below
        String clientResponse = inFromClient.readLine();
        System.out.println(clientResponse);
        
        while(true) {
        	if (count == maxTries) {
    			outToClient.println("Sorry attempts depleted exiting now\nGOODBYE\n#");
    			break;
    			
    		}
        	
        	if (!(clientResponse.charAt(0)=='m') && !(clientResponse.charAt(0)=='p') && !(clientResponse.equalsIgnoreCase("Q") )) {//did it this way cause wasnt sequencing properly if just put this in else{..}
    			outToClient.println("Sorry we couldn't find that login account please try again\nRe-try attempts left= "+(maxTries-count)+"\nLogin:\n#");
    			clientResponse = inFromClient.readLine();
    			count++;
    		}
        	
    		if(clientResponse.charAt(0)=='m') {
    			//Medical staff------------login
    			ArrayList<Record> loginInfo = AuthenticationFile.read("StaffAuth.txt");
    			for(Record x: loginInfo) {
        				if(x.getID().equals(clientResponse)) {//ID found just need to verify password now...
        					outToClient.println("ID confirmed\nPlease enter password\nPassword:\n#");
        					clientResponse = inFromClient.readLine();
        					String hash= Encryption.SHA1(clientResponse);
    					
        					if(hash.equals(x.getHashPass())) {//CORRECT PASS entered....Here is where we need to use a reference from the authentication file to medical staff functions
        						//*******Referencing location
        						outToClient.println("ID & Password confirmed welcome (Name and title after referenced location):\n#");
        						//used flag to break out the while loop or put medical staff menu here
        						flag=1;
        						break;
        						
        					}else {//INCORRECT pass entered used attempt counter here as well
        						outToClient.println("ID OR Password incorrect please try again\nRe-try attempts left= "+(maxTries-count)+"\nLogin:\n#");
        						clientResponse = inFromClient.readLine();
        	        			count++;
        	        			break;
        					}
        				}
    			}//end for loop
    		}
	
    		if(clientResponse.charAt(0)=='p') {
    			//Patient------------login
    			ArrayList<Record> loginInfo = AuthenticationFile.read("PatientAuth.txt");
    			for(Record x: loginInfo) {
    				if(x.getID().equals(clientResponse)) {//ID found just need to verify password now...
    					outToClient.println("ID confirmed\nPlease enter password\nPassword:\n#");
    					clientResponse = inFromClient.readLine();
    					String hash= Encryption.SHA1(clientResponse);
					
    					if(hash.equals(x.getHashPass())) {//CORRECT PASS entered....Here is where we need to use a reference from the authentication file to patient records 
    						//*******Referencing location
    						outToClient.println("ID & Password confirmed welcome (Name and title after referenced location):\n#");
    						//used flag to break out the while loop or put patient menu here
    						flag=2;
    						break;
    						
    					}else {//INCORRECT pass entered used attempt counter here as well
    						outToClient.println("ID OR Password incorrect please try again\nRe-try attempts left= "+(maxTries-count)+"\nLogin:\n#");
    						clientResponse = inFromClient.readLine();
    	        			count++;
    	        			break;
    					}
    				}
    			}//end for 
    		}
    		
    		
    		if(clientResponse.equalsIgnoreCase("Q")) {
    			//Quit section 
    			outToClient.println("GOODBYE\n#");
    			break;
    		}
    		
    		if(flag!=0) {//generic escape route 
    			break;
    		}
    		
        }//end the while loop that has the attempt counter within it 

	}
	
	public static void AuthenticateUsers() {
		UserFiles file = new UserFiles ();
		file.openFile();
		file.addRecords();
		file.closeFile();
	}
	
	
	
	
	
	
	
	
}