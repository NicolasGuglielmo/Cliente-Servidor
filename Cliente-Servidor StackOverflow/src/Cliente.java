import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Cliente implements Runnable {

	Socket socketConexion;
	DataOutputStream enviarAlServidor;
	DataInputStream mensajeRecibido;


	Cliente() throws UnknownHostException, IOException {

		socketConexion = new Socket("127.0.0.1", 8000);
		enviarAlServidor = new DataOutputStream(socketConexion.getOutputStream());
		mensajeRecibido = new DataInputStream(socketConexion.getInputStream());
		
		SimpleDateFormat dateformat2 = new SimpleDateFormat();

		Thread thread;
		thread = new Thread(this);
		thread.start();

		BufferedReader br = null;
		String nick = null;
		Scanner entradaTeclado = new Scanner(System.in);
		String mensaje = "";
		try {
			System.out.print("Ingrese su nick: ");
			nick = entradaTeclado.next();
			nick += ": ";
			String msjHora = null;
			br = new BufferedReader(new InputStreamReader(System.in));
			while (!mensaje.equalsIgnoreCase("exit")) {
				mensaje = br.readLine();
				msjHora = "[" + dateformat2.format(new Date()) + "] ";
				enviarAlServidor.writeUTF(msjHora + nick + mensaje);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void run() {
		while (true) {
			try {
				System.out.println("\n" + mensajeRecibido.readUTF());

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	
	public static void main(String[] arg) throws UnknownHostException, IOException {
		Cliente client = new Cliente();
	}
}

//Elija que sala desea unirse (Le mostramos las salas abiertas)
//y que elija la/s sala/s con numeros separados por coma