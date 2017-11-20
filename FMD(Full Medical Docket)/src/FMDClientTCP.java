import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.stream.Stream;

public class FMDClientTCP {

	public static void main(String[] args) throws IOException {
		  System.out.print("/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */\n"
	                + "\t\t\t\t YOUR FULL MEDICAL DOCKET!!!\n"
	                + "/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */\n");
		  System.out.println("\n************Your medical hystory no longer a mystery************\n");
	        
	        
		Socket clientSocket =null;
        String serverHostName = "localhost";//I have it set for local host so far 
        
        int port = 6790;
        
       try {
           clientSocket = new Socket(serverHostName, port);
       } catch (IOException ex) {
           System.out.println("[FMD Client TCP ] cannot open the socket with the server");
       }
       
       PrintStream printStream = new PrintStream(clientSocket.getOutputStream());
       Scanner inFromUser = new Scanner(System.in);//to collect input from user
       
       
       
       String message = InFromServer(clientSocket);//This should display login request 
      //printStream.println(inFromUser.nextLine()); 
       //message = InFromServer(clientSocket);
       
       String s = "GOODBYE";
       
       
       while(!(message.contains(s))) {//checks if the user has quit the FMD only exits if Q is chosen nothing else
    	   printStream.println(inFromUser.nextLine());//response from user sent to server
           message = InFromServer(clientSocket);//This section gives response message after decision was given above
    	   
       }
       
    	   
      
       
       inFromUser.close();
       clientSocket.close();
       

	}
	
	public static String InFromServer(Socket clientSocket) throws IOException {//Wanted to be able to read in multiple lines from server so used the '#' character to state when was the end Lines sent to client
	    InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
	    BufferedReader inFromServer = new BufferedReader(inputStream);//to collect input from server
	    String message = inFromServer.readLine(), prev_message=null;
	    
	    while(!(message.equals("#"))) {//makes it read until the # appears 
	    	System.out.println(message);
	    	prev_message=message;
	    	message = inFromServer.readLine();
	    
	    }
	    return prev_message;
	    
		
	}

}
