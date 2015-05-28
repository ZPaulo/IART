package ui;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class NodeUI
{
	public String name;
	public ArrayList<Edge> Adj;

	public Vector pos;
	public Vector posTodraw;
	public Color color;
	public NodeUI(int x, int y)
	{
		pos= new Vector(x, y);
		color = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
	}
	
	
	public void Draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval((int)(posTodraw.getX()-20), (int)(posTodraw.getY()-20), 40, 40);
		g.setColor(Color.black); 
		g.drawString(name,(int)(posTodraw.getX()-20), (int)(posTodraw.getY()-20));
	}
	
	public void setPosToDraw(Vector v)
	{
		posTodraw = v;
	}
	
	public Vector getCentroid(ArrayList<NodeUI> ll)
	{
		double sx=0,sy=0;
		for(int i=0;i<ll.size();i++){
			 sx += ll.get(i).posTodraw.getX();
			 sy += ll.get(i).posTodraw.getY();
		}
		return new Vector(sx/ll.size(),sy/ll.size());
	}
}
