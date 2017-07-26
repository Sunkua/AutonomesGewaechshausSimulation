package gewaechshaus.logic;

import org.junit.*;

/**
 * Dieser Test simuliert den perfekten Ablauf. Eine Pflanze wird gesetzt, wächst, es wird ein Auftrag zum ernten erstellt
 * und sobald der Inhalt an der Ablagestation ankommt ist alles wunderbar.
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
			Assert.fail("Fehler beim Hinzufügen der Pflanze!");
		}
        
        a = 0; // Safeguard, falls die Pflanze nicht wächst.
        while ((pVerwaltung.holePflanzenVonStatus(PflanzenStatus.eReif).isEmpty() == true) && ( a < 1000))
        {
        	uhr.tick();
        	a++;
        }
        
        // Teste ob es wirklich nicht mehr leer ist oder er nur aus der Schleife geflogen ist
        Assert.assertFalse(pVerwaltung.holePflanzenVonStatus(PflanzenStatus.eReif).isEmpty());
        
        leitSystem.auftragHinzufuegen(auftragsgenerator.pflanzenVonStatusErnten(PflanzenStatus.eReif));
        
        a = 0; // Safeguard, falls der Roboter nicht fährt.
        while ((abladestation.getFuellstand() == 0.0) && (a < 1000))
        {
        	uhr.tick();
        	a++;
        }
        
        if (abladestation.getFuellstand() > 0.0)
        {
        	Assert.assertTrue(true);
        }
        else
        {
        	Assert.fail("Keine Früchte an der Ladestation erhalten");
        }
	}
}
