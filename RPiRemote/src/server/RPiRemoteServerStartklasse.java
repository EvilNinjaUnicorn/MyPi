package server;

import java.io.IOException;

public class RPiRemoteServerStartklasse {

	public static void main(String[] args) {

		try {
			ServerLogin serverLogin = new ServerLogin();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
