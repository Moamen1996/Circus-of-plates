package saving;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter {

	public JsonWriter() {
	}

	public void save(StateBundle stateBundle, String filePath) throws IOException {
		filePath = filePath + ".json";
		initializeJsonFile(filePath);
		Gson gsonState = new GsonBuilder().setPrettyPrinting().create();
		String jsonWriter = gsonState.toJson(stateBundle);
		System.out.println(filePath);
		FileWriter writer = new FileWriter(filePath);
		writer.write(jsonWriter);
		writer.close();
	}

	private void initializeJsonFile(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
	}

	public StateBundle load(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		Gson gsonState = new Gson();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		StateBundle stateBundle = gsonState.fromJson(bufferedReader, StateBundle.class);
		return stateBundle;
	}

}