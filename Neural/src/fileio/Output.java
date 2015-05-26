package fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import neuralnet.Network;

public class Output {
	static public void writeNet(String filename) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
		String line = "";
		for (int i = 1; i < Network.network.length; i++) {
			for (int j = 0; j < Network.network[i].length; j++) {
				for (int k = 0; k < Network.network[i][j].dweights.length; k++) {
					line += Double.toString(Network.network[i][j].dweights[k])
							+ ",";
				}
				line = line.substring(0, line.length() - 1) + ";";
			}
			line += "\n";
		}
		line = line.substring(0, line.length());
		System.out.println(line);
		bw.write(line);
		bw.close();
	}
}
