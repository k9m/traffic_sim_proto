package org.k9m;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;


/**
 * Class for representing a 3D Barchart at user's request. 
 * The colours of the Bars are randomly generated whenever the object 
 * is created and the Chart can dynamically expand horizontally along with
 * the frame however the height of the bar is always 2 units taller than the 
 * tallest Bar itself.
 * @author 1000999m
 *
 */
public class RoadNetwork extends JComponent{
	
	private List<Segment> segments;

	/**
	 * Default Constructor for Barchart sets up the arrays and
	 * and creates random colors for BarChart.
	 */
	public RoadNetwork(){
		segments = new LinkedList<Segment>();
	}

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		drawline(g2);

	}	

	private void drawline(Graphics2D g2)
	{

		Segment prevSegment = null;

		for (Segment segment : segments){		

			g2.setStroke(new BasicStroke(2));			
			double a[] = segment.getRight();
			Line2D aLine = new Line2D.Double(a[0],a[1],a[2],a[3]);
			g2.draw(aLine);

			double c[] = segment.getLeft();
			Line2D cLine = new Line2D.Double(c[0],c[1],c[2],c[3]);
			g2.draw(cLine);			

			if (prevSegment != null){				
				double[] right = prevSegment.getLeft();
				double[] left = prevSegment.getRight();
				double[] rright = segment.getLeft();
				
				int x0 = (int)left[2];
				int y0 = (int)left[3];

				int x1 = (int)right[2];
				int y1 = (int)right[3];

				int x2 = (int)rright[2];
				int y2 = (int)rright[3];

				//r=sqrt((x1-x0)(x1-x0) + (y1-y0)(y1-y0))
				int r = 50;
				int x = x1-r;
				int y = y1-r;
				int width = 2*r;
				int height = 2*r;
				int startAngle = (int) (180/Math.PI*Math.atan2(y1-y0, x1-x0));
				int endAngle = (int) (180/Math.PI*Math.atan2(y2-y0, x2-x0));

				if (segment.isLeft() == 0){
					g2.drawArc(x1 - r, y1 - r, width, height, 0, 360);
				}
				else{
					g2.drawArc(x0 - r, y0 - r, width, height, 0, 360);
				}

			}

			prevSegment = segment;

			float[] dashPattern = { 30, 30, 30, 30 };
			g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10,dashPattern, 0));
			double b[] = segment.getCentral();
			Line2D bLine = new Line2D.Double(b[0],b[1],b[2],b[3]);
			g2.draw(bLine);
		}
	}



	public void addSegment(Segment segment){			
		segments.add(segment);
	}	

	public synchronized List<Segment> getSegment(){
		return segments;
	}
}
