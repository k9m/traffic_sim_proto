package trafficsim.gui;

import trafficsim.ctrl.CarThread;
import trafficsim.ctrl.Controller;
import trafficsim.model.RoadSegment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;


public class GlassPane extends JComponent implements MouseListener, MouseMotionListener{


	private static final int SIZE = 256;
    private int a = SIZE / 2;
    private int b = a;
    private int r = 4 * SIZE / 5;
    private int n;
	
	private int x1,x2,y1,y2;

	private static final int offsetPixels = 25;

	private List<Line2D> line_A;
	private List<Line2D> line_B;

	private Line2D line_A_Curr;
	private Line2D line_B_Curr;

	private boolean leftBend;
	private boolean rightBend;

	int k = 1;

	int l = -10;


	RoadNetwork base;

	private Controller ctrl;

	Ellipse2D leftEdge;
	Ellipse2D rightEdge;

	private int rightX,rightY,leftX,leftY;	

	private Image image;

	private int imX,imY;
	private boolean dragEnabled = true;

	public GlassPane()
	{			
		super();

		line_A = new LinkedList<Line2D>();
		line_B = new LinkedList<Line2D>();

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

	}

	public GlassPane(RoadNetwork base)
	{			
		this();
		this.base = base;	
	}



	public void setController(Controller ctrl)
	{
		this.ctrl = ctrl;
	}

	public void update(Graphics g)
	{
		paint(g);
	}

	public synchronized void setIm(int imX, int imY)
	{
		this.imX = imX;
		this.imY = imY;
	}

	public synchronized void paint(Graphics g)
	{		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		image = createImage(15, 15);		
		Graphics offG = image.getGraphics();
		offG.fillRect(0, 0, 15, 15);		

		a = getWidth() / 2;
        b = getHeight() / 2;
        int m = Math.min(a, b);
        r = 4 * m / 5;
        int r2 = Math.abs(m - r) / 2;        
        g2.setColor(Color.blue);
		g2.drawImage(image, imX, imY, null);		

		if (leftEdge != null && rightEdge != null){
			g2.setColor(Color.RED);
			g2.fill(leftEdge);
			g2.setColor(Color.YELLOW);
			g2.fill(rightEdge);
		}
		
		g2.setColor(Color.BLACK);

		drawline(g2, 0 * k *offsetPixels);	    	    
		drawline(g2, 2 * k * offsetPixels);		

		float[] dashPattern = { 30, 30, 30, 30 };
		g2.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10,dashPattern, 0));	    
		drawline(g2, 1 * k * offsetPixels);
		leftBend = false;
	}	

	private void drawline(Graphics2D g2, int offsetPixels)
	{			
		double l = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));	

		//This is the second line
		double x1p = x1 + offsetPixels * (y2-y1) / l;
		double x2p = x2 + offsetPixels * (y2-y1) / l;
		double y1p = y1 + offsetPixels * (x1-x2) / l;
		double y2p = y2 + offsetPixels * (x1-x2) / l;	

		//line_A_Curr = new Line2D.Double(x_start,y_start,x_end,y_end);
		line_B_Curr = new Line2D.Double( x1p,y1p,x2p,y2p);		

		//g2.draw(line_A_Curr);
		g2.draw(line_B_Curr);


	}

	private double[] transform(double x1, double x2, double y1, double y2, int i)
	{		
		double[]tr = new double[4];

		double l = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));	

		tr[0] = x1 + i * offsetPixels * (y2-y1) / l;
		tr[2] = x2 + i * offsetPixels * (y2-y1) / l;
		tr[1] = y1 + i * offsetPixels * (x1-x2) / l;
		tr[3] = y2 + i * offsetPixels * (x1-x2) / l;

		return tr;		
	}	

	@Override
	public void mouseDragged(MouseEvent arg0){

		
		if (dragEnabled){ 
			this.x2 = arg0.getX();
			this.y2 = arg0.getY();			
			this.repaint();			
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (rightEdge != null)
			if (rightEdge.contains(e.getX(), e.getY())){
				
			}
		
		if (leftEdge != null)
			if (leftEdge.contains(e.getX(), e.getY())){
				
			}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.err.println("Click");

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		if (leftEdge != null)
			if (leftEdge.contains(arg0.getX(), arg0.getY())){				
				this.x1 = leftX;
				this.y1 =leftY;
				k = -1;
				l = 0;
				
				dragEnabled = true;
			}
		if (rightEdge != null)
			if (rightEdge.contains(arg0.getX(), arg0.getY())){				
				this.x1 = rightX;
				this.y1 =rightY;
				k = 1;
				l = 1;
				
				dragEnabled = true;
			}		

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {		

		if (dragEnabled)
		{
			RoadSegment segment = new RoadSegment(x1,x2,y1,y2, k * offsetPixels, l);
			ctrl.addSegment(segment);
			double[] tl = segment.getLeft();
			double[] tr = segment.getRight();
			leftX = (int)tl[2];
			leftY = (int)tl[3];	
			
			rightX = (int)tr[2];
			rightY = (int)tr[3];

			leftEdge  = new Ellipse2D.Double(leftX, leftY, 10, 10);
			rightEdge  = new Ellipse2D.Double(rightX, rightY, 10, 10);

			dragEnabled = false;
		}


		CarThread thr = new CarThread("AAA", (Graphics2D)this.getGraphics(), base.getSegment(), this);
		thr.start();


	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}





}
