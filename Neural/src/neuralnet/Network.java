package neuralnet;

import java.io.IOException;
import java.util.ArrayList;

import fileio.Input;

public class Network {

	public static Node[][] network;
	public static ArrayList<ArrayList<Double>> input;
	static double target;
	static double error, realError, sumErrors;
	static double numErrors;
	static double learningRate;
	static int numHiddenLayers;
	static int[] numNodes;

	static public void Init(String[] args) {
		
		try {
			Input.read_input("train_data.txt");
		} catch (IOException e) {
			System.out.println("Couldn't read file");
		}
		
		numHiddenLayers = Integer.parseInt(args[0]);
		
		network = new Node[numHiddenLayers + 2][];
		
		for(int i = 1; i < args.length; i++){
			network[i] = new Node[Integer.parseInt(args[i])];
		}
		network[numHiddenLayers + 1] = new Node[1];
		
		Node.init();

		network[0] = new Node[input.get(0).size() - 2];

		for (int i = 1; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				network[i][j] = new Node(network[i - 1].length, i);
			}
		}
		
		realError = 1;
		learningRate = 0.5;

		while (realError > 0.01) {

			numErrors = 0;
			sumErrors = 0;
			
			
			for(int i = 0; i < input.size(); i++){

				network[0] = new Node[input.get(i).size() - 2];

				for(int j = 0; j < network[0].length; j++){
					network[0][j] = new Node(input.get(i).get(j));
				}
				
				target = input.get(i).get(input.get(i).size()-1);
				forward();
				backPropagation();
			}
			realError = sumErrors / (2*numErrors);
			System.out.println("Error is " + realError);
		}
		
		network[0] = new Node[input.get(0).size() - 2];
		for(int j = 0; j < network[0].length; j++){
			network[0][j] = new Node(input.get(0).get(j));
		}
		
		forward();
		System.out.println("Output is " + network[2][0].output);
		System.out.println("Target is " + input.get(0).get(input.get(0).size()-1));

	}

	static void forward() {
		for (int i = 1; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				network[i][j].forward();
			}
		}
	}

	static void backPropagation() {
		

		error = network[network.length - 1][0].getOutput() - target;
		sumErrors += error*error;
		numErrors++;

		network[network.length - 1][0].gradient = network[network.length - 1][0]
				.getOutput()
				* (1 - network[network.length - 1][0].getOutput())
				* error;

		for (int i = network.length - 2; i > 0; i--) {
			for (int j = 0; j < network[i].length; j++) {
				// retropropagar o erro para as camadas seguintes
				double sum = 0;
				for (int k = 0; k < network[i + 1].length; k++) {
					sum += network[i + 1][k].gradient
							* network[i + 1][k].dweights[j];
				}
				network[i][j].gradient = network[i][j].getOutput()
						* (1 - network[i][j].getOutput()) * sum;
			}
		}

		for (int i = network.length - 1; i > 0; i--) {
			for (int j = 0; j < network[i].length; j++) {
				for (int k = 0; k < network[i][j].dweights.length; k++) {
					// modificar os pesos
					double deltaW = network[i][j].gradient
							* network[i - 1][k].getOutput();
					network[i][j].dweights[k] -= learningRate * deltaW;
				}
				//modificar o peso do bias
				network[i][j].biasWeight -= learningRate * network[i][j].gradient * network[i][j].bias;  
			}
		}

	}

}
