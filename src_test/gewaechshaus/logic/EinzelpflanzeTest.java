package gewaechshaus.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EinzelpflanzeTest {
	Einzelpflanze einzelpflanze;
	Position position = new Position(4, 4);
	double gewicht = 1.3;

	double erwartetReif = 100.0;
	double erwartetUnreif = 10.0;
	double erwartetSonstiges = 0.0;

	double erwartetWachseTomate = erwartetUnreif + Konstanten.WachstumTomate;
	double erwartetWachseGruke = erwartetUnreif + Konstanten.WachstumGurke;

	@Before
	public void init() {
		einzelpflanze = new Einzelpflanze(PflanzenArt.eGurke, position, gewicht, PflanzenStatus.eUnreif);
	}

	@Test
	public void erstelleEinzelpflanzeNichtNull() throws Exception {
		einzelpflanze = new Einzelpflanze(PflanzenArt.eGurke, position, gewicht, PflanzenStatus.eReif);
		assertFalse(einzelpflanze.equals(null));
	}

	@Test
	public void setPflanzenStatusReif() {
		einzelpflanze.setPflanzenStatus(PflanzenStatus.eReif);
		assertEquals(einzelpflanze.getPflanzenstatus(), PflanzenStatus.eReif);
		assertTrue(einzelpflanze.getReifestatus() == erwartetReif);
	}

	@Test
	public void setPflanzenStatusUnreif() {
		einzelpflanze.setPflanzenStatus(PflanzenStatus.eUnreif);
		assertEquals(einzelpflanze.getPflanzenstatus(), PflanzenStatus.eUnreif);
		assertTrue(einzelpflanze.getReifestatus() == erwartetUnreif);
	}

	@Test
	public void setPflanzenStatusKein() {
		einzelpflanze.setPflanzenStatus(PflanzenStatus.eGeerntet);
		assertEquals(einzelpflanze.getPflanzenstatus(), PflanzenStatus.eGeerntet);
		assertTrue(einzelpflanze.getReifestatus() == erwartetSonstiges);
	}

	@Test
	public void setReifestatusUnreifPositiv() {
		einzelpflanze.setReifestatus(erwartetUnreif);
		assertTrue(einzelpflanze.getReifestatus() == erwartetUnreif);
		assertEquals(einzelpflanze.getPflanzenstatus(), PflanzenStatus.eUnreif);
	}

	@Test
	public void setReifestatusReifPositiv() {
		einzelpflanze.setReifestatus(erwartetReif);
		assertTrue(einzelpflanze.getReifestatus() == erwartetReif);
		assertEquals(einzelpflanze.getPflanzenstatus(), PflanzenStatus.eReif);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void setReifestatusZuHochExeption() {
		einzelpflanze.setReifestatus(100.1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void setReifestatusZuTiefExeption() {
		einzelpflanze.setReifestatus(-0.1);
	}

	@Test
	public void WachseTomate() {
		einzelpflanze = new Einzelpflanze(PflanzenArt.eTomate, position, gewicht, PflanzenStatus.eUnreif);
		einzelpflanze.Wachse();
		assertTrue(einzelpflanze.getReifestatus() == erwartetWachseTomate);
	}

	@Test
	public void WachseGurke() {
		einzelpflanze = new Einzelpflanze(PflanzenArt.eGurke, position, gewicht, PflanzenStatus.eUnreif);
		einzelpflanze.Wachse();
		assertTrue(einzelpflanze.getReifestatus() == erwartetWachseGruke);
	}
}
