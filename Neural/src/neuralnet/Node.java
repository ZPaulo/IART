package neuralnet;

import java.util.Random;

public class Node {
	private static Random rand;
	public double delta;
	public double[] dweights;
	double output;
	int layer;
	double bias;

	public Node(int l, int layer) {
		bias = 1;
		dweights = new double[l];
		for (int i = 0; i < dweights.length; i++) {
			dweights[i] = rand.nextDouble();// TODO mudar isto
			if (rand.nextBoolean() == true)
				dweights[i] *= -1;
		}
		this.layer = layer;
	}

	public Node(double d) {
		this.output = d;
		layer = 0;
	}

	public void forward() {
		double sum = bias;
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
