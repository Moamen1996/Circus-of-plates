package model;

import java.util.List;

import org.apache.log4j.Logger;

import avatar.Avatar;

public class AllStacksEndSystem implements EndSystemStrategy {
	private static final Logger LOGGER = Logger.getLogger(AllStacksEndSystem.class);

	public AllStacksEndSystem() {
		LOGGER.info("All stacks are full ends the game system chosen");
	}

	@Override
	public boolean gameEnded(List<Avatar> avatars) {
		for (Avatar avatar : avatars) {
			if (!avatar.checkStackFull()) {
				LOGGER.info("A stack is not full yet");
				return false;
			}
		}
		LOGGER.info("All stacks are full.. game ended");
		return true;
	}

}
