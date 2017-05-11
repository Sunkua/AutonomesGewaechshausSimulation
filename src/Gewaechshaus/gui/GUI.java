package Gewaechshaus.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;

enum GuiState { idle, initDone, run};

public class GUI  extends JFrame {
	
	private String title = "SE2 Gewächshaus";	// The application title
	
	private JPanel mainPanel;				// Everything we draw will be put into this panel
	private GuiState guistate = GuiState.idle;
	
	private GuiGewaechshaus guiGewaechshaus;
	private GuiEigenschaften guiEigenschaften;
	private GuiBedinterminal guiBedinterminal;


	public GUI() {
		// TODO Auto-generated constructor stub
        setTitle(title);//size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	    //height of the task bar
	    Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
	    int taskBarSize = scnMax.bottom;
	    
	    Dimension WindowSize = new Dimension();
	    WindowSize.setSize(screenSize.getWidth(), screenSize.getHeight() - taskBarSize);
        setSize(WindowSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.setToolTipText("The main panel container");
        getContentPane().add(mainPanel);
        

        guiGewaechshaus = new GuiGewaechshaus();
        guiEigenschaften = new GuiEigenschaften();
        guiBedinterminal = new GuiBedinterminal();

        guiGewaechshaus.setBounds(0, 0, getWidth() * 2/3, getHeight() -40); // todo 40 ist die höhe des Headers
        guiGewaechshaus.setToolTipText("Gewächshaus Demo");
        mainPanel.add(guiGewaechshaus);

        guiBedinterminal.setBounds(getWidth() * 2/3, 0, getWidth()*1/3, getHeight()*1/2);
        guiBedinterminal.setToolTipText("Bedinterminal Demo");
        mainPanel.add(guiBedinterminal);

        guiEigenschaften.setBounds(getWidth() * 2/3, getHeight()*1/2, getWidth()*1/3, getHeight()*1/2 - 25); // todo 25 .. angenommeen
        guiEigenschaften.setToolTipText("Eigenschaften Demo");
        mainPanel.add(guiEigenschaften);

        guiGewaechshaus.init();
        guiBedinterminal.init();        
        
        guistate = GuiState.initDone;        
                
        setVisible(true);
	}

}
