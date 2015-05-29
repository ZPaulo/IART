package fileio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import neuralnet.Network;
import neuralnet.Node;

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

	static public void read_network(String filename,int inputSize) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		int layer=1;
		while ((line = br.readLine()) != null) {
			String nodes[]=line.split(";");
			ArrayList<Node> nodeLayer=new ArrayList<Node>();
			for (int i = 0; i < nodes.length; i++) {
				String[] node=nodes[i].split(",");
				Node n;
				if(layer==1)
					n=new Node(inputSize, layer);
				else
					n=new Node(Network.network[layer - 1].length, layer);
				for (int j = 0; j < node.length; j++) {
					n.dweights[j]=Double.parseDouble(node[i]);
				}
				nodeLayer.add(n);
			}
			layer++;
			Network.network[layer]=(Node[]) nodeLayer.toArray();
		}
		br.close();
	}

}