package gewaeshaus.logic.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import gewaeshaus.logic.Gitter;
import gewaeshaus.logic.RelativePosition;

public class Wegfindung {

	@Test
	public void test() throws SecurityException, IOException {
		Gitter g = new Gitter(10f,10f, 4,4);
		RelativePosition von = new RelativePosition();
		RelativePosition zu = new RelativePosition();
		von.setReihenID(0);
		von.setSpaltenID(0);
		zu.setReihenID(2);
		zu.setSpaltenID(2);
				
		ArrayList<RelativePosition> erwartet = new ArrayList<RelativePosition>();
		RelativePosition p1 = new RelativePosition();
		p1.setReihenID(1);
		p1.setSpaltenID(0);
		erwartet.add(zu);
		erwartet.add(p1);
		
		
		
		ArrayList<RelativePosition> pList = g.kuerzesterWegNach(von, zu);
		if(erwartet.equals(pList)) {
			System.out.println("erfolg");
		}
		assertEquals(erwartet, pList);
		
		
		
		
	}

}
