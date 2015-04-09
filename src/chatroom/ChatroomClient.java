package chatroom;
import java.net.*;
import java.io.*;

public class ChatroomClient{
	private boolean terminate = false;
	
	public void run(){
		String serverName = "10.190.77.51";
		int port = Integer.parseInt("6066");
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
			System.out.println("Server says " + in.readUTF());
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
	
	public static void main(String [] args){ // for future use 104.131.22.182
		ChatroomClient c = new ChatroomClient();
		c.run();
		c.terminate();
	}
}
