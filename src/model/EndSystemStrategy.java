package model;

import java.util.List;

import avatar.Avatar;

public interface EndSystemStrategy {
	public boolean gameEnded(List<Avatar> avatars);
}
