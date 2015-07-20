package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ClientGui extends JFrame {

	private JPanel contentPane;
	private JTextField tfEingabe;
	private JButton bSenden;
	private JButton bBeenden;
	private JScrollPane spTerminal;
	private JLabel lTerminal;
	private JPanel pEingabe;

	private String chatText;
	
	private Socket socket;
	private OutputStream os;
	private PrintWriter writer;
	private InputStream inputStream;
	private BufferedReader reader;
	private ClientThread clientThread;

	public ClientGui() throws UnknownHostException, IOException {

		initFrame();

		int portWert;
		String ipAdresse;

		
		JTextField tfIp = new JTextField();
		JTextField tfPort = new JTextField();
		JPasswordField pfPassword = new JPasswordField();
		
		
		do {
		
		Object[] message = { "Ip-Adresse", tfIp, "Port", tfPort, "Password", pfPassword };


		JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		pane.createDialog(null, "Login").setVisible(true);
	

		} while (!pfPassword.getText().equals("123"));
		
		
		ipAdresse = tfIp.getText();
		portWert = Integer.parseInt(tfPort.getText());
	
		InetAddress ipAddress = InetAddress.getByName(ipAdresse);		

		socket = new Socket(ipAddress, portWert);

		System.out.println("Client gestartet");

		os = socket.getOutputStream();
		writer = new PrintWriter(os);

		inputStream = socket.getInputStream();
		reader = new BufferedReader(new InputStreamReader(inputStream));

		setTitle("Terminal");

		clientThread = new ClientThread(this);
		
		chatText = new String();
		chatText = "";

	}

	private void initFrame() {

		contentPane = new JPanel();
		add(contentPane);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		tfEingabe = new JTextField();
		tfEingabe.setPreferredSize(new Dimension(350, 25));
		bBeenden = new JButton("Beenden");
		bSenden = new JButton("Senden");
		pEingabe = new JPanel();
		
		
		lTerminal = new JLabel("Terminal");
		spTerminal = new JScrollPane(lTerminal);

		pEingabe.add(tfEingabe);
		pEingabe.add(bSenden);
		pEingabe.add(bBeenden);

		contentPane.add(spTerminal, BorderLayout.CENTER);
		contentPane.add(pEingabe, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		bBeenden.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					socket.close();
					writer.close();
					reader.close();
					inputStream.close();
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.exit(DISPOSE_ON_CLOSE);

			}
		});

		bSenden.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					sendeZuServer(tfEingabe.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				tfEingabe.setText("");

			}
		});

		
		
		tfEingabe.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {


				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					

					try {
						sendeZuServer(tfEingabe.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					tfEingabe.setText("");
					
				}
				
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		

	}

	public void sendeZuServer(String msg) throws IOException {

		writer.write(msg + "\n");
		writer.flush();
		writer.write("");
		writer.flush();

	}

	public String leseVonServer() throws IOException {

		return reader.readLine();
	}
	
	public void addChatText(String str) {

		chatText += str +"<br>";
		lTerminal.setText("<html>" +chatText +"</html>");

		
	}

}
