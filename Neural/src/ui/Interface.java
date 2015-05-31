package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import neuralnet.Network;

public class Interface extends JPanel implements DropTargetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame f;

	public static void main(String[] args) {
		new Interface(args);
	}

	/**
	 * Create the frame.
	 */
	public Interface(String[] args) {
		f = new JFrame();
		String[] arg = null;
		Boolean flag = false;
		while (!flag) {
			String input = JOptionPane
					.showInputDialog("Enter the number of nodes you want for intermediate nodes separated by spaces(min number of nodes is 7, max number of intermediate layers is 9");
			arg = input.split("\\s+");
			if (arg.length == 0) {
				JOptionPane.showMessageDialog(f,
						"The number of intermediate layers have to be > 1.");
			} else {
				for (int i = 0; i < arg.length; i++) {
					if (Integer.parseInt(arg[i]) < 7) {
						flag = true;
						break;
					}
				}
				if (flag) {
					JOptionPane
							.showMessageDialog(f,
									"The number of nodes of intermediate layers have to be > 7.");
					flag = false;
				} else {
					break;
				}
			}
		}
		JTextArea text = new JTextArea();
		//JScrollPane scroll = new JScrollPane (text, 
		PrintStream printStream = new PrintStream(new TextAreaOutputStream(
				text, "Console"));
		System.setOut(printStream);
		JOptionPane.showMessageDialog(f, "Please wait...");
		Network.Init(arg);
		int w = (Network.network.length) * 210;
		this.setBounds(0, 0, w, 900);
		f.setTitle("Neural Networks");
		XYDataset ds = createDataset();
		JFreeChart chart = ChartFactory.createXYLineChart("Error graph", "Epoch",
				"Error", ds, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel cp = new ChartPanel(chart);
		chart.getXYPlot().getRangeAxis().setRange(0.195, 0.205);
		cp.setBounds(0, (int) this.getHeight() / 2, (int) this.getWidth() / 2,
				(int) this.getHeight() / 3);
		f.add(cp);
		text.setBounds((int) this.getWidth() / 2, (int) this.getHeight() / 2,
				(int) this.getWidth() / 2, (int) this.getHeight() / 3);
		text.setEditable(false);
		f.add(text);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(800, 600));
		f.setVisible(true);
		f.setSize(w, 840);
		f.setResizable(false);
		new DropTarget(f, this);
	}

	private static XYDataset createDataset() {

		DefaultXYDataset ds = new DefaultXYDataset();

		double[] d1 = new double[Network.epoch.size()];
		double[] d2 = new double[Network.arrError.size()];
		for (int i = 0; i < Network.epoch.size(); i++) {
			d2[i] = Network.epoch.get(i).doubleValue();
		}
		for (int i = 0; i < Network.arrError.size(); i++) {
			d1[i] = Network.arrError.get(i).doubleValue();
		}
		double[][] data = {d2, d1};

		ds.addSeries("Error variance", data);

		return ds;
	}

	public void paint(Graphics g) {
		super.paint(g);
		int x = 50, d = 0;
		for (int i = 0; i < Network.network.length; i++) {
			for (int j = 0; j < Network.network[i].length; j++) {
				if (i == 0) {
					if ((Network.network[i].length - 1) < 4 || j < 3) {
						g.setColor(Color.RED);
						g.fillOval(x, this.getHeight() / 8 + d, 50, 50);
						g.setColor(Color.BLACK);
						g.drawString(
								Double.toString(Network.network[i][j].output),
								x + 10, this.getHeight() / 8 + d + 30);
						for (int k = 0; k < 5; k++) {
							g.drawLine(x + 50, (int) this.getHeight() / 8 + 25
									+ 50 * j, x + 205, (int) this.getHeight()
									/ 8 + k * 45);
						}
						g.drawLine(x + 50, (int) this.getHeight() / 8 + 25 + 50
								* j, x + 205, (int) this.getHeight() / 8 + 280);

						d += 50;
						continue;
					} else if (j == 3) {
						g.setColor(Color.BLACK);
						g.fillOval(x + 20, this.getHeight() / 8 + 160, 10, 10);
						g.fillOval(x + 20, this.getHeight() / 8 + 160 + 10, 10,
								10);
						g.fillOval(x + 20, this.getHeight() / 8 + 160 + 20, 10,
								10);
						for (int k = 0; k < 6; k++) {
							if (k == 5) {
								g.drawLine(x + 50, (int) this.getHeight() / 8
										+ 70 + 50 * j, x + 205,
										(int) this.getHeight() / 8 + k * 45
												+ 55);
							} else
								g.drawLine(x + 50, (int) this.getHeight() / 8
										+ 70 + 50 * j, x + 205,
										(int) this.getHeight() / 8 + k * 45);
						}
					}
					if (j > 3 && j != Network.network[i].length - 2
							&& j != Network.network[i].length - 1) {
						continue;
					}
					if (j == Network.network[i].length - 1) {
						g.setColor(Color.RED);
						g.fillOval(x, this.getHeight() / 8 + 200, 50, 50);
						g.setColor(Color.BLACK);
						g.drawString(
								Double.toString(Network.network[i][j].output),
								x + 10, this.getHeight() / 8 + 200 + 30);
						d = 0;
					}

				} else if (i != 0 && i != Network.network.length - 1) {

					if (i + 1 != Network.network.length - 1 && j < 6) {
						if (j == 5) {
							for (int k = 0; k < 6; k++) {
								if (k == 5) {
									g.drawLine(x + 45, (int) this.getHeight()
											/ 8 + j * 50 + 50, x + 205,
											(int) this.getHeight() / 8 + k * 50
													+ 50);
								} else
									g.drawLine(x + 45, (int) this.getHeight()
											/ 8 + j * 50 + 50, x + 205,
											(int) this.getHeight() / 8 + k * 50);
							}
						} else {
							for (int k = 0; k < 6; k++) {
								if (k == 5) {
									g.drawLine(x + 45, (int) this.getHeight()
											/ 8 + j * 50, x + 205,
											(int) this.getHeight() / 8 + k * 50
													+ 50);
								} else
									g.drawLine(x + 45, (int) this.getHeight()
											/ 8 + j * 50, x + 205,
											(int) this.getHeight() / 8 + k * 50);
							}
						}

					}
					if ((Network.network[i].length - 1) < 6 || j < 5) {
						g.setColor(Color.BLACK);
						g.fillOval(x, this.getHeight() / 8 - 30 + d, 50, 50);
						d += 50;
						continue;
					} else if (j == 5) {
						g.setColor(Color.BLACK);
						g.fillOval(x + 20, this.getHeight() / 8 + 230, 10, 10);
						g.fillOval(x + 20, this.getHeight() / 8 + 230 + 10, 10,
								10);
						g.fillOval(x + 20, this.getHeight() / 8 + 230 + 20, 10,
								10);
					}
					if (j > 5 && j != Network.network[i].length - 2
							&& j != Network.network[i].length - 1) {
						continue;
					}
					if (j == Network.network[i].length - 1) {
						g.setColor(Color.BLACK);
						g.fillOval(x, this.getHeight() / 8 + 270, 50, 50);
						d = 0;
					}
				} else {
					g.setColor(Color.RED);
					g.fillOval(x, this.getHeight() / 8 + 80 * (j + 1), 50, 50);
					g.setColor(Color.BLACK);
					g.drawString(Double.toString(Network.network[i][j].output),
							x, this.getHeight() / 8 + 80 * (j + 1));
					g.drawLine(x - 155, (int) this.getHeight() / 8, x,
							this.getHeight() / 8 + 95 * (j + 1));
					g.drawLine(x - 155, (int) this.getHeight() / 8 + 45, x,
							this.getHeight() / 8 + 95 * (j + 1));
					g.drawLine(x - 155, (int) this.getHeight() / 8 + 90, x,
							this.getHeight() / 8 + 95 * (j + 1));
					g.drawLine(x - 155, (int) this.getHeight() / 8 + 135, x,
							this.getHeight() / 8 + 95 * (j + 1));
					g.drawLine(x - 155, (int) this.getHeight() / 8 + 180, x,
							this.getHeight() / 8 + 95 * (j + 1));
					g.drawLine(x - 155, (int) this.getHeight() / 8 + 280, x,
							this.getHeight() / 8 + 95 * (j + 1));

				}
			}
			x += 200;
		}
	}

	@Override
	public void dragEnter(DropTargetDragEvent event) {
		Transferable transferable = event.getTransferable();
		DataFlavor[] flavors = transferable.getTransferDataFlavors();

		for (DataFlavor flavor : flavors) {

			try {
				if (flavor.isFlavorJavaFileListType()) {

					@SuppressWarnings("unchecked")
					List<File> files = (List<File>) transferable
							.getTransferData(flavor);
					for (@SuppressWarnings("unused")
					File file : files) {
						String input = JOptionPane
								.showInputDialog("Enter the number of nodes you want for intermediate nodes separated by spaces(min number of nodes is 7, max number of intermediate layers is 9");
						String[] args = input.split("\\s+");
						Network.Init(args);
						f.repaint();
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void dragExit(DropTargetEvent arg0) {

	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {

	}

	@Override
	public void drop(DropTargetDropEvent arg0) {

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {

	}

}
