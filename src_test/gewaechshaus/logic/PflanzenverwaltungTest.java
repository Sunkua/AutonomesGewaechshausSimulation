package gewaechshaus.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;


public class PflanzenverwaltungTest {
    Pflanzenverwaltung pv;

    @Before
    public void setUp() throws Exception {
        pv = new Pflanzenverwaltung();
        pv.setBreite(10);
        pv.setHoehe(10);
    }

    @After
    public void tearDown() throws Exception {
        // Entferne alle Pflanzen

    }

    @Test
    public void pflanzeHinzufuegen() throws Exception {
        Einzelpflanze p = new Einzelpflanze(PflanzenArt.eGurke, new Position(4,4), 10f,PflanzenStatus.eReif,new Date());
        pv.pflanzeHinzufuegen(p);
    }

    @Test
    public void pflanzeEntfernen() throws Exception {
    }

    @Test
    public void holePflanzenVonArt() throws Exception {
    }

    @Test
    public void holePflanzenVonStatus() throws Exception {
    }

    @Test
    public void getPflanzenMapVonTyp() throws Exception {
    }

    @Test
    public void getPflanzenMapVonStatus() throws Exception {
    }

    @Test
    public void getAllePflanzen() throws Exception {
    }

    @Test
    public void holePflanzeVonPosition() throws Exception {
    }

}