package neuralnet;

public class Network {

	public static Node[][] network;
	public static double[][] input;
	static double target;
	static double error;
	static double learningRate;

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
		
		while(error > 0.001){
			forward();
			backPropagation();
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

	static void backPropagation(){

		network[network.length-1][0].delta = network[network.length-1][0].getOutput()*(1-network[network.length-1][0].getOutput()) * error;

		for (int i = network.length - 1; i > 1; i--) { 
			for (int j = 0; j < network[i].length; j++) {

				//retropropagar o erro para as camadas seguintes
				if(i != network.length -1){
					double sum = 0;
					for(int k = 0; k < network[i+1].length;k++){
						sum += network[i+1][k].delta * network[i+1][k].dweights[j];
					}
					network[i][j].delta = network[i][j].getOutput() * (1-network[i][j].getOutput())*sum;
				}

				for(int k = 0; k < network[i][j].dweights.length; k++){				
					//modificar os pesos
					double deltaW = network[i][j].delta * network[i-1][k].getOutput();
					network[i][j].dweights[k] -= learningRate*deltaW;
				}
			}
		}
	}

}