package gewaechshaus.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PflanzenverwaltungTest {


    private Pflanzenverwaltung pv;
    private Einzelpflanze ep;
    private HashMap<Position, Einzelpflanze> map;
    Gitter gitter;

    /**
     * Kommentar 29.06.2017: Einzelpflanze benötigt kein Mocking.
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        gitter = mock(Gitter.class);
        pv = new Pflanzenverwaltung(gitter);
        when(gitter.getBreite()).thenReturn(10);
        when(gitter.getHoehe()).thenReturn(10);
        when(gitter.naechsteFreiePflanzenPositionSuchen()).thenReturn(new Position(1, 1));

        pv.pflanzeHinzufuegen(PflanzenArt.eGurke);
    }

    @After
    public void tearDown() throws Exception {
        // Entferne alle Pflanzen
    	pv.löscheAllePflanzen();
    }

    /*
        @Test
        public void pflanzeHinzufuegen() throws Exception {
            pv.pflanzeHinzufuegen(ep);
            assertEquals(pv.holePflanzeVonPosition(ep.getPosition()), ep);
        }

        @Test(expected = Exception.class)
        public void pflanze_an_ungültiger_position_hinzufuegen() throws Exception {
            ep.setPosition(new Position(15, 15));
            pv.pflanzeHinzufuegen(ep);
        }

        @Test(expected = Exception.class)
        public void pflanze_entfernen_und_Pflanze_von_selber_Position_holen() throws Exception {
            pv.pflanzeHinzufuegen(ep);
            pv.pflanzeEntfernen(ep.getPosition());
            pv.holePflanzeVonPosition(ep.getPosition());
        }
    */

    @Test
    public void holePflanzenVonTyp() throws Exception {
        Map<Position, Einzelpflanze> ergebnis = pv.getPflanzenMapVonTyp(PflanzenArt.eGurke);
        for (Einzelpflanze pflanze : ergebnis.values()) {
            assertTrue(pflanze.getArt().equals(PflanzenArt.eGurke));
        }
    }

    @Test
    public void holePflanzenVonStatus() throws Exception {
        Map<Position, Einzelpflanze> ergebnis = pv.getPflanzenMapVonStatus(PflanzenStatus.eReif);
        for (Einzelpflanze pflanze : ergebnis.values()) {
            assertTrue(map.containsValue(pflanze));
            assertTrue(pflanze.getPflanzenstatus().equals(PflanzenStatus.eReif));
        }
    }


    @Test
    public void getAllePflanzen() throws Exception {
        Map<Position, Einzelpflanze> ergebnis = pv.getAllePflanzen();
        for (Einzelpflanze p : ergebnis.values()) {
            assertTrue(p.getArt().equals(PflanzenArt.eGurke));
        }
    }

    @Test
    public void hole_Pflanze_von_vorhandener_Position() throws Exception {
        Einzelpflanze pflanze = pv.holePflanzeVonPosition(new Position(1, 1));
        assertEquals(PflanzenArt.eGurke, pflanze.getArt());
    }

    @Test (expected = Exception.class)
    public void hole_Pflanze_von_Position_ohne_Pflanze() throws Exception {
    	pv.holePflanzeVonPosition(new Position(0,0));
    }

    @Test (expected = Exception.class)
    public void hole_Pflanze_von_Position_ausserhalb_der_Pflanzenverwaltung() throws Exception {
        pv.holePflanzeVonPosition(new Position(15,15));
    }

}