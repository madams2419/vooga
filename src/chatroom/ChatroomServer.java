package chatroom;

import java.net.*;
import java.io.*;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class ChatroomServer extends Thread {
	private ServerSocket serverSocket;
	private String previousInputStream;
	private String previousOutputString;
	private String currentOutputString;
	private boolean continueReading = true;
	private DataInputStream in;
	private DataOutputStream out;
	View myView;
	
	public ChatroomServer(int port, View view) throws IOException{
		myView = view;
		serverSocket = new ServerSocket(port);
		System.out.println(InetAddress.getLocalHost());
		serverSocket.setSoTimeout(10000);
	}

	public String readConsoleInput(){
		System.out.print("Enter message (type goodbye to terminate): ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String message = br.readLine();
			return message;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "invalid message";
	}
	
	public void run(){
		while(true){
			try{
				System.out.println("Waiting for client on port " +
						serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				server.setSoTimeout(100000000);
				System.out.println("Just connected to "
						+ server.getRemoteSocketAddress());
				
				in = new DataInputStream(server.getInputStream());
				out = new DataOutputStream(server.getOutputStream());
				while(continueReading){
					if(!server.getInputStream().equals(previousInputStream)){
						myView.sendText(in.readUTF());
					}
					if(myView.getStringChanged()){
						String outputString = myView.getText();
						out.writeUTF(outputString);
						previousOutputString = outputString;
					}
					if(!currentOutputString.equals(previousOutputString)){
						out.writeUTF(currentOutputString);
						previousOutputString = currentOutputString;
					}
					previousInputStream = server.getInputStream().toString();
				}
//				out.writeUTF("Thank you for connecting to "
//						+ server.getLocalSocketAddress() + "Michael\nGoodbye!");
				server.close();
			}catch(SocketTimeoutException s)
			{
				System.out.println("Socket timed out!");
				break;
			}catch(IOException e)
			{
				e.printStackTrace();
				break;
			}
		}
	}
	
	public void writeString(String string){
		currentOutputString = string;
	}


}