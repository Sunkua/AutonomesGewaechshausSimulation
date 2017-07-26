package gewaechshaus.logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Dieser Test simuliert den perfekten Ablauf. Eine Pflanze wird gesetzt,
 * wächst, es wird ein Auftrag zum ernten erstellt und sobald der Inhalt an der
 * Ablagestation ankommt ist alles wunderbar.
 */
public class SystemTest {

	Pflanzenverwaltung pVerwaltung;
	Roboterleitsystem leitSystem;
	Auftragsgenerator auftragsgenerator;
	Abladestation abladestation;
	Uhr uhr;

	@Before
	public void init() {
		Gitter gitter = new Gitter(12f, 12f, 12, 12);

		pVerwaltung = new Pflanzenverwaltung(gitter);

		uhr = new Uhr(200);
		leitSystem = new Roboterleitsystem(gitter, uhr);
		auftragsgenerator = new Auftragsgenerator(pVerwaltung, leitSystem, uhr);

		Position abladestelle = new Position(11f, 11f);
		gitter.toKarthesisch(abladestelle);
		abladestation = new Abladestation(abladestelle);

		pVerwaltung.addObserver(leitSystem);
		pVerwaltung.addObserver(gitter);
		leitSystem.addObserver(gitter);
		uhr.addObserver(leitSystem);
		uhr.addObserver(pVerwaltung);

		leitSystem.abladestationHinzufuegen(abladestation);
		leitSystem.roboterHinzufuegen(pVerwaltung);

		abladestation.setAblagetyp(AblageTyp.eGut);
		abladestation.leeren();
	}

	@Test
	public void testSystem() {
		int a;

		try {
			pVerwaltung.pflanzeHinzufuegen(PflanzenArt.eTomate);
		} catch (Exception e) {
			fail("Fehler beim Hinzufügen der Pflanze!");
		}

		// Mache die Pflanze schwerer, damit sie weggebracht werden muss)
		double Tomatengewicht = Konstanten.maximalerFuellstand + 1;
		pVerwaltung.holePflanzenVonStatus(PflanzenStatus.eUnreif).get(0).setGewicht(Tomatengewicht);
		;

		a = 0; // Safeguard, falls die Pflanze nicht wächst.
		// So lange Pflanze wachsen lassen bis sie reif ist (Keine Unreifen mehr da
		// sind)  
		// Bei 0.2 Wachstum pro Tick, bei 90 = reif, dann ist die Pflanze ausgewachsen
		double maxTicks = 90 / Konstanten.WachstumTomate + 30;

		while (pVerwaltung.holePflanzenVonStatus(PflanzenStatus.eUnreif).isEmpty() == false && a <= maxTicks) {
			uhr.tick();
			a++;
		}

		// Teste ob es wirklich nicht mehr leer ist oder er nur aus der Schleife
		// geflogen ist
        assertNotEquals("Pflanze ist nicht gewachsen", pVerwaltung.holePflanzenVonStatus(PflanzenStatus.eReif), 0);

		leitSystem.auftragHinzufuegen(auftragsgenerator.pflanzenVonStatusErnten(PflanzenStatus.eReif));

		a = 0; // Safeguard, falls der Roboter nicht fährt.
		while ((abladestation.getFuellstand() == 0.0) && (a < 100)) {
			uhr.tick();
			a++;
		}

		// Die Pflanze wurde in der Abladestation abgegeben
		assertEquals("Tomate wurde nicht geerntet - noch reife Tomate vorhanden", 0,
				pVerwaltung.holePflanzenVonStatus(PflanzenStatus.eReif).size());
        //assertEquals("Tomate wurde nicht geerntet - keine geerntete Tomate vorhanden", 1,
        //		pVerwaltung.holePflanzenVonStatus(PflanzenStatus.eGeerntet).size());
    }
}
