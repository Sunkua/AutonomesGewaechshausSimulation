package gewaechshaus.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AkkuLadenIntegrationsTest {

    AkkuLaden akkuLaden;
    Roboter roboter;
    Ladestation ladestation;
    Roboterleitsystem roboterleitsystem;
    Pflanzenverwaltung pverwaltung;
    Uhr uhr;

    @Before
    public void init() throws KeinWegGefundenException {
        uhr = new Uhr(100);
        Gitter g = new Gitter(12, 12, 12, 12);
        //Einzelpflanze ep = new Einzelpflanze(PflanzenArt.eGurke, new Position(2, 2), 0.5, PflanzenStatus.eReif);
        ladestation = new Ladestation(new Position(11, 11));
        roboterleitsystem = new Roboterleitsystem(g, uhr);
        roboterleitsystem.ladestationHinzufuegen(ladestation);
        pverwaltung = new Pflanzenverwaltung(g);
        roboterleitsystem.roboterHinzufuegen(pverwaltung);
        roboter = roboterleitsystem.getRoboter().get(0);
        // Simuliere aufgeladene Pflanze
        akkuLaden = new AkkuLaden(roboterleitsystem, roboterleitsystem.getFreieLadestation());
    }


    @Test
    public void ausfuehren_in_undefinierter_Anzahl_Schritte() {
        roboter.getAkku().setLadestand(80f);
        uhr.addObserver(akkuLaden);
        uhr.addObserver(roboterleitsystem);
        akkuLaden.setRoboter(roboter);
        akkuLaden.ausfuehren(roboter);
        while (!akkuLaden.getStatus().equals(UnterauftragsStatus.beendet)) {
            assertEquals(akkuLaden.getStatus(), UnterauftragsStatus.ausfuehrend);
            uhr.tick();
            //akkuLaden.ausfuehren(roboter);
        }
        assertTrue(akkuLaden.getStatus().equals(UnterauftragsStatus.beendet));
        assertTrue(roboter.getLadestand() > 90);
    }
}
