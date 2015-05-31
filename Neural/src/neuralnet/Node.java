package neuralnet;

import java.util.Random;

public class Node {
	private static Random rand;
	public double prevGradient;
	public double prevBiasDelta;
	public double[] prevDeltas;
	public double gradient;
	public double[] dweights;
	public double output, prevOutput;
	public double biasWeight;
	public double learningRate;
	int layer;
	double bias;

	public Node(int l, int layer,double lR) {
		bias = 1;
		learningRate = lR;
		dweights = new double[l];
		for (int i = 0; i < dweights.length; i++) {
			dweights[i] = rand.nextDouble();// TODO mudar isto
			if (rand.nextBoolean() == true)
				dweights[i] *= -1;
		}
		
		biasWeight = rand.nextDouble();// TODO mudar isto
		if (rand.nextBoolean() == true)
			biasWeight *= -1;
		
		prevBiasDelta = 2;
		prevOutput = 0;
		prevGradient = 0;
		gradient = 0;
		output = 0;
		this.layer = layer;
	}

	public Node(double d) {
		this.output = d;
		layer = 0;
		prevOutput = 2;
	}

	public void forward() {
		double sum = bias * biasWeight;
		prevOutput = output;
		for (int i = 0; i < dweights.length; i++) {
			sum += dweights[i] * Network.network[layer - 1][i].getOutput();
		}
		output = sigmoid(sum);
	}

	public double getOutput() {
		return output;
	}

	static void init() {
		rand = new Random();
	};

	static double sigmoid(double x) {
		return (1.0) / (1.0 + Math.exp(-x));
	}
}
