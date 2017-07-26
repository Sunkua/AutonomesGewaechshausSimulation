package gewaechshaus.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AbladenIntegrationsTest {
    Abladen abladen;
    Roboter roboter;
    Abladestation abladestation;
    Roboterleitsystem roboterleitsystem;
    Pflanzenverwaltung pverwaltung;

    @Before
    public void init() throws KeinWegGefundenException {
        Uhr uhr = new Uhr(100);
        Gitter g = new Gitter(12, 12, 12, 12);
        Einzelpflanze ep = new Einzelpflanze(PflanzenArt.eGurke, new Position(2, 2), 0.5, PflanzenStatus.eReif);
        abladestation = new Abladestation(new Position(11, 11));
        roboterleitsystem = new Roboterleitsystem(g, uhr);
        roboterleitsystem.abladestationHinzufuegen(abladestation);
        pverwaltung = new Pflanzenverwaltung(g);
        roboterleitsystem.roboterHinzufuegen(pverwaltung);
        roboter = roboterleitsystem.getRoboter().get(0);
        // Simuliere aufgeladene Pflanze
        roboter.ladePflanzeAuf(ep);
        abladen = new Abladen(roboterleitsystem, roboterleitsystem.getFreieAbladestation());
    }


    @Test
    public void ausfuehren_in_3_Schritten() {
        roboter.setPosition(new Position(10, 11));
        abladen.ausfuehren(roboter);
        for (int i = 0; i < 2; i++) {
            assertEquals(abladen.getStatus(), UnterauftragsStatus.ausfuehrend);
            abladen.ausfuehren(roboter);
        }
        assertTrue(abladen.getStatus().equals(UnterauftragsStatus.beendet));
        assertTrue(roboter.getFuellstand() == 0);
    }

    @Test
    public void ausfuehren_in_undefinierter_Anzahl_Schritte() {
        abladen.ausfuehren(roboter);
        assertTrue(roboter.getFuellstand() > 0);
        while (!abladen.getStatus().equals(UnterauftragsStatus.beendet)) {
            assertEquals(abladen.getStatus(), UnterauftragsStatus.ausfuehrend);
            abladen.ausfuehren(roboter);
        }
        assertTrue(abladen.getStatus().equals(UnterauftragsStatus.beendet));
        assertTrue(roboter.getFuellstand() == 0);
    }



}