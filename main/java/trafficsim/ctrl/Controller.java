package trafficsim.ctrl;

import trafficsim.gui.RoadNetwork;
import trafficsim.gui.GlassPane;
import trafficsim.model.RoadSegment;

import java.util.LinkedList;
import java.util.List;


public class Controller{
	
	private RoadNetwork roadNetwork;
	GlassPane glass;
	private List<RoadSegment> segments;
	
	public Controller(RoadNetwork roadNetwork, GlassPane glass)
	{
		this.roadNetwork = roadNetwork;
		this.glass = glass;
		glass.setController(this);
		segments = new LinkedList<RoadSegment>();
	}
	
	public void note()
	{
		System.err.println("NOTED");
	}
	
	public void addSegment(RoadSegment segment)
	{			
		segments.add(segment);
		roadNetwork.addSegment(segment);
		roadNetwork.repaint();
	}
	
	public List<RoadSegment> getSegment()
	{
		return segments;
	}

}
