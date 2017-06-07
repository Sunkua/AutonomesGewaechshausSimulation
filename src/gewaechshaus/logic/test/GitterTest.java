package gewaechshaus.logic.test;

import gewaechshaus.logic.Gitter;
import gewaechshaus.logic.Position;
import gewaechshaus.logic.Positionsbelegung;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;


public class GitterTest {
    Gitter gitter;

    @Before
    public void init() {
        gitter = new Gitter(10f,10f,10,10);
    }

    @Test
    public void checkPositionOben() {
        Position res =gitter.getPositionOben(new Position(2,2));
        assertTrue(res.equals(new Position(2,1)));
    }

    @Test (expected = Exception.class)
    public void checkPositionObenOutOfBounds() {
        Position res = gitter.getPositionOben(new Position(2,0));
    }

    @Test
    public void testToKarthesisch() {
        Position rel = new Position(2.4f, 3.6f);
        gitter.toKarthesisch(rel);
        assertTrue(rel.equals(new Position(2,4)));
    }
@Test
    public void kuerzesterPfadLeerTest()  {
        Position von = new Position();
        Position zu = new Position();
        von.setReihenID(0);
        von.setSpaltenID(0);
        zu.setReihenID(4);
        zu.setSpaltenID(4);
        int erwarteteSchrittzahl = 8;
        ArrayList<Position> pList = gitter.kuerzesterWegNach(von, zu);
        assertEquals(pList.size() - 1, erwarteteSchrittzahl);
    }

    @Test
    public void kuerzesterPfadHindernis() {
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
        gitter.setPosition(Positionsbelegung.ladestation, hindernis1);
        gitter.setPosition(Positionsbelegung.pflanze, hindernis2);
        int erwarteteSchrittzahl = 6;
        ArrayList<Position> pList = gitter.kuerzesterWegNach(von, zu);
        assertEquals(pList.size() - 1, erwarteteSchrittzahl);
    }

}
