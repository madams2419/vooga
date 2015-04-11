package chatroom;
import java.net.*;
import java.io.*;

public class ChatroomClient{
	private Socket client;
	private boolean terminate = false;
	private boolean continueReading = true;
	private String previousInputStream;
	private View myView;
	private String previousOutputString;
	private String currentOutputString;
	private DataInputStream in;
	private DataOutputStream out;
	private Console console = null;
	
	public ChatroomClient(String serverName, int serverPort){
		console = System.console();
		System.out.println("Connecting to "+ serverName + "..");
		try{
			client = new Socket(serverName, serverPort);
			System.out.println("Connected.");
			start();
		}catch(IOException e){
			e.printStackTrace();
		}
		boolean run = false;
		try{
		while(continueReading){
			if(!client.getInputStream().equals(previousInputStream)){
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
			previousInputStream = client.getInputStream().toString();
		}
		}catch(IOException e){

		}
		
		while(!run){
			try{
				String input = in.readUTF();
				System.out.println(input);
				run = input.equals("404");
			}catch(IOException e){
				run = true;
			}
			String output = "";
			if(!output.equals("404")){
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
	}
	
	public void start() throws IOException{
		in = new DataInputStream(System.in);
		out = new DataOutputStream(client.getOutputStream());
	}
	
	public void run(){
		String serverName = "10.190.77.51";

		int port = Integer.parseInt("6060");

//		int port = Integer.parseInt("6059");

		try
		{
			System.out.println("Connecting to " + serverName
					+ " on port " + port);
			Socket client = new Socket(serverName, port);
			client.setSoTimeout(10000);
			System.out.println(InetAddress.getLocalHost());
			System.out.println("Just connected to "
					+ client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out =
					new DataOutputStream(outToServer);
			out.writeUTF("Hello from "
					+ client.getLocalSocketAddress());
			InputStream inFromServer = client.getInputStream();
			DataInputStream in =
					new DataInputStream(inFromServer);
			while(continueReading){
				System.out.println("Server says " + in.readUTF());
				String s = readConsoleInput();
				if(s.toLowerCase().equals("goodbye")){
					continueReading = false;
				}
				out.writeUTF(s);
			}			
			if(terminate){
				client.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private String readConsoleInput() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args){
		ChatroomClient client = new ChatroomClient("10.190.77.51",6050);
	}
//	public String readConsoleInput(){
//		System.out.print("Enter reply (type goodbye to terminate): ");
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
//		myView = new View();
//		
//		String serverName = "10.190.77.51";
//		int port = Integer.parseInt("6059");
//		try
//		{
//			System.out.println("Connecting to " + serverName
//					+ " on port " + port);
//			Socket client = new Socket(serverName, port);
//			client.setSoTimeout(100000000);
//			System.out.println(InetAddress.getLocalHost());
//			System.out.println("Just connected to "
//					+ client.getRemoteSocketAddress());
//			OutputStream outToServer = client.getOutputStream();
//			DataOutputStream out =
//					new DataOutputStream(outToServer);
//			out.writeUTF("Hello from "
//					+ client.getLocalSocketAddress());
//			InputStream inFromServer = client.getInputStream();
//			DataInputStream in =
//					new DataInputStream(inFromServer);
////			while(continueReading){
////				System.out.println("Server says " + in.readUTF());
////				String s = readConsoleInput();
////				if(s.toLowerCase().equals("goodbye")){
////					continueReading = false;
////				}
////				out.writeUTF(s);
////			}
//			
//			while(continueReading){
//				if(!client.getInputStream().equals(previousInputStream)){
//					myView.sendText(in.readUTF());
//				}
//				if(myView.getStringChanged()){
//					String outputString = myView.getText();
//					out.writeUTF(outputString);
//					previousOutputString = outputString;
//				}
//				if(!currentOutputString.equals(previousOutputString)){
//					out.writeUTF(currentOutputString);
//					previousOutputString = currentOutputString;
//				}
//				previousInputStream = client.getInputStream().toString();
//			}
//			
//			
//			if(terminate){
//				client.close();
//				System.out.println("here");
//			}
//		}catch(IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
//	
//	public void terminate(){
//		terminate = true;
//	}
	
//	public static void main(String [] args){ // for future use 104.131.22.182
//		ChatroomClient c = new ChatroomClient();
//		c.run();
//	}
}
