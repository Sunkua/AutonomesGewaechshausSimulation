package gewaechshaus.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;

/**
 * Dieser Test simuliert den perfekten Ablauf. Zwei Pflanzen werden der
 * Pflanzenverwaltung hinzugefügt und wachsen Die Beladungsgrenze des Roboters
 * wird so eingestellt, dass der Roboter nach mindestens einer geernteten
 * Pflanze zur Abladestation fahren und abladen muss. Die kritische Ladegrenze
 * wird ebenfalls so eingestellt, dass der Roboter nach dem Abladen innerhalb
 * des Tests mindestens einmal zur Ladestation fahren muss und auflädt bevor er
 * die zweite Pflanze ernten kann
 */
public class SystemTest {

	Pflanzenverwaltung pVerwaltung;
	Roboterleitsystem leitSystem;
	Auftragsgenerator auftragsgenerator;
	Abladestation abladestation;
    Ladestation ladestation;
    Uhr uhr;

	@Before
	public void init() {
		// Initialisierung der Testumgebung
		Gitter gitter = new Gitter(12f, 12f, 12, 12);
		pVerwaltung = new Pflanzenverwaltung(gitter);

		uhr = new Uhr(200);
		leitSystem = new Roboterleitsystem(gitter, uhr);
		auftragsgenerator = new Auftragsgenerator(pVerwaltung, leitSystem, uhr);

		Position abladestelle = new Position(11, 11);
		abladestation = new Abladestation(abladestelle);
        ladestation = new Ladestation(new Position(0, 11));

		pVerwaltung.addObserver(leitSystem);
		pVerwaltung.addObserver(gitter);
		leitSystem.addObserver(gitter);
		uhr.addObserver(leitSystem);
		uhr.addObserver(pVerwaltung);

		leitSystem.abladestationHinzufuegen(abladestation);
        leitSystem.ladestationHinzufuegen(ladestation);
        leitSystem.roboterHinzufuegen(pVerwaltung);

		abladestation.setAblagetyp(AblageTyp.eGut);
		abladestation.leeren();
	}

	@Test
	public void testSystem() {
		int a;

		// 2 Pflanzen hinzufügen
		try {
			pVerwaltung.pflanzeHinzufuegen(PflanzenArt.eTomate);
			pVerwaltung.pflanzeHinzufuegen(PflanzenArt.eTomate);
		} catch (Exception e) {
			fail("Fehler beim Hinzufügen der Pflanze!");
		}

		// kritische Grenzen des Roboters setzen
		Roboter r = leitSystem.getRoboter().get(0);
        r.getAkku().setKritischeGrenze(90);
        Konstanten.maximalerFuellstand = 1;

		// Mache die Pflanze schwerer, damit sie weggebracht werden muss)
		double Tomatengewicht = Konstanten.maximalerFuellstand + 1;
		pVerwaltung.holePflanzenVonStatus(PflanzenStatus.eUnreif).get(0).setGewicht(Tomatengewicht);

		a = 0; // Safeguard, falls die Pflanze nicht wächst.
		// So lange Pflanze wachsen lassen bis sie reif ist (Keine Unreifen mehr
		// da
		// sind)
		// Bei 0.2 Wachstum pro Tick, bei 90 = reif, dann ist die Pflanze
		// ausgewachsen
		double maxTicks = 90 / Konstanten.WachstumTomate + 30;
        boolean ladenAuftragIstVorgekommen = false;

        // Pflanzen reifen lassen
		while (!pVerwaltung.holePflanzenVonStatus(PflanzenStatus.eUnreif).isEmpty() && a <= 10000) {
			uhr.tick();
			a++;
		}

		// Teste ob es wirklich nicht mehr leer ist oder er nur aus der Schleife
		// geflogen ist
		assertNotEquals("Pflanze ist nicht gewachsen", pVerwaltung.holePflanzenVonStatus(PflanzenStatus.eReif), 0);

		leitSystem.auftragHinzufuegen(auftragsgenerator.pflanzenVonStatusErnten(PflanzenStatus.eReif));

		a = 0; // Safeguard, falls der Roboter nicht fährt.
        // Ernten, Abladen, Akku laden, Ernten
		while ((abladestation.getFuellstand() == 0 || r.getFuellstand() == 0) && (a < 10000)) {
			uhr.tick();
            if (r.getStatus().equals(RoboterStatus.eLädt)) {
				ladenAuftragIstVorgekommen = true;
			}
			a++;
		}
        Logging.log(this.getClass().getName(), Level.INFO, "Test wurde nach: " + a + " Schritten beendet");

        assertTrue("Füllstand des Roboters muss mindestens > 0 sein", r.getFuellstand() > 0);
        assertTrue("Füllstand der Abladestation muss > 0 sein", abladestation.getFuellstand() > 0);
        assertTrue("Mindestens einmal muss geladen worden sein", ladenAuftragIstVorgekommen);
	}
}
