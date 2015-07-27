package PianoClient;

import java.util.Vector;

public class Buffer implements Runnable {

	private Vector<SampleAbspielen> sampleAbspielen;
	private Thread thread;
	private Vector<String> gedrueckteTasten;

	public Buffer(Vector<String> gedrueckteTasten) {

		this.sampleAbspielen = new Vector();
		thread = new Thread(this);
		thread.start();
		this.gedrueckteTasten = gedrueckteTasten;

	}

	@Override
	public void run() {
		
		
		
		while (true) {
			
				if (gedrueckteTasten.size() >= 1) {
						sampleAbspielen.add(new SampleAbspielen(this, "/PianoSample/" + gedrueckteTasten.firstElement() + ".wav"));
						gedrueckteTasten.remove(gedrueckteTasten.firstElement());
						
				}
			
		}
		
		
	}

	public boolean remove(Object o) {
		return sampleAbspielen.remove(o);

	}

}
