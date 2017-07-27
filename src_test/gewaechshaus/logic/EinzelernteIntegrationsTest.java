package gewaechshaus.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EinzelernteIntegrationsTest {
	Einzelernte einzelernte;
	Roboter roboter;
	Ladestation ladestation;
	Roboterleitsystem roboterleitsystem;
	Pflanzenverwaltung pverwaltung;
	Uhr uhr;

	@Before
	public void init() throws Exception {
		uhr = new Uhr(100);
		Gitter g = new Gitter(12, 12, 12, 12);
		// Einzelpflanze ep = new Einzelpflanze(PflanzenArt.eGurke, new
		// Position(2, 2), 0.5, PflanzenStatus.eReif);
		ladestation = new Ladestation(new Position(11, 11));
		roboterleitsystem = new Roboterleitsystem(g, uhr);

		pverwaltung = new Pflanzenverwaltung(g);
		pverwaltung.pflanzeHinzufuegen(PflanzenArt.eGurke);

		roboterleitsystem.roboterHinzufuegen(pverwaltung);
		roboter = roboterleitsystem.getRoboter().get(0);
		// Simuliere aufgeladene Pflanze
		einzelernte = new Einzelernte((Einzelpflanze) pverwaltung.getAllePflanzen().values().toArray()[0],
				roboterleitsystem);

	}

	@Test
	public void ausfuehren_in_undefinierter_Anzahl_Schritte() {
		uhr.addObserver(einzelernte);
		uhr.addObserver(roboterleitsystem);
		assertTrue(roboter.getFuellstand() == 0);
		double initLadestand = roboter.getLadestand();
		einzelernte.setRoboter(roboter);
		einzelernte.ausfuehren(roboter);

		while (!einzelernte.getStatus().equals(UnterauftragsStatus.beendet)) {
			assertEquals(einzelernte.getStatus(), UnterauftragsStatus.ausfuehrend);
			uhr.tick();
		}
		assertTrue(initLadestand > roboter.getLadestand());
		assertTrue(einzelernte.getStatus().equals(UnterauftragsStatus.beendet));
		assertTrue(roboter.getFuellstand() == 1);
	}
}
