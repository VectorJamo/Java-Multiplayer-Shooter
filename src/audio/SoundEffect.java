package audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class SoundEffect {
	
	private File soundFile;
	private AudioInputStream audioStream;
	
	private Clip audioClip;
	
	public SoundEffect(String path) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
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
	
	public void stop() {
		audioClip.stop();
	}
}