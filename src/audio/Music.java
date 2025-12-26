package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Music {
	
	private File soundFile;
	private AudioInputStream audioStream;
	
	private Clip audioClip;
	
	public Music(String path) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		soundFile = new File(path);
		audioStream = AudioSystem.getAudioInputStream(soundFile);
		
		audioClip = AudioSystem.getClip();
		audioClip.open(audioStream);
	}
	
	public void play() {
		audioClip.stop();
		audioClip.setFramePosition(0);
		audioClip.start();
	}
	
	public void loop() {
		audioClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		audioClip.stop();
	}
}
