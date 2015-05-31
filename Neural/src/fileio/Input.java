package fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import neuralnet.Network;
import neuralnet.Node;

public class Input {
	static public void read_input(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		Network.input = new ArrayList<ArrayList<ArrayList<Double>>>();
		while ((line = br.readLine()) != null) {
			String[] inputs = line.split(",");
			ArrayList<Double> ad = new ArrayList<Double>();
			for (int i = 0; i < inputs.length; i++)
				ad.add(Double.parseDouble(inputs[i]));
			if(Network.input.size()<ad.get(0))
				Network.input.add(new ArrayList<ArrayList<Double>>());
			Network.input.get(ad.get(0).intValue()-1).add(ad);
		}
		br.close();
	}
	
	static public void old_read_input(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;
		Network.oldinput = new ArrayList<ArrayList<Double>>();
		while ((line = br.readLine()) != null) {
			String[] inputs = line.split(",");
			ArrayList<Double> ad = new ArrayList<Double>();
			for (int i = 0; i < inputs.length; i++)
				ad.add(Double.parseDouble(inputs[i]));
			Network.oldinput.add(ad);
		}
		br.close();
	}
	
	static public void aux(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		BufferedWriter bw = new BufferedWriter(new FileWriter("newfile.txt"));
		String line;
		while ((line = br.readLine()) != null) {
			String[] inputs = line.split(",");
			for (int i = 0; i < inputs.length-2; i++)
				bw.write(inputs[i]+",");
			bw.write(inputs[inputs.length-1]);
			bw.newLine();
		}
		bw.close();
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