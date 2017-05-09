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
		Gitter g = new Gitter(10f,10f, 5,5);
		RelativePosition von = new RelativePosition();
		RelativePosition zu = new RelativePosition();
		von.setReihenID(0);
		von.setSpaltenID(0);
		zu.setReihenID(4);
		zu.setSpaltenID(4);

		
		
		int erwarteteSchrittzahl = 8;
		
		
		
		ArrayList<RelativePosition> pList = g.kuerzesterWegNach(von, zu);
		assertEquals(pList.size()-1, erwarteteSchrittzahl);

		
		
		
		
	}

}
