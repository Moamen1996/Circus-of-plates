package model;

import java.util.List;

import org.apache.log4j.Logger;

import avatar.Avatar;

public class OneStackEndSystem implements EndSystemStrategy {
	private static final Logger LOGGER = Logger.getLogger(OneStackEndSystem.class);

	public OneStackEndSystem() {
		LOGGER.info("One full stack ends the game system chosen");
	}

	@Override
	public boolean gameEnded(List<Avatar> avatars) {
		for (Avatar avatar : avatars) {
			if (avatar.hasFullStack()) {
				LOGGER.info("A stack is full.. game ended");
				return true;
			}
		}
		LOGGER.info("No full stacks exist");
		return false;
	}

}
