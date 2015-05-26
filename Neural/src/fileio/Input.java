package fileio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import neuralnet.Network;

public class Input {
	static public void read_input(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		Network.input = new ArrayList<ArrayList<Double>>();
		while ((line = br.readLine()) != null) {
			String[] inputs = line.split(",");
			ArrayList<Double> ad = new ArrayList<Double>();
			for (int i = 0; i < inputs.length; i++)
				ad.add(Double.parseDouble(inputs[i]));
			Network.input.add(ad);
		}
		br.close();
	}

	static public void main(String args[]) throws IOException {
		read_input("train_data.txt");
		for (int i = 0; i < Network.input.size(); i++) {
			System.out.println("Input " + i);
			for (int j = 0; j < Network.input.get(i).size(); j++) {
				System.out.print(Network.input.get(i).get(j) + "-");
			}
		}
	}
}
