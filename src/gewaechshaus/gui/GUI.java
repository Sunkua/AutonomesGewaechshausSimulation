package gewaechshaus.gui;


import javax.swing.*;

import gewaechshaus.logic.Pflanzenverwaltung;

import java.awt.*;

enum GuiState { idle, initDone, run}

public class GUI  extends JFrame {
	
	private String title = "SE2 Gewächshaus";	// The application title
	
	private JPanel mainPanel;				// Everything we draw will be put into this panel
	private GuiState guistate = GuiState.idle;
	
	private GuiGewaechshaus guiGewaechshaus;
	private GuiEigenschaften guiEigenschaften;
	private GuiBedinterminal guiBedinterminal;
	
	private Pflanzenverwaltung pflanzenverwaltung;


	public GUI(Pflanzenverwaltung p) {
		// TODO Auto-generated constructor stub
		
		pflanzenverwaltung = p;
		
        setTitle(title);//size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	    //height of the task bar
	    Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
	    int taskBarSize = scnMax.bottom;
	    
	    Dimension WindowSize = new Dimension();
	    WindowSize.setSize(screenSize.getWidth(), screenSize.getHeight() - taskBarSize);
        setSize(WindowSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // erstelle MainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);
        getContentPane().add(mainPanel);
        
        // füge Gewächhaus hinzu
        guiGewaechshaus = new GuiGewaechshaus("Gewächshaus", pflanzenverwaltung);
        guiGewaechshaus.setPreferredSize(new Dimension( getWidth() * 2/3, getHeight()));
        mainPanel.add(guiGewaechshaus, BorderLayout.LINE_START);
        
        // füqe ein temp panel hinzu
        JPanel RightPanel = new JPanel();
        RightPanel.setBackground(Color.DARK_GRAY);
        RightPanel.setPreferredSize(new Dimension( getWidth() * 1/3, getHeight()));
        mainPanel.add(RightPanel, BorderLayout.LINE_END);
        RightPanel.setLayout( new GridLayout(2,1));

        // füqe Bedinterminal hinzu      	
        guiBedinterminal = new GuiBedinterminal("Bedinterminal",pflanzenverwaltung);
        guiBedinterminal.setPreferredSize(new Dimension( getWidth() * 1/3, getHeight()*1/2));
        RightPanel.add(guiBedinterminal);

        // füqe Eigenschaftenfenster hinzu  
        guiEigenschaften = new GuiEigenschaften("Eigenschaften");
        guiEigenschaften.setPreferredSize(new Dimension( getWidth() * 1/3, getHeight()*1/2));
        RightPanel.add(guiEigenschaften);


        
        guiGewaechshaus.init();
        guiBedinterminal.init();   
        setVisible(true);
        
        guistate = GuiState.initDone;        
                
	}

}
