package fileio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import neuralnet.Network;

public class Output {
	static public void writeNet(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = "";
		for (int i = 0; i < Network.network.length; i++)
			for (int j = 0; j < Network.network[i].length; j++) {
				for (int k = 0; k < Network.network[i][j].dweights.length; k++) {
					line += Double.toString(Network.network[i][j].dweights[k])
							+ ",";
				}
			}
	}
}
