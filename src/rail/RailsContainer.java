package rail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

public class RailsContainer {
	private static final Logger LOGGER = Logger.getLogger(RailsContainer.class);

	private List<Rail> rails;

	public RailsContainer() {
		rails = new ArrayList<Rail>();
	}

	public RailsContainer(ArrayList<Rail> rails) {
		this.rails = new ArrayList<Rail>(rails);
	}

	public void addRail(Rail rail) {
		rails.add(rail);
	}

	/**
	 * @return A random rail if exists else returns null.
	 */
	public Rail getRandomRail() {
		if (rails.isEmpty()) {
			return null;
		} else {
			LOGGER.debug("Random rail chosen");
			return rails.get((new Random()).nextInt(rails.size()));
		}
	}

	public List<Rail> getRails() {
		return rails;
	}
}
