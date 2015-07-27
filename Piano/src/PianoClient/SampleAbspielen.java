package PianoClient;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.sound.sampled.*;

/**
 * Spielt ein Sample ab
 * 
 * @author Emanuel
 * @version 0.1
 */

public class SampleAbspielen implements Runnable {

	private Mixer mixer;
	private Clip clip;
	private DataLine.Info dataInfo;
	private Mixer.Info[] mixerInfos;
	private Buffer Buffer;
	private String sample;
	private Thread thread;

	/**
	 * Spielt einen Clap-Ton ab
	 * 
	 * @author Emanuel
	 */

	public SampleAbspielen(Buffer Buffer, String sample) {

		this.Buffer = Buffer;
		this.sample = sample;
		thread = new Thread(this);
		thread.start();
		

	}

	@Override
	public void run() {

		try {

			File file = new File(SampleAbspielen.class.getResource(sample).toURI());

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			AudioFormat format = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);

			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioInputStream);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			clip = null;

			e.printStackTrace();
		}

		if (clip != null) {
			clip.start();

			
				try {
					thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			clip.close();
		}

		Buffer.remove(this);

	}

}
