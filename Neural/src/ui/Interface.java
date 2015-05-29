package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import neuralnet.Network;

public class Interface extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int wNodes, hNodes;
	
	public static void main(String[] args)
	{
		new Interface(args);
	}
	
	/**
	 * Create the frame.
	 */
	public Interface(String[] args) {
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Network.Init(args);
			}
		});
		t.start();
		
		JFrame f = new JFrame();
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 600);
		f.setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		int x = 50, y = 50, d = 0;
		for(int i = 0; i < Network.network.length; i++) {
			if(i != 0 && i != Network.network.length) {
				y = 50;
			}
			for(int j = 0; j < Network.network[i].length; j++) {
				if(i == 0) {
					if((Network.network[i].length - 1) < 4 || j < 3) {
						g.setColor(Color.RED);
						g.fillOval(x, this.getHeight()/3 + d, 50, 50);
						g.setColor(Color.BLACK);
						g.drawString(Double.toString(Network.network[i][j].output), x, this.getHeight()/3 + d);
						d += 50;
						continue;
					}
					else if(j == 3) {
						g.setColor(Color.BLACK);
						g.fillOval(x, d + 300, 10, 10);
						g.fillOval(x, d + 300 + 10, 10, 10);
						g.fillOval(x, d + 300 + 20, 10, 10);
					}
					if (j > 3 && j != Network.network[i].length - 2 && j != Network.network[i].length - 1) {
						continue;
					}
					if(j == Network.network[i].length - 1) {
						g.setColor(Color.RED);
						g.fillOval(x, this.getHeight()/3 + d, 50, 50);
						g.setColor(Color.BLACK);
						g.drawString(Double.toString(Network.network[i][j].output), x, this.getHeight()/3  + d);
					}
					
				}
				else if(i != 0 && i != Network.network.length - 1) {
					
					if(j < 3 && j != Network.network[i].length - 1) {
						g.setColor(Color.BLACK);
						g.fillOval(x, y, 30, 30);
						g.drawString(Integer.toString(j), x, y);
					}
					if(j == Network.network[i].length - 1) {
						g.setColor(Color.BLACK);
						g.fillOval(x, y, 30, 30);
					}
					g.setColor(Color.BLACK);
					g.fillOval(x, y, 30, 30);
					g.drawString(Integer.toString(j), x, y);
				}
				else {
					g.setColor(Color.RED);
					g.fillOval(x, this.getHeight()/2 - 80, 40, 40);
					g.setColor(Color.BLACK);
					g.drawString(Double.toString(Network.network[i][j].output), x, this.getHeight()/2 - 80);
				}
			}
			x += 300;
		}
	}

}
