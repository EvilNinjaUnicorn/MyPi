package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

public class Server implements Runnable {

	private Thread thread;
	private OutputStream os;
	private PrintWriter writer;
	private InputStream inputStream;
	private BufferedReader reader;
	private Socket socket;
	private Process process;
	private ServerLogin serverLogin;

	public Server(Socket socket, int clientId, ServerLogin serverLogin)
			throws IOException {

		this.serverLogin = serverLogin;

		thread = new Thread(this);

		this.socket = socket;

		os = socket.getOutputStream();
		writer = new PrintWriter(os);

		inputStream = socket.getInputStream();
		reader = new BufferedReader(new InputStreamReader(inputStream));

		String password = reader.readLine();

		if (password.equals("123")) {

			thread.start();
			System.out.println("Login erfolgreich");
			schreibeAnClient("Login erfolgreich");

		} else {

			this.serverLogin.remove(this);
			System.out.println("Login fehlgeschlagen");
			schreibeAnClient("Login fehlgeschlagen");

		}

	}

	@Override
	public void run() {

		String eingabe;

		while (true) {

			try {
				while ((eingabe = reader.readLine()) != null) {

					if (eingabe != "") {

						System.out.println("Befehl: " + eingabe);

						String cmd = "";
						Vector<String> cmdsVektor = new Vector();

						// cmds.add("/bin/sh");
						// cmds.add("-c");

						for (int i = 0; i < eingabe.length(); i++) {

							if (eingabe.charAt(i) == ' ') {

								cmdsVektor.add(cmd);
								cmd = "";

							} else {

								cmd += eingabe.charAt(i);
							}

						}

						cmdsVektor.add(cmd);
						String[] cmds = cmdsVektor.toArray(new String[cmdsVektor.size()]);

						process = Runtime.getRuntime().exec(cmds);
						InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
						BufferedReader cmdReader = new BufferedReader(inputStreamReader);
						//OutputStreamWriter outputStreamWriter = new OutputStreamWriter(process.getOutputStream());
						//BufferedWriter cmdWriter = new BufferedWriter(outputStreamWriter);

						String ausgabe;

						while ((ausgabe = cmdReader.readLine()) != null) {

							schreibeAnClient(ausgabe);

						}
						
					}
				
				}

				process.destroy();
				schreibeAnClient("-----------");

			} catch (IOException e) {
				if (process != null) {
					process.destroy();
				}

				e.printStackTrace();
				continue;

			}

		}

	}

	public void start() {

		if (!thread.isAlive()) {
			thread.run();
		}

	}

	public void schreibeAnClient(String msg) {

		writer.write(msg + "\n");
		writer.flush();

	}

}
