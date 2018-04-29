package trafficsim.gui;
import trafficsim.ctrl.Controller;

import javax.swing.*;
import java.awt.*;


public class GUI{
	
	JButton xxx;
	RoadNetwork base;
	Controller ctrl;
	
	public GUI(){	
	
	JFrame frame = new JFrame("GlassPaneDemo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    frame.setLocation(10,10);
    frame.setSize(1024, 768);
    frame.setTitle("FancyGUI");
    frame.setResizable(false);
    frame.setVisible(true);
    frame.createBufferStrategy(2);
		
	base = new RoadNetwork();
   
	frame.setLayout(new BorderLayout());
	frame.add(base, BorderLayout.NORTH);
    frame.add(base);
    
	GlassPane glassPane = new GlassPane(base);
	frame.setGlassPane(glassPane);
	glassPane.setVisible(true);
	
	ctrl = new Controller(base, glassPane);
}

	

}
