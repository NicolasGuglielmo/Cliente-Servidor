import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
	private int puerto;
	private String host;
	private String userName;
	
	public Cliente(String host, int puerto) {
		this.host = host;
		this.puerto = puerto;
		Socket cliente = null;
		
		try {
			cliente = new Socket(this.host, this.puerto);
			
			Scanner entradaTeclado = new Scanner(System.in);
			
			System.out.print("Ingrese su userName: ");
			String userName = entradaTeclado.next();
			setUserName(userName);
			
			DataInputStream entrada = new DataInputStream(cliente.getInputStream());
			DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
			System.out.println(entrada.readUTF());
			
			Scanner entradaTeclado1 = new Scanner(System.in);
			System.out.print("Que desea comunicar?: ");
			String mensaje = entradaTeclado1.nextLine();
			
			salida.writeUTF(mensaje);
			
			entradaTeclado.close();
			entradaTeclado1.close();
			entrada.close();
			cliente.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public static void main(String[] args) {
		new Cliente("localhost",10000);
	}
}
