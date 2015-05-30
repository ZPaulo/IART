package neuralnet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import fileio.Input;

public class Network {

	public static Node[][] network;
	public static ArrayList<ArrayList<Double>> input, inputCopy;
	static double target;
	static double error, realError, sumErrors,prevRealError;
	static double numErrors;
	static double learningRate;
	static int numHiddenLayers;
	static int[] numNodes;
	static int numHits,numTests; 

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
		network[numHiddenLayers + 1] = new Node[1];

		Node.init();

		network[0] = new Node[input.get(0).size() - 3];

		for (int i = 1; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				network[i][j] = new Node(network[i - 1].length, i);
			}
		}

		realError = 1;
		prevRealError = 0;
		learningRate = 0.1;
		int numTimes = 0;

		while (numTimes < 2000) {

			numTimes++;

			if(realError < 0.001)
				break;

			numErrors = 0;
			sumErrors = 0;
			prevRealError = realError;

			randomInput();
			realError = sumErrors / (2*numErrors);
			if(numTimes % 100 == 0)
				System.out.println("Error is " + realError);
		}

		testFase();

	}

	public static void testFase() {
		
		try {
			Input.read_input("test_data.txt");
		} catch (IOException e) {
			System.out.println("Couldn't read file");
		}
		
		numHits = 0;
		numTests = 0;
		
		for(int i = 0; i < input.size(); i++){
			network[0] = new Node[input.get(i).size() - 2];
			for(int j = 1; j < input.get(i).size() - 1; j++){
				network[0][j-1] = new Node(input.get(i).get(j));
			}
			
			forward();
			target = input.get(i).get(input.get(i).size()-1);
			if(Math.ceil(network[numHiddenLayers+1][0].output) == target)
				numHits++;
			numTests++;
		}

		
		
		
		System.out.println("Final error is " + realError);
		System.out.println("Correct output was achieved in " + (numHits / numTests)*100 + "% of the times");
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

					if(network[i][j].prevWeights != null){

						network[i][j].dweights[k] -= (learningRate * deltaW + network[i][j].prevWeights[k] * 0.4);	
						network[i][j].prevWeights[k] = network[i][j].dweights[k];
					}
					else{
						network[i][j].prevWeights = new double[network[i][j].dweights.length];
						network[i][j].dweights[k] -= (learningRate * deltaW);
					}
				}
				//modificar o peso do bias
				if(network[i][j].prevBiasWeight != 2){
					network[i][j].biasWeight -= (learningRate * network[i][j].gradient * network[i][j].bias + 0.4 * network[i][j].prevBiasWeight);
					network[i][j].prevBiasWeight = network[i][j].biasWeight;
				}
				else{
					network[i][j].biasWeight -= learningRate * network[i][j].gradient * network[i][j].bias;
				}  
			}
		}

	}

	static void randomInput(){

		inputCopy = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < input.size();i++){
			ArrayList<Double> temp = new ArrayList<Double>();
			for(int j = 0; j < input.get(i).size(); j++)
				temp.add(input.get(i).get(j));
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
			network[0] = new Node[inputCopy.get(randomPos).size() - 3];

			for(int j = 1; j < inputCopy.get(randomPos).size()-2; j++){
				network[0][j-1] = new Node(inputCopy.get(randomPos).get(j));
			}

			target = inputCopy.get(randomPos).get(input.get(randomPos).size()-1);
			forward();
			backPropagation();

			inputCopy.remove(randomPos);

		}
	}

}
