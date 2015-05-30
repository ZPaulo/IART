package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import neuralnet.Network;

public class Interface extends JPanel implements DropTargetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
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
		JScrollPane scroll = new JScrollPane();
		this.add(scroll);
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int w = (Network.network.length) * 200;
		f.setPreferredSize(new Dimension(800, 600));
		f.setSize(w, 600);
		f.setVisible(true);
		new DropTarget(f, this);
	}

	public void paint(Graphics g) {
		super.paint(g);
		int x = 50, y = 0, d = 0;
		for (int i = 0; i < Network.network.length; i++) {
			if (i != 0 && i != Network.network.length) {
				y = 50;
			}
			for (int j = 0; j < Network.network[i].length; j++) {
				if (i == 0) {
					if ((Network.network[i].length - 1) < 4 || j < 3) {
						g.setColor(Color.RED);
						g.fillOval(x, this.getHeight() / 4 + d, 50, 50);
						g.setColor(Color.BLACK);
						g.drawString(
								Double.toString(Network.network[i][j].output),
								x + 15, this.getHeight() / 4 + d + 25);
						for (int k = 0; k < 5; k++) {
							g.drawLine(x + 50, (int) this.getHeight() / 4 + 25
									+ 50 * j, x + 205, (int) this.getHeight()
									/ 4 + k * 45);
						}
						g.drawLine(x + 50, (int) this.getHeight() / 4 + 25 + 50
								* j, x + 205, (int) this.getHeight() / 4 + 280);

						d += 100;
						continue;
					} else if (j == 3) {
						g.setColor(Color.BLACK);
						g.fillOval(x + 20, this.getHeight() / 4 + 160, 10, 10);
						g.fillOval(x + 20, this.getHeight() / 4 + 160 + 10, 10,
								10);
						g.fillOval(x + 20, this.getHeight() / 4 + 160 + 20, 10,
								10);
						for (int k = 0; k < 6; k++) {
							if (k == 5) {
								g.drawLine(x + 50, (int) this.getHeight() / 4
										+ 70 + 50 * j, x + 205,
										(int) this.getHeight() / 4 + k * 45
												+ 55);
							} else
								g.drawLine(x + 50, (int) this.getHeight() / 4
										+ 70 + 50 * j, x + 205,
										(int) this.getHeight() / 4 + k * 45);
						}
					}
					if (j > 3 && j != Network.network[i].length - 2
							&& j != Network.network[i].length - 1) {
						continue;
					}
					if (j == Network.network[i].length - 1) {
						g.setColor(Color.RED);
						g.fillOval(x, this.getHeight() / 4 + 50, 50, 50);
						g.setColor(Color.BLACK);
						g.drawString(
								Double.toString(Network.network[i][j].output),
								x + 15, this.getHeight() / 4 + 50 + 25);
						d = 0;
					}

				} else if (i != 0 && i != Network.network.length - 1) {

					if (i + 1 != Network.network.length - 1 && j < 6) {
						if (j == 5) {
							for (int k = 0; k < 6; k++) {
								if (k == 5) {
									g.drawLine(x + 45, (int) this.getHeight()
											/ 4 + j * 50 + 50, x + 205,
											(int) this.getHeight() / 4 + k * 50
													+ 50);
								} else
									g.drawLine(x + 45, (int) this.getHeight()
											/ 4 + j * 50 + 50, x + 205,
											(int) this.getHeight() / 4 + k * 50);
							}
						} else {
							for (int k = 0; k < 6; k++) {
								if (k == 5) {
									g.drawLine(x + 45, (int) this.getHeight()
											/ 4 + j * 50, x + 205,
											(int) this.getHeight() / 4 + k * 50
													+ 50);
								} else
									g.drawLine(x + 45, (int) this.getHeight()
											/ 4 + j * 50, x + 205,
											(int) this.getHeight() / 4 + k * 50);
							}
						}

					}
					if ((Network.network[i].length - 1) < 6 || j < 5) {
						g.setColor(Color.BLACK);
						g.fillOval(x, this.getHeight() / 4 - 30 + d, 50, 50);
						d += 50;
						continue;
					} else if (j == 5) {
						g.setColor(Color.BLACK);
						g.fillOval(x + 20, this.getHeight() / 4 + 230, 10, 10);
						g.fillOval(x + 20, this.getHeight() / 4 + 230 + 10, 10,
								10);
						g.fillOval(x + 20, this.getHeight() / 4 + 230 + 20, 10,
								10);
					}
					if (j > 5 && j != Network.network[i].length - 2
							&& j != Network.network[i].length - 1) {
						continue;
					}
					if (j == Network.network[i].length - 1) {
						g.setColor(Color.BLACK);
						g.fillOval(x, this.getHeight() / 4 + 270, 50, 50);
						d = 0;
					}
				} else {
					g.setColor(Color.RED);
					g.fillOval(x, this.getHeight() / 4 + 100, 50, 50);
					g.setColor(Color.BLACK);
					g.drawString(Double.toString(Network.network[i][j].output),
							x, this.getHeight() / 4 + 100);
					g.drawLine(x - 155, (int) this.getHeight() / 4, x,
							this.getHeight() / 4 + 125);
					g.drawLine(x - 155, (int) this.getHeight() / 4 + 45, x,
							this.getHeight() / 4 + 125);
					g.drawLine(x - 155, (int) this.getHeight() / 4 + 90, x,
							this.getHeight() / 4 + 125);
					g.drawLine(x - 155, (int) this.getHeight() / 4 + 135, x,
							this.getHeight() / 4 + 125);
					g.drawLine(x - 155, (int) this.getHeight() / 4 + 180, x,
							this.getHeight() / 4 + 125);
					g.drawLine(x - 155, (int) this.getHeight() / 4 + 280, x,
							this.getHeight() / 4 + 125);

				}
			}
			x += 200;
		}
	}

	@Override
	public void dragEnter(DropTargetDragEvent event) {
		Transferable transferable = event.getTransferable();

        // Get the data formats of the dropped item
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        // Loop through the flavors
        for (DataFlavor flavor : flavors) {

            try {

                // If the drop items are files
                if (flavor.isFlavorJavaFileListType()) {

                    // Get all of the dropped files
                    @SuppressWarnings("unchecked")
					List<File> files = (List<File>) transferable.getTransferData(flavor);

                    // Loop them through
                    for (File file : files) {

                        // Print out the file path
                        System.out.println("File path is '" + file.getPath() + "'.");

                    }

                }

            } catch (Exception e) {

                // Print out the error stack
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
