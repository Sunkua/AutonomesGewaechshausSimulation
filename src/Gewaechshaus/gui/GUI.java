package Gewaechshaus.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;


public class GUI  extends JFrame {
	
	private String title = "SE2 Gewächshaus";	// The application title
	
	private JPanel mainPanel;				// Everything we draw will be put into this panel
	
	private GuiGewaechshaus guiGewaechshaus;
	private GuiEigenschaften guiEigenschaften;
	private GuiBedinterminal guiBedinterminal;

	public GUI() {
		// TODO Auto-generated constructor stub
        setTitle(title);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setToolTipText("The main panel container");
        getContentPane().add(mainPanel);
        
        guiGewaechshaus = new GuiGewaechshaus();
        guiEigenschaften = new GuiEigenschaften();
        guiBedinterminal = new GuiBedinterminal();

        guiGewaechshaus.setBounds(0, 0, screenSize.width * 2/3, screenSize.height);
        guiGewaechshaus.setToolTipText("Gewächshaus Demo");
        mainPanel.add(guiGewaechshaus);

        guiBedinterminal.setBounds(screenSize.width * 2/3, 0, screenSize.width*1/3,   screenSize.height*1/2);
        guiBedinterminal.setToolTipText("Bedinterminal Demo");
        mainPanel.add(guiBedinterminal);
        
        guiEigenschaften.setBounds(screenSize.width * 2/3, screenSize.height*1/2, screenSize.width*1/3,   screenSize.height*1/2);
        guiEigenschaften.setToolTipText("Eigenschaften Demo");
        mainPanel.add(guiEigenschaften);
        
        setVisible(true);
	}

}
