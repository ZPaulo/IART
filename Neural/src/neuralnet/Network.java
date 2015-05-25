package neuralnet;

public class Network {

	public static Node[][] network;
	public static double[][] input;
	static double target;
	static double error;

	static public void main(String[] args) {
		network = new Node[3][];
		network[0] = new Node[26];
		network[1] = new Node[27];// TODO provisorio
		network[2] = new Node[1];
		Node.init();
		for (int i = 1; i < network.length - 1; i++) {
			for (int j = 0; j < network[i].length; j++) {
				network[i][j] = new Node(network[i - 1].length, i);
			}
		}
	}

	static void forward() {
		for (int i = 1; i < network.length; i++) {
			for (int j = 0; j < network[i].length; j++) {
				network[i][j].forward();
			}
		}
		error = target - network[network.length - 1][0].getOutput();
	}

}