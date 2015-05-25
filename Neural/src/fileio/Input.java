package fileio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import neuralnet.Network;

public class Input {
	static public void read_input(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		int n = 0;
		while ((line = br.readLine()) != null) {
			String[] inputs = line.split(",");
			for (int i = 0; i < inputs.length; i++)
				Network.input[n][i] = Double.parseDouble(inputs[i]);
			++n;
		}
		br.close();
	}
}
