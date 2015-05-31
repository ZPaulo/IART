package neuralnet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import fileio.Input;

public class Network {

	public static Node[][] network;
	public static ArrayList<ArrayList<ArrayList<Double>>> input, inputCopy;
	public static ArrayList<ArrayList<Double>> oldinput;
	static double[] target;
	static double error, realError, sumErrors;
	static double numErrors;
	static double learningRate,momentum;
	static int numHiddenLayers;
	static int[] numNodes;
	static double numHits,numTests;

	static public void Init(String[] args) {

		try {
			Input.read_input("train_data.txt");
		} catch (IOException e) {
			System.out.println("Couldn't read file");
		}

		numHiddenLayers = args.length;

		network = new Node[numHiddenLayers + 2][];

		for(int i = 0; i < args.length; i++){
			network[i+1] = new Node[Integer.parseInt(args[i])];
		}
		network[numHiddenLayers + 1] = new Node[2];

		Node.init();

		network[0] = new Node[input.get(0).size() - 3];

		for (int i = 1; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				network[i][j] = new Node(network[i - 1].length, i);
			}
		}

		realError = 1;
		learningRate = 0.05;
		momentum = 0.3;
		target = new double[2];
		int numTimes = 0;

		while (numTimes < 2000) {

			numTimes++;

			if(realError < 0.001)
				break;

			numErrors = 0;
			sumErrors = 0;

			randomInput();
			realError = sumErrors / (2*numErrors);
			//System.out.println("Error is " + realError );
			if(numTimes % 100 == 0){
				System.out.println("Error is " + realError + " ------- Epoch is " + numTimes);
			}
		}

		testFase();

	}

	public static void testFase() {

		try {
			Input.old_read_input("test_data.txt");
		} catch (IOException e) {
			System.out.println("Couldn't read file");
		}

		numHits = 0.0;
		numTests = 0.0;

		for(int i = 0; i < oldinput.size(); i++){
			network[0] = new Node[oldinput.get(i).size() - 2];
			for(int j = 1; j < oldinput.get(i).size() - 1; j++){
				network[0][j-1] = new Node(oldinput.get(i).get(j));
			}

			forward();
			target[0] = oldinput.get(i).get(oldinput.get(i).size()-1);


			if(((double) Math.round(network[numHiddenLayers+1][0].output)) == target[0]){
				numHits++;
				System.out.println("Output1 is " + network[network.length - 1][0].output);
				System.out.println("Output2 is " + network[network.length - 1][1].output);
				System.out.println(target[0]);

			}
			numTests++;
		}

		System.out.println("Final error is " + realError);
		System.out.println("Output is " + network[network.length - 1][0].output);
		System.out.println(target[0]);
		double hitRatio = (numHits / numTests);
		System.out.println("Correct output was achieved " + hitRatio * 100.0 + "% of the times");
	}

	static void forward() {
		for (int i = 1; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				network[i][j].forward();
			}
		}
	}

	static void backPropagation() {
		error = 0;

		//calculo do gradiente em nos de output
		for(int i = 0; i < network[network.length - 1].length; i++){
			error += network[network.length - 1][i].output - target[i];
			network[network.length - 1][i].gradient = network[network.length - 1][i].output
					* (1-network[network.length - 1][i].output)
					* (network[network.length - 1][i].output - target[i]);
		}


		sumErrors += error*error;
		numErrors++;

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
					double deltaW = network[i][j].gradient
							* network[i - 1][k].getOutput();

					if(network[i][j].deltaWeights != null){

						network[i][j].acumWeights[k]  += (learningRate * deltaW + network[i][j].deltaWeights[k] * momentum);
						network[i][j].deltaWeights[k] = learningRate * deltaW;
					}
					else{
						network[i][j].deltaWeights = new double[network[i][j].dweights.length];
						network[i][j].deltaWeights[k] = learningRate * deltaW;
						network[i][j].acumWeights[k] += (learningRate * deltaW);
					}

				} 
				//modificar o peso do bias
				if(network[i][j].prevBiasDelta != 2){
					network[i][j].acumB += learningRate * network[i][j].gradient * network[i][j].bias + momentum * network[i][j].prevBiasDelta;
					network[i][j].prevBiasDelta = learningRate * network[i][j].gradient * network[i][j].bias;
				}
				else{
					network[i][j].acumB += learningRate * network[i][j].gradient * network[i][j].bias;
					network[i][j].prevBiasDelta = learningRate * network[i][j].gradient * network[i][j].bias;
				} 
			}
		}

	}

	public static void updateWeights() {
		for (int i = network.length - 1; i > 0; i--) {
			for (int j = 0; j < network[i].length; j++) {
				for (int k = 0; k < network[i][j].dweights.length; k++) {
					network[i][j].dweights[k] += network[i][j].acumWeights[k];
					network[i][j].acumWeights[k] = 0;
				}
				network[i][j].biasWeight += network[i][j].acumB;
				network[i][j].acumB = 0;
			}
		}
	}

	static void randomInput(){

		inputCopy = new ArrayList<ArrayList<ArrayList<Double>>>();
		for(int i = 0; i < input.size();i++){
			ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
			for(int j = 0; j < input.get(i).size(); j++){
				ArrayList<Double> temp1 = new ArrayList<Double>();
				for(int k = 0; k < input.get(i).get(j).size(); k++)
					temp1.add(input.get(i).get(j).get(k));
				temp.add(temp1);
			}
			inputCopy.add(temp);
		}

		Random rand = new Random();

		boolean turn = true;
		while(inputCopy.size() != 0){
			int randomPos;
			if(turn){
				randomPos = rand.nextInt((inputCopy.size() - 1)/2 + 1);
				if (rand.nextBoolean() == true)
					turn = false;
			}
			else{
				randomPos = rand.nextInt(((inputCopy.size() - 1) - ((inputCopy.size() - 1)/2)) + 1) + (inputCopy.size() - 1)/2;
				if (rand.nextBoolean() == true)
					turn = true;
			}
			for(int i = 0; i < inputCopy.get(randomPos).size();i++){
				network[0] = new Node[inputCopy.get(randomPos).get(i).size() - 3];
				for(int j = 1; j < inputCopy.get(randomPos).get(i).size()-2; j++){
					network[0][j-1] = new Node(inputCopy.get(randomPos).get(i).get(j));
				}

				target[0] = inputCopy.get(randomPos).get(i).get(input.get(randomPos).get(i).size()-1);
				if(target[0] == 0)
					target[1] = 0;
				else
					target[1] = inputCopy.get(randomPos).get(i).get(input.get(randomPos).get(i).size()-2) / 100.0;
					
				forward();
				backPropagation();
			}
			updateWeights();
			inputCopy.remove(randomPos);

		}

	}

}
