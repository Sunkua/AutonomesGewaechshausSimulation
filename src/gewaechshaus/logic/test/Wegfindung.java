package gewaechshaus.logic.test;

import gewaechshaus.logic.Gitter;
import gewaechshaus.logic.Position;
import gewaechshaus.logic.Positionsbelegung;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class Wegfindung {

	@Test
	/**
	 * Alle Positionen frei 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public void kuerzesterPfadLeerTest() throws SecurityException, IOException {
		Gitter g = new Gitter(10f,10f, 5,5);
		Position von = new Position();
		Position zu = new Position();
		von.setReihenID(0);
		von.setSpaltenID(0);
		zu.setReihenID(4);
		zu.setSpaltenID(4);		
		int erwarteteSchrittzahl = 8;	
		ArrayList<Position> pList = g.kuerzesterWegNach(von, zu);
		assertEquals(pList.size()-1, erwarteteSchrittzahl);	
	}
	
	@Test
	public void kuerzesterPfadHindernis() throws SecurityException, IOException {
		Gitter g = new Gitter(10f,10f, 5,5);
		Position von = new Position();
		Position zu = new Position();
		Position hindernis1 = new Position();
		Position hindernis2 = new Position();
		hindernis1.setReihenID(1);
		hindernis1.setSpaltenID(1);
		hindernis2.setReihenID(0);
		hindernis2.setSpaltenID(1);
		von.setReihenID(0);
		von.setSpaltenID(0);
		zu.setReihenID(0);
		zu.setSpaltenID(2);		
		g.setPosition(Positionsbelegung.ladestation, hindernis1);
		g.setPosition(Positionsbelegung.pflanze, hindernis2);
		int erwarteteSchrittzahl = 6;	
		ArrayList<Position> pList = g.kuerzesterWegNach(von, zu);
		assertEquals(pList.size()-1, erwarteteSchrittzahl);	
	}
	
}
