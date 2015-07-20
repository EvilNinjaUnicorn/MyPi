package client;

import java.io.IOException;

public class ClientStartklasse {

	public static void main(String[] args) {
		
		
		try {
			ClientGui clientGui = new ClientGui();
			clientGui.setVisible(true);
			clientGui.setSize(600, 500);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

	}

}
