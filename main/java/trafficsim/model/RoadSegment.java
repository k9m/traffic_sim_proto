package trafficsim.model;


public class RoadSegment {

	private static final int NR_POINTS = 4;
	
	private double[]a = new double[NR_POINTS];
	private double[]b = new double[NR_POINTS];	
	private double[]c = new double[NR_POINTS];	
	private double[]d = new double[NR_POINTS];
	
	private int left = 0;

	private int offsetPixels;

	public RoadSegment(int x1, int x2, int y1, int y2, int offsetPixels, int left)
	{		
		this.offsetPixels = offsetPixels;

		double lane = 0.2;
		if (left == 0)
		{
			lane = 1.6;
		}
		
		
		this.a = transform(x1,x2,y1,y2, 0);
		this.b = transform(x1,x2,y1,y2, 1);
		this.c = transform(x1,x2,y1,y2, 2);		
		this.d = transform(x1,x2,y1,y2, lane);
		
		if (left == 0)
		{
			double[] tmp = a;
			a = c;
			c = tmp;
		}
		
		this.left = left;
	}

	


	private synchronized double[] transform(double x1, double x2, double y1, double y2, double i)
	{		
		double[]tr = new double[4];

		double l = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));	

		tr[0] = x1 + i * offsetPixels * (y2-y1) / l;
		tr[2] = x2 + i * offsetPixels * (y2-y1) / l;
		tr[1] = y1 + i * offsetPixels * (x1-x2) / l;
		tr[3] = y2 + i * offsetPixels * (x1-x2) / l;

		return tr;		
	}	

	
	
	public synchronized double[] getRight()
	{return a;}

	public synchronized double[] getCentral()
	{return b;}
	
	public synchronized double[] getLeft()
	{return c;}
	
	public synchronized double[] getLane()
	{return d;}

	public synchronized int isLeft() {
		return left;
	}
}
