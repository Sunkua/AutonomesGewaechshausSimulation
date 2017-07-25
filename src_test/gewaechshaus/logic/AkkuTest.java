package gewaechshaus.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sunku on 01.06.2017.
 */
public class AkkuTest {
	Akku akku;

	@Before
	public void init() {
		akku = new Akku(100, 50);
	}

	@Test
	public void getLadestandPositiv() throws Exception {
		akku.setLadestand(50);
		assertTrue(50f == akku.getLadestand());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setLadestandZuHochExeption() throws Exception {
		akku.setLadestand(101);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setLadestandZuTiefExeption() throws Exception {
		akku.setLadestand(-1);
	}

	@Test
	public void setLadestandPositiv() throws Exception {
		akku.setLadestand(50f);
		assertTrue(akku.getLadestand() == 50f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setKritischeGrenzeZuHochExeption() throws Exception {
		akku = new Akku(100, 101);
	}

	@Test(expected = IllegalArgumentException.class)
	public void settKritischeGrenzeZuTiefExeption() throws Exception {
		akku = new Akku(100, -1);
	}

	@Test
	public void settKritischeGrenzePositiv() throws Exception {
		akku = new Akku(100, 50);
	}

	@Test
	public void istKritischPositiv() throws Exception {
		akku = new Akku(40, 50);
		assertTrue(akku.istKritisch());
	}

	@Test
	public void istKritischNegativ() {
		akku = new Akku(40, 30);
		assertFalse(akku.istKritisch());
	}

	@Test
	public void istLeerPositiv() {
		akku = new Akku(0, 30);
		assertTrue(akku.istLeer());
	}

	@Test
	public void istLeerNegativ() {
		akku = new Akku(1, 30);
		assertFalse(akku.istLeer());
	}

	@Test
	public void aktualisierenOhneAenderung() {
		akku = new Akku(1, 30);
		akku.aktualisieren(0.0);
		assertTrue(akku.getLadestand() == 1f);
	}

	@Test
	public void aktualisierenMitAenderung() {
		akku = new Akku(1, 30);
		akku.aktualisieren(0.5);
		assertTrue(akku.getLadestand() == 1.5f);
	}

	@After
	public void reset() {
		akku = null;
	}

}