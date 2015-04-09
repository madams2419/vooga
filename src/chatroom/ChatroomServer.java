package chatroom;

import java.net.*;
import java.io.*;

public class ChatroomServer extends Thread{
	private ServerSocket serverSocket;

	public ChatroomServer(int port) throws IOException{
		serverSocket = new ServerSocket(port);
		System.out.println(InetAddress.getLocalHost());
		serverSocket.setSoTimeout(10000);
	}

	public void run(){
		while(true){
			try{
				System.out.println("Waiting for client on port " +
						serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				server.setSoTimeout(10000);
				System.out.println("Just connected to "
						+ server.getRemoteSocketAddress());
				DataInputStream in =
						new DataInputStream(server.getInputStream());
				System.out.println(in.readUTF());
				DataOutputStream out =
						new DataOutputStream(server.getOutputStream());
				out.writeUTF("Thank you for connecting to "
						+ server.getLocalSocketAddress() + "Michael\nGoodbye!");
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
	
	public static void main(String [] args)	{
		int port = Integer.parseInt("6066");
		try{
			Thread t = new ChatroomServer(port);
			t.start();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}