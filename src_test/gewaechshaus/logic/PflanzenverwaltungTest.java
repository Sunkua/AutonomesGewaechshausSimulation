package gewaechshaus.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PflanzenverwaltungTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    Gitter gitterMock;
    private Pflanzenverwaltung pv;
    private Einzelpflanze ep;
    private HashMap<Position, Einzelpflanze> map;

    /**
     * Kommentar 29.06.2017: Einzelpflanze benötigt kein Mocking.
     * 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        Gitter gitter = new Gitter(10f, 10f, 10, 10);
        pv = new Pflanzenverwaltung(gitter);
        ep = new Einzelpflanze(PflanzenArt.eGurke,
                new Position(4, 4),
                10f,
                PflanzenStatus.eFaul);
        Einzelpflanze epGurke = new Einzelpflanze(PflanzenArt.eGurke,
                new Position(4, 5),
                12f,
                PflanzenStatus.eReif);
        Einzelpflanze epTomate = new Einzelpflanze(PflanzenArt.eTomate,
                new Position(4, 6),
                12f,
                PflanzenStatus.eReif);
        map = new HashMap<>();
        map.put(ep.getPosition(), ep);
        map.put(epGurke.getPosition(), epGurke);
        map.put(epTomate.getPosition(), epTomate);
/*
        pv.pflanzeHinzufuegen(ep);
        pv.pflanzeHinzufuegen(epGurke);
        pv.pflanzeHinzufuegen(epTomate);*/
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
            assertTrue(map.containsValue(pflanze));
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
        assertEquals(ergebnis, map);
    }

    @Test
    public void getAllePflanzen_keine_Pflanzen_Im_System() {
    	Map<Position, Einzelpflanze> allePflanzen = pv.getAllePflanzen();
        for (Entry<Position, Einzelpflanze> pflanze : allePflanzen.entrySet()) {
            pv.pflanzeEntfernen(pflanze.getValue());
        }
        allePflanzen = pv.getAllePflanzen();
        
        assertEquals(allePflanzen.size(), 0);
    }

    @Test
    public void hole_Pflanze_von_vorhandener_Position() throws Exception {
        Einzelpflanze pflanze = pv.holePflanzeVonPosition(new Position(4,4));
        assertEquals(pflanze, ep);
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