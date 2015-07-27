package PianoClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

public class PianoClient {

	private Socket socket;
	private InputStreamReader inputStreamReader;
	private BufferedReader reader;
	private Vector<String> gedrueckteTasten;
	private Buffer buffer;

	public PianoClient() {

		Scanner s = new Scanner(System.in);
		// System.out.print("Ip-Adresse eingeben: ");
		// String ipAdresse = s.next();
		System.out.print("Port eingeben: ");
		int port = s.nextInt();

		try {
			socket = new Socket("192.168.178.30", port);

			System.out.println("Client gestartet");

			inputStreamReader = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(inputStreamReader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gedrueckteTasten = new Vector();
		
		buffer = new Buffer(gedrueckteTasten);

		String str = "";

		try {
			while ((str = reader.readLine()) != "shutdownServer") {

				System.out.println(str);
				gedrueckteTasten.add(str);
				

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}



}
