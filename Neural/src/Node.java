import java.util.Random;


public class Node {
	private static Random rand;
	double[] dweights;
	double output;
	double bias;
	
	
	public Node(int l){
		bias = 1;
		dweights=new double[l];
		for (int i = 0; i < dweights.length; i++) {
			dweights[i]=rand.nextDouble();
		}
		
	}
	
	static void init(){
		rand=new Random();
	};
}
