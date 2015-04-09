package chatroom;
import java.net.*;
import java.io.*;

public class ChatroomClient{
	private boolean terminate = false;
	private boolean continueReading = true;
	private String previousInputStream;
	private View myView;
	private String previousOutputString;
	private String currentOutputString;
	private DataInputStream in;
	private DataOutputStream out;
	
	public ChatroomClient(View view){
		myView = view;
	}
	
	public String readConsoleInput(){
		System.out.print("Enter reply (type goodbye to terminate): ");
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
		myView = new View();
		
		String serverName = "10.190.77.51";
		int port = Integer.parseInt("6059");
		try
		{
			System.out.println("Connecting to " + serverName
					+ " on port " + port);
			Socket client = new Socket(serverName, port);
			client.setSoTimeout(100000000);
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
//			while(continueReading){
//				System.out.println("Server says " + in.readUTF());
//				String s = readConsoleInput();
//				if(s.toLowerCase().equals("goodbye")){
//					continueReading = false;
//				}
//				out.writeUTF(s);
//			}
			
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
			
			
			if(terminate){
				client.close();
				System.out.println("here");
			}
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void terminate(){
		terminate = true;
	}
	
//	public static void main(String [] args){ // for future use 104.131.22.182
//		ChatroomClient c = new ChatroomClient();
//		c.run();
//	}
}
