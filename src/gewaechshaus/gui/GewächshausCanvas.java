package gewaechshaus.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import com.sun.xml.internal.ws.api.Component;

import gewaechshaus.logic.*;

public class GewächshausCanvas extends Canvas {
	
	private int delta = 50;

	private Pflanzenverwaltung pflanzenverwaltung;
	private Roboterleitsystem roboterleitsystem;
	public GewächshausCanvas(Pflanzenverwaltung p, Roboterleitsystem rLeitsystem) {
		// TODO Auto-generated constructor stub
		pflanzenverwaltung = p;
		roboterleitsystem = rLeitsystem;

	}

	public GewächshausCanvas(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		Position größe = pflanzenverwaltung.getMaxGröße();

		for(int i = 0; i < größe.getSpaltenID(); i++) {
		    for(int j = 0; j < größe.getReihenID(); j++) {
		        g.setColor(Color.black);
		        g.drawRect(i*delta, j*delta, delta, delta);
            }
        }

	    Map<Position, Einzelpflanze> pflanzen = pflanzenverwaltung.getAllePflanzen();
	    for(Map.Entry<Position, Einzelpflanze> pflanze : pflanzen.entrySet()) {
            g.setColor(Color.green);
            g.fillOval(pflanze.getKey().getSpaltenID() * delta, pflanze.getKey().getReihenID()*delta, delta, delta);
        }

		// Draw Roboter
        Set<Position> roboterPos = roboterleitsystem.getRoboterPositionen();
		for(Position p : roboterPos) {
		    g.setColor(Color.blue);
		    g.fillRect(p.getSpaltenID() * delta, p.getReihenID()*delta, delta, delta);
        }
	}



}
