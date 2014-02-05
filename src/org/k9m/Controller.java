package org.k9m;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;


public class Controller{
	
	private RoadNetwork roadNetwork;
	GlassPane glass;
	private List<Segment> segments;
	
	Controller(RoadNetwork roadNetwork, GlassPane glass)
	{
		this.roadNetwork = roadNetwork;
		this.glass = glass;
		glass.setController(this);
		segments = new LinkedList<Segment>();
	}
	
	public void note()
	{
		System.err.println("NOTED");
	}
	
	public void addSegment(Segment segment)
	{			
		segments.add(segment);
		roadNetwork.addSegment(segment);
		roadNetwork.repaint();
	}
	
	public List<Segment> getSegment()
	{
		return segments;
	}

}
