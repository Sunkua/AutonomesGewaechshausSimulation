package gewaechshaus.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {
	Position position;

	@Before
	public void init() {
		position = new Position();
	}

	@Test
	public void Neue_Position_mit_korrekten_Integer_Werten() {
		position = new Position(0, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void Neue_Position_mit_x_Wert_kleiner_Null_Integer_Exception() {
		position = new Position(-1, 0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void Neue_Position_mit_y_Wert_kleiner_Null_Integer_Exception() {
		position = new Position(0, -1);
	}

	@Test
	public void Neue_Position_mit_korrekten_Double_Werten() {
		position = new Position(0.0, 0.0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void Neue_Position_mit_x_Wert_kleiner_Null_Double_Exception() {
		position = new Position(-1.0, 0.0);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void Neue_Position_mit_y_Wert_kleiner_Null_Double_Exception() {
		position = new Position(0.0, -1.0);
	}

	@Test
	public void berechneReihenPosition_mit_korrekten_Wert() {
		position = new Position(50.5, 50.5);
		position.berechneReihenPosition(10, 100);
		assertEquals(position.getReihenID(), 5);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void berechneReihenPosition_negativer_maxReihen_Wert_Exception() {
		position = new Position(50.5, 50.5);
		position.berechneReihenPosition(-1, 100);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void berechneReihenPosition_negativer_maxHoehe_Wert_Exception() {
		position = new Position(50.5, 50.5);
		position.berechneReihenPosition(10, -1);
	}

	@Test(expected = Exception.class)
	public void berechneReihenPosition_0_maxReihen_Wert_Exception() {
		position = new Position(50.5, 50.5);
		position.berechneReihenPosition(0, 100);
	}

	@Test(expected = Exception.class)
	public void berechneReihenPosition_0_maxHoehe_Wert_Exception() {
		position = new Position(50.5, 50.5);
		position.berechneReihenPosition(10, 0);
	}

	@Test
	public void berechneSpaltenPosition_mit_korrekten_Wert() {
		position = new Position(50.5, 50.5);
		position.berechneSpaltenPosition(10, 100);
		assertEquals(position.getSpaltenID(), 5);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void berechneSpaltenPosition_negativer_maxSpalten_Wert_Exception() {
		position = new Position(50.5, 50.5);
		position.berechneSpaltenPosition(-1, 100);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void berechneSpaltenPosition_negativer_maxBreite_Wert_Exception() {
		position = new Position(50.5, 50.5);
		position.berechneSpaltenPosition(10, -1);
	}

	@Test(expected = Exception.class)
	public void berechneSpaltenPosition_0_maxSpalten_Wert_Exception() {
		position = new Position(50.5, 50.5);
		position.berechneSpaltenPosition(0, 100);
	}

	@Test(expected = Exception.class)
	public void berechneSpaltenPosition_0_maxBreite_Wert_Exception() {
		position = new Position(50.5, 50.5);
		position.berechneSpaltenPosition(10, 0);
	}

	@Test
	public void equals_mit_gleicher_Position_Integer_True() {
		position = new Position(10, 10);
		assertTrue(position.equals(new Position(10, 10)));
	}

	@Test
	public void equals_mit_anderer_Position_Integer_False() {
		position = new Position(10, 10);
		assertFalse(position.equals(new Position(10, 1)));
	}

	@Test
	public void equals_mit_gleicher_Position_Double_False() {
		position = new Position(10.1, 10.1);
		assertFalse(position.equals(new Position(10.1, 10.1)));
	}

	/**
	 * Soll eine Exception werfen. Sollte aber präzisiert werden.
	 */
	@Test
	public void equals_mit_anderer_Position_Double_False() {
		position = new Position(10.1, 10.1);
		assertFalse(position.equals(new Position(10.1, 1.1)));
	}

	@Test
	public void equals_mit_null_vergleich_False() {
		position = new Position(10, 10);
		assertFalse(position == null);
	}

	@Test
	public void hashCode_ohne_Initialisierung() {
		position.hashCode();
	}

	@Test
	public void hashCode_mit_Integer_Initialisierung() {
		position = new Position(10, 10);
		assertEquals(position.hashCode(), 75804992);
	}

	@Test
	public void hashCode_mit_Double_Initialisierung() {
		position = new Position(10.0, 10.0);
		assertEquals(position.hashCode(), 75466720);
	}
}
