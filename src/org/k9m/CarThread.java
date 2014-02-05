package org.k9m;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;


public class CarThread extends Thread {

	protected Graphics2D g2;
	private List<Segment> segments;
	protected GlassPane probe;

	public CarThread(String ID, Graphics2D g2, List<Segment> segments, GlassPane probe)
	{
		super();
		this.g2 = g2;
		this.segments = segments;
		this.probe = probe;
	}

	public void run()
	{	

		for (Segment segment : segments){

			double a[] = segment.getLane();
			double stepdist = 2; // max pixels per step
			double accbrkdist = 30; // max pixels per step
			double acceleration = 2;
			double brake = 1;

			double x1 =a[0];
			double y1 =a[1];			

			double xdiff = a[2] - a[0];
			double ydiff = a[3] - a[1];
			double dist = Math.sqrt( xdiff * xdiff + ydiff * ydiff );
			int steps = (int) ( ( dist - 1 ) / stepdist );


			if( steps > 0 )
			{


				xdiff /= steps;
				ydiff /= steps;
				int sleepInterval = 4;
				int sleepInterval_Acc = 2;
				if (steps > steps - accbrkdist)
					sleepInterval_Acc = (int)(accbrkdist/stepdist);

				while( --steps >= 0 )
				{
					x1 += xdiff;
					y1 += ydiff;

					probe.setIm((int)x1, (int)y1);
					probe.repaint();

					if (steps > steps - accbrkdist && sleepInterval_Acc > sleepInterval)
						try {

							Thread.sleep(sleepInterval_Acc -= brake);
							continue;

						} catch ( InterruptedException e ) {


						}

					if (steps > accbrkdist)
						try {

							Thread.sleep(sleepInterval);
							continue;

						} catch ( InterruptedException e ) {


						}


					try {
						Thread.sleep(sleepInterval += acceleration);

					} catch ( InterruptedException e ) {

						// do nothing
					}				
				}

			}
		}

	}



}
