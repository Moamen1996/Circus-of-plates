package controller;

import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import gui.Main;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
	private static final Logger LOGGER = Logger.getLogger(AudioController.class);

	private static final String BACKGROUND_MUSIC = "background_Music.mp3";
	private Media backgroundMusic;
	private MediaPlayer player;

	private static AudioController audioController;

	private AudioController() {
		String path;
		try {
			path = Main.class.getResource("/music" + "/" + BACKGROUND_MUSIC).toURI().toString();
			backgroundMusic = new Media(path);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static AudioController getInstance() {
		if (audioController == null) {
			audioController = new AudioController();
		}
		return audioController;
	}

	public void playBackgroundMusic() {
		player = new MediaPlayer(backgroundMusic);
		player.setCycleCount(MediaPlayer.INDEFINITE);
		player.play();
		LOGGER.info("Back ground music started");
	}

	public void pauseBackgroundMusic() {
		player.pause();
		LOGGER.info("Back ground music paused");
	}

	public void resumeBackgroundMusic() {
		player.play();
		LOGGER.info("Back ground music resumed");
	}

	public void mute() {
		player.setMute(true);
		LOGGER.info("Back ground music muted");
	}

	public void unmute() {
		player.setMute(false);
		LOGGER.info("Back ground music unmuted");
	}

	public void beginBackgroundMusic() {
		player.stop();
		player.play();
	}
}
