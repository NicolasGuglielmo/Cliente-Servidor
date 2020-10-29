import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.*;

public class Servidor {
	static Vector<Socket> ClientSockets;
	int clientCount = 0;
	// int i = 0;

	Servidor() throws IOException {
		
		SimpleDateFormat dateformat2 = new SimpleDateFormat();

		System.out.println("MultiThreadServer started at " + dateformat2.format(new Date()));
		System.out.println();

		ServerSocket server = new ServerSocket(8000);
		ClientSockets = new Vector<Socket>();

		while (true) {
			Socket client = server.accept();
			AcceptClient acceptClient = new AcceptClient(client);
			System.out.println(
					"Connection from Socket " + "[addr = " + client.getLocalAddress() + ",port = " + client.getPort()
							+ ",localport = " + client.getLocalPort() + "] at " + dateformat2.format(new Date()));
			System.out.println();
			// System.out.println(clientCount);

		}
		// server.close();
	}

	public static void main(String[] args) throws IOException {
		Servidor server = new Servidor();
	}

	class AcceptClient extends Thread {
		Socket ClientSocket;
		DataInputStream din;
		DataOutputStream dout;

		AcceptClient(Socket client) throws IOException {
			ClientSocket = client;
			din = new DataInputStream(ClientSocket.getInputStream());
			dout = new DataOutputStream(ClientSocket.getOutputStream());

			// String LoginName = din.readUTF();
			// i = clientCount;
			clientCount++;
			ClientSockets.add(ClientSocket);
			// System.out.println(ClientSockets.elementAt(i));
			// System.out.println(ClientSockets.elementAt(1));

			start();
		}

		public void run() {

			try {
				while (true) {
					String msgFromClient = din.readUTF();
					System.out.println(msgFromClient);
					
					/*
					 * Recibe /msj en el body, hago algo
					 * Sino, lo dirijo a la sala que me corresponde.
					 */
					for (int i = 0; i < ClientSockets.size(); i++) {
						Socket pSocket = (Socket) ClientSockets.elementAt(i);
						DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream());
						pOut.writeUTF(msgFromClient);
						pOut.flush();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}