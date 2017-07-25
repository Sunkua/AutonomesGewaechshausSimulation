package gewaechshaus.logic;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class EinzelernteTest {
	Einzelernte einzelernte;
	Roboter roboter;
	Einzelpflanze ep;
	Roboterleitsystem roboterleitsystem;
	Position zielPosition;
	ArrayList<Position> wegListe;
	ArrayList<Position> freieNachbarfelder;

	@Before
	public void init() throws KeinWegGefundenException {
		zielPosition = new Position(2, 2);

		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(0, 0));

		ep = Mockito.mock(Einzelpflanze.class);
		Mockito.when(ep.getPosition()).thenReturn(zielPosition);

		freieNachbarfelder = new ArrayList<Position>();
		freieNachbarfelder.add(zielPosition);

		wegListe = new ArrayList<Position>();
		wegListe.add(zielPosition);

		roboterleitsystem = Mockito.mock(Roboterleitsystem.class);
		Mockito.when(roboterleitsystem.getFreieNachbarFelderVon(ep.getPosition()))
				.thenReturn(freieNachbarfelder);

		einzelernte = new Einzelernte(null, roboterleitsystem);
	}

	@Test
	public void fahreZuNachbarpositionNachUntenPositiv() throws KeinWegGefundenException {
		//roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(2, 1));
		
		roboterleitsystem = Mockito.mock(Roboterleitsystem.class);
		Mockito.when(roboterleitsystem.getFreieNachbarFelderVon(ep.getPosition()))
				.thenReturn(freieNachbarfelder);

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		einzelernte.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachUnten();
	}

	@Test
	public void fahreZuNachbarpositionNachObenPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(2, 3));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		einzelernte.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachOben();
	}

	@Test
	public void fahreZuNachbarpositionNachLinksPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(3, 2));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		einzelernte.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachLinks();
	}

	@Test
	public void fahreZuNachbarpositionNachRechtsPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(1, 2));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		einzelernte.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachRechts();
	}

	@Test
	public void ausfuehrenPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(1, 2));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		assertEquals(einzelernte.zustand, 0);

		einzelernte.ausfuehren(roboter);

		assertEquals(einzelernte.zustand, 1);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(2, 2));

		einzelernte.ausfuehren(roboter);

		assertEquals(einzelernte.zustand, 2);

		einzelernte.ausfuehren(roboter);

		assertEquals(einzelernte.status, UnterauftragsStatus.beendet);
		Mockito.verify(roboter).fahreNachRechts();
	}
}
