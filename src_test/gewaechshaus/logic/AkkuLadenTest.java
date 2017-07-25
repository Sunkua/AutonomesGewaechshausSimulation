package gewaechshaus.logic;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AkkuLadenTest {
	AkkuLaden akkuLaden;
	Roboter roboter;
	Ladestation ladestation;
	Roboterleitsystem roboterleitsystem;
	Position zielPosition;
	ArrayList<Position> wegListe;

	@Before
	public void init() throws KeinWegGefundenException {
		zielPosition = new Position(2, 2);

		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(0, 0));

		ladestation = Mockito.mock(Ladestation.class);
		Mockito.when(ladestation.getPosition()).thenReturn(zielPosition);

		ArrayList<Position> freieNachbarfelder = new ArrayList<Position>();
		freieNachbarfelder.add(zielPosition);

		wegListe = new ArrayList<Position>();
		wegListe.add(zielPosition);

		roboterleitsystem = Mockito.mock(Roboterleitsystem.class);
		Mockito.when(roboterleitsystem.getFreieNachbarFelderVon(ladestation.getPosition()))
				.thenReturn(freieNachbarfelder);

		akkuLaden = new AkkuLaden(roboterleitsystem, ladestation);
	}

	@Test
	public void fahreZuNachbarpositionNachUntenPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(2, 1));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		akkuLaden.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachUnten();
	}

	@Test
	public void fahreZuNachbarpositionNachObenPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(2, 3));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		akkuLaden.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachOben();
	}

	@Test
	public void fahreZuNachbarpositionNachLinksPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(3, 2));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		akkuLaden.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachLinks();
	}

	@Test
	public void fahreZuNachbarpositionNachRechtsPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(1, 2));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		akkuLaden.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachRechts();
	}

	@Test
	public void ausfuehrenPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(1, 2));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		assertEquals(akkuLaden.zustand, 0);

		akkuLaden.ausfuehren(roboter);

		assertEquals(akkuLaden.zustand, 1);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(2, 2));

		akkuLaden.ausfuehren(roboter);

		assertEquals(akkuLaden.zustand, 2);
		Mockito.when(roboter.getAkku().getLadestand()).thenReturn(99.5);

		akkuLaden.ausfuehren(roboter);

		assertEquals(akkuLaden.status, UnterauftragsStatus.beendet);
		Mockito.verify(roboter).fahreNachRechts();
	}
}
