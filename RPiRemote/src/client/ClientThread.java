package client;

import java.io.IOException;

public class ClientThread implements Runnable {
	
	private ClientGui clientGui;
	private Thread thread;
	
	public ClientThread(ClientGui clientGui) {
		
		this.clientGui = clientGui;
		thread = new Thread(this);
		thread.start();
		
	}

	@Override
	public void run() {
		
		String str = "";
		
		while(true) {

		
		try {
			
			if ((str = clientGui.leseVonServer()) != "") {
				System.out.println(str);
				clientGui.addChatText(str);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	}
	
	public void start(){
		if (!thread.isAlive()) {
			thread.run();
		}
	}

}
