package chatroom;

import java.net.*;
import java.io.*;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class ChatroomServer extends Thread {
	private ServerSocket serverSocket;
	private Socket socket;
	private String previousInputStream;
	private String previousOutputString;
	private String currentOutputString;
	private boolean continueReading = true;
	private DataInputStream in;
	private DataOutputStream out;
	private Thread myThread = null;
	private Console console = null;
	
	View myView;

	public ChatroomServer(int port) throws IOException{
		console = System.console();
		try{
			System.out.println("Initializing server. Port: " + 
					port + " IP: " + InetAddress.getLocalHost());
			serverSocket = new ServerSocket(port);
			start();
		}catch(IOException e){
			e.printStackTrace();
		}
		//		
		//		System.out.println(InetAddress.getLocalHost());
		//		serverSocket.setSoTimeout(10000);
	}
	public void runServer(){
		while(myThread != null){
			try{
				System.out.println("Waiting for client...");
				socket = serverSocket.accept();
				open();
				boolean run = false;
				if(!run){
					try{
						String input = in.readUTF();
						System.out.println(input);
						run = input.equals("404");
					}catch(IOException e){
						run = true;
					}
					String output = "";
					while(!output.equals("404")){
						try{
							if(console != null){
								output = console.readLine("Enter message: ");
								out.writeUTF("Server: " + output);
								out.flush();
							}
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				}
				close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public void start(){
		if(myThread == null){
			myThread = new Thread(this);
			myThread.start();
		}
	}

	public void open() throws IOException{
		in = new DataInputStream(new BufferedInputStream(
				socket.getInputStream()));
	}

	public void close() throws IOException{
		if(socket != null){
			socket.close();
		}
		if(in != null){
			in.close();
		}
	}

	public static void main(String[] args){
		try {
			ChatroomServer myServer = new ChatroomServer(5000);
			myServer.runServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//	
	//	public String readConsoleInput(){
	//		System.out.print("Enter message (type goodbye to terminate): ");
	//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//		try {
	//			String message = br.readLine();
	//			return message;
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//		return "invalid message";
	//	}
	//	
	//	public void run(){
	//		while(true){
	//			try{
	//				System.out.println("Waiting for client on port " +
	//						serverSocket.getLocalPort() + "...");
	//				Socket server = serverSocket.accept();
	//				server.setSoTimeout(100000000);
	//				System.out.println("Just connected to "
	//						+ server.getRemoteSocketAddress());
	//				
	//				in = new DataInputStream(server.getInputStream());
	//				out = new DataOutputStream(server.getOutputStream());
	//				while(continueReading){
	//					if(!server.getInputStream().equals(previousInputStream)){
	//						myView.sendText(in.readUTF());
	//					}
	//					if(myView.getStringChanged()){
	//						String outputString = myView.getText();
	//						out.writeUTF(outputString);
	//						previousOutputString = outputString;
	//					}
	//					if(!currentOutputString.equals(previousOutputString)){
	//						out.writeUTF(currentOutputString);
	//						previousOutputString = currentOutputString;
	//					}
	//					previousInputStream = server.getInputStream().toString();
	//				}
	////				out.writeUTF("Thank you for connecting to "
	////						+ server.getLocalSocketAddress() + "Michael\nGoodbye!");
	//				server.close();
	//			}catch(SocketTimeoutException s)
	//			{
	//				System.out.println("Socket timed out!");
	//				break;
	//			}catch(IOException e)
	//			{
	//				e.printStackTrace();
	//				break;
	//			}
	//		}
	//	}
	//	
	//	public void writeString(String string){
	//		currentOutputString = string;
	//	}


}