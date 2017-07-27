package gewaechshaus.logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuftragTest {

	Uhr uhr;
	Roboterleitsystem roboterleitsystem;
	Auftrag auftrag;
	Unterauftrag unterauftrag;
	Roboter r;
	Abladestation abladestation;
	Ladestation ladestation;
	ArrayList<Roboter> roboterliste;
	ArrayList<Unterauftrag> unterauftragsListe;

	@Before
	public void setUp() {
		uhr = mock(Uhr.class);
		r = mock(Roboter.class);
		abladestation = mock(Abladestation.class);
		ladestation = mock(Ladestation.class);
		roboterliste = new ArrayList<Roboter>();
		unterauftragsListe = new ArrayList<>();
		roboterliste.add(r);
		roboterleitsystem = mock(Roboterleitsystem.class);
		when(roboterleitsystem.getRoboter()).thenReturn(roboterliste);
		when(roboterleitsystem.getFreieAbladestation()).thenReturn(abladestation);
		when(roboterleitsystem.getFreieLadestation()).thenReturn(ladestation);
		auftrag = new Auftrag(uhr, roboterleitsystem);
	}

	@Test
	public void teste_Einzelernte_roboter_ist_bereit() throws Exception {
		unterauftrag = mock(Einzelernte.class);
		unterauftragsListe.add(unterauftrag);
		auftrag.setUnterauftraege(unterauftragsListe);
		when(r.getStatus()).thenReturn(RoboterStatus.eBereit);
		auftrag.naechstenUnterauftragAusfuehren(r);
		assertTrue(auftrag.getUnterauftragsAnzahl() == 0);
		assertEquals(auftrag.getStatus(), AuftragsStatus.ausfuehrend);
	}

	@Test
	public void teste_Einzelernte_roboter_ist_voll() throws Exception {
		unterauftrag = mock(Einzelernte.class);
		unterauftragsListe.add(unterauftrag);
		auftrag.setUnterauftraege(unterauftragsListe);
		when(r.getStatus()).thenReturn(RoboterStatus.eBehaelterVoll);
		auftrag.naechstenUnterauftragAusfuehren(r);
		assertTrue(auftrag.getUnterauftragsAnzahl() == 1);
	}

	@Test
	public void teste_Einzelernte_Roboterladestand_ist_kritisch_() throws Exception {
		unterauftrag = mock(Einzelernte.class);
		unterauftragsListe.add(unterauftrag);
		auftrag.setUnterauftraege(unterauftragsListe);
		when(r.getStatus()).thenReturn(RoboterStatus.eAkkuKritisch);
		auftrag.naechstenUnterauftragAusfuehren(r);
		assertTrue(auftrag.getUnterauftragsAnzahl() == 1);
	}

	@Test(expected = Exception.class)
	public void teste_Fehlerfall_mehr_aktive_Unterauftraege_als_Roboter() throws Exception {
		unterauftrag = mock(Einzelernte.class);
		unterauftragsListe.add(unterauftrag);
		auftrag.setUnterauftraege(unterauftragsListe);
		when(r.getStatus()).thenReturn(RoboterStatus.eBereit);

		auftrag.naechstenUnterauftragAusfuehren(r);
		roboterliste.clear();
		when(roboterleitsystem.getRoboter()).thenReturn(roboterliste);
		auftrag.naechstenUnterauftragAusfuehren(r);

	}

}
