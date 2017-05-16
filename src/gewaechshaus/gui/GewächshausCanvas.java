package gewaechshaus.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;

import gewaechshaus.logic.Pflanzenverwaltung;
import gewaechshaus.logic.Position;

public class GewächshausCanvas extends Canvas {

	Pflanzenverwaltung pflanzenverwaltung;
	public GewächshausCanvas(Pflanzenverwaltung p) {
		// TODO Auto-generated constructor stub
		pflanzenverwaltung = p;
	}

	public GewächshausCanvas(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	void init(){
		Position Größe = pflanzenverwaltung.getMaxGröße();
		int GridSpalten = Größe.getSpaltenID() + Größe.getSpaltenID() % 5 + 2;
		int GridZeilen = Größe.getReihenID() + Größe.getReihenID() % 5 + 2; // toTo -> zeilen oder reihen -> entscheiden!
		if(GridSpalten <=2 || GridZeilen <=2)
			return;
		int dx = getWidth() / GridSpalten;
		int dy = getHeight() / GridZeilen;
		
		//Graphics g = getGraphics();
		//g.setColor(Color.black);
		//g.fillRect(50, 50, 50, 50);
		
		
	}

}
