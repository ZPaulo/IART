package neuralnet;

import java.util.ArrayList;

public class Network {

	public static Node[][] network;
	public static ArrayList<ArrayList<Double>> input;
	static double target;
	static double error, realError, sumErrors;
	static int numErrors;
	static double learningRate;

	static public void Init() {
		
		network = new Node[3][];
		network[0] = new Node[input.get(0).size()];
		network[1] = new Node[45];// TODO provisorio
		network[2] = new Node[1];
		Node.init();

		for (int i = 1; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				network[i][j] = new Node(network[i - 1].length, i);
			}
		}
		// teste
		target = 0.7;
		error = 1;
		realError = 1;
		learningRate = 0.9;
		for (int i = 0; i < network[0].length; i++) {
			if (i % 2 == 0)
				network[0][i] = new Node(0);
			else
				network[0][i] = new Node(0);
		}

		while (realError > 0.0001) {

<<<<<<< HEAD
			numErrors = 0;
			sumErrors = 0;
			
			for(int i = 0; i < input.size(); i++){
				for(int j = 0; j < input.get(i).size(); j++){
					network[0][j].output = input.get(i).get(j);
				}
				forward();
				backPropagation();
			}
			realError = (1/(2*numErrors)) * sumErrors;

			System.out.println("Error is " + realError);
=======
			System.out.println("Output is "
					+ network[network.length - 1][0].getOutput());
>>>>>>> branch 'master' of https://github.com/ZPaulo/IART.git
		}

	}

	static void forward() {
		for (int i = 1; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				network[i][j].forward();
			}
		}
		error = network[network.length - 1][0].getOutput() - target;
		sumErrors += error*error;
		numErrors++;
	}

	static void backPropagation() {

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
