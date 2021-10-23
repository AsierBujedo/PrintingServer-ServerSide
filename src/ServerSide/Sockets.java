package ServerSide;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import Printer.Printer;


public class Sockets {
	Printer printer = new Printer();
	public void ServerInit(int PORT) throws IOException {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Servidor abierto");
		} catch (IOException ex) {
			System.out.println("No se pudo abrir el servidor en el puerto: " + PORT);
		}

		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;

		try {
			socket = serverSocket.accept();
			System.out.println("conexión aceptada");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			in = socket.getInputStream();
		} catch (IOException ex) {
			System.out.println("no se ha podido obtener el input stream");
		}

		try {
			out = new FileOutputStream("Output.pdf");
		} catch (FileNotFoundException ex) {
			System.out.println("File not found. ");
		}

		byte[] bytes = new byte[16 * 1024];

		int count;
		while ((count = in.read(bytes)) > 0) {
			out.write(bytes, 0, count);
		}
		File f = new File("Output.pdf");
		printer.printPDF(f);
		out.close();
		in.close();
		socket.close();
		serverSocket.close();

	}
}
