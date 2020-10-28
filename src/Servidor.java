import java.io.*;
import java.net.*;

public class Servidor {
	private int puerto;
	
	public Servidor(int puerto) {
		this.puerto = puerto;
		ServerSocket servidor = null;
		
		try {
			servidor = new ServerSocket(this.puerto);
			System.out.println("Server iniciado - Esperando clientes..");
			
			for(int i = 1; i <= 3; i++) {
				Socket cliente = servidor.accept();
				System.out.println("Se conecto el cliente " + i);
				DataInputStream entrada = new DataInputStream(cliente.getInputStream());
				DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
				
				salida.writeUTF("Hola cliente " + i );
				System.out.println("Cliente " + i + ": " + entrada.readUTF());
				
				salida.close();
				cliente.close();
				System.out.println("Cliente " + i + " saliendo..");
			}
			servidor.close();
			System.out.println("Servidor cerrado");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Servidor(10000);
	}
	
}
