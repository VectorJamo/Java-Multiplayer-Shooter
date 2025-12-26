package audio.audioHandler;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import audio.SoundEffect;

public class SoundEffects {
	public static SoundEffect shootSound;
	public static SoundEffect explosionSound;
	
	public static void initSounds() {
		try {
			shootSound = new SoundEffect("res/audio/laserShoot.wav");
			explosionSound = new SoundEffect("res/audio/explosion.wav");
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
}
