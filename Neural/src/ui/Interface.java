package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import neuralnet.Network;

public class Interface extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int nNodes;
	
	public static void main(String[] args)
	{
		new Interface();
	}
	
	/**
	 * Create the frame.
	 */
	public Interface() {
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Network.Init();
			}
		});
		t.start();
		
		JFrame f = new JFrame();
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600,600);
		f.setVisible(true);
		contentPane.setBackground(Color.WHITE);
		nNodes = Network.network[1].length + 2;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		int x = 100, y = 300;
		for(int i = 0; i < Network.network.length; i++) {
			for(int j = 0; j < Network.network[i].length; j++) {
				g.setColor(Color.GREEN);
				if(i == 0) {
					g.fillOval(x, y, 40, 40);
					g.setColor(Color.BLACK);
					g.drawString(String.valueOf(Network.network[i][j].output), x, y);
				}
				if(i != 0 && i != nNodes) {
					int d;
					d = (this.getHeight() / Network.network[i].length * 40) / Network.network[i].length;
				}
			}
		}
	}

}
