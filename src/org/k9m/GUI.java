package org.k9m;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;


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
