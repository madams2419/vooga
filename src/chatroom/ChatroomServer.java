package chatroom;

import java.net.*;
import java.io.*;

public class ChatroomServer extends Thread{
	private ServerSocket serverSocket;
	private String previousInputStream;
	private boolean continueReading = true;
	public ChatroomServer(int port) throws IOException{
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
				server.setSoTimeout(10000000000);
				System.out.println("Just connected to "
						+ server.getRemoteSocketAddress());
				
				DataInputStream in =
						new DataInputStream(server.getInputStream());
				DataOutputStream out =
						new DataOutputStream(server.getOutputStream());
				while(continueReading){
					if(!server.getInputStream().equals(previousInputStream)){
						System.out.println(in.readUTF());
					}
					String s = readConsoleInput();
					if(s.toLowerCase().equals("goodbye")){
						continueReading = false;
					}
					out.writeUTF(s);
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

	public static void main(String [] args)	{

		int port = Integer.parseInt("6059");

		try{
			Thread t = new ChatroomServer(port);
			t.start();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}