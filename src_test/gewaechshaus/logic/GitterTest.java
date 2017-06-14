package gewaechshaus.logic;

import gewaechshaus.logic.Gitter;
import gewaechshaus.logic.Position;
import gewaechshaus.logic.Positionsbelegung;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

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
public void kuerzesterPfadLeerTest() throws Exception {
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
    public void getFreieNachbarn() {

        // Alle Felder frei
        Position testPosition = new Position(2, 2);
        gitter.setPosition(Positionsbelegung.pflanze, testPosition);
        gitter.setPosition(Positionsbelegung.frei, new Position(2, 3));
        gitter.setPosition(Positionsbelegung.frei, new Position(2, 1));
        gitter.setPosition(Positionsbelegung.frei, new Position(1, 2));
        gitter.setPosition(Positionsbelegung.frei, new Position(3, 2));
        Collection<Position> freieNachbarn = gitter.getFreieNachbarFelder(testPosition);
        for (Position pos : freieNachbarn) {
            assertTrue(gitter.getPositionsbelegung(pos).equals(Positionsbelegung.frei));
        }

        // 1 Feld frei
        gitter.setPosition(Positionsbelegung.pflanze, testPosition);
        gitter.setPosition(Positionsbelegung.pflanze, new Position(2, 3));
        gitter.setPosition(Positionsbelegung.pflanze, new Position(2, 1));
        gitter.setPosition(Positionsbelegung.pflanze, new Position(1, 2));
        gitter.setPosition(Positionsbelegung.frei, new Position(3, 2));
        freieNachbarn = gitter.getFreieNachbarFelder(testPosition);
        for (Position pos : freieNachbarn) {
            assertTrue(gitter.getPositionsbelegung(pos).equals(Positionsbelegung.frei));
        }

        // kein Feld frei
        gitter.setPosition(Positionsbelegung.pflanze, testPosition);
        gitter.setPosition(Positionsbelegung.pflanze, new Position(2, 3));
        gitter.setPosition(Positionsbelegung.pflanze, new Position(2, 1));
        gitter.setPosition(Positionsbelegung.pflanze, new Position(1, 2));
        gitter.setPosition(Positionsbelegung.pflanze, new Position(3, 2));
        freieNachbarn = gitter.getFreieNachbarFelder(testPosition);
        assertTrue(freieNachbarn.size() == 0);
    }

    @Test
    public void kuerzesterPfadHindernis() throws Exception {
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
