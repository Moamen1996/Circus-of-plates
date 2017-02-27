package model;

import view.Observer;

public interface Observable {

	public void attachObserver(Observer observer);

	public void notifyObserver();

	public void updateState();
}
