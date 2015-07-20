package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class ServerLogin {

	private Socket socket;
	private ServerSocket serverSocket;
	private Vector<Server> server;
	private int clientId;

	public ServerLogin() throws IOException {

		Scanner s = new Scanner(System.in);
		System.out.print("Bitte Port eingeben: ");

		serverSocket = new ServerSocket(s.nextInt());

		server = new Vector<>();

		System.out.println(Inet4Address.getLocalHost().getHostAddress());
		System.out.println("Server gestartet");

		clientId = 0;

		while (true) {
			socket = serverSocket.accept();
			server.add(new Server(socket, clientId, this));
			clientId++;
		}

	}

}
