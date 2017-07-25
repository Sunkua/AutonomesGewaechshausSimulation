package gewaechshaus.logic;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AbladenTest {
	Abladen abladen;
	Roboter roboter;
	Abladestation abladestation;
	Roboterleitsystem roboterleitsystem;
	Position zielPosition;
	ArrayList<Position> wegListe;

	@Before
	public void init() throws KeinWegGefundenException {
		zielPosition = new Position(2, 2);

		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(0, 0));

		abladestation = Mockito.mock(Abladestation.class);
		Mockito.when(abladestation.getPosition()).thenReturn(zielPosition);

		ArrayList<Position> freieNachbarfelder = new ArrayList<Position>();
		freieNachbarfelder.add(zielPosition);

		wegListe = new ArrayList<Position>();
		wegListe.add(zielPosition);

		roboterleitsystem = Mockito.mock(Roboterleitsystem.class);
		Mockito.when(roboterleitsystem.getFreieNachbarFelderVon(abladestation.getPosition()))
				.thenReturn(freieNachbarfelder);

		abladen = new Abladen(roboterleitsystem, abladestation);
	}

	@Test
	public void fahreZuNachbarpositionNachUntenPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(2, 1));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		Mockito.verify(roboter).fahreNachUnten();
	}

	@Test
	public void fahreZuNachbarpositionNachObenPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(2, 3));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		abladen.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachOben();
	}

	@Test
	public void fahreZuNachbarpositionNachLinksPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(3, 2));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		abladen.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachLinks();
	}

	@Test
	public void fahreZuNachbarpositionNachRechtsPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(1, 2));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		abladen.fahreZuNachbarposition(roboter);

		Mockito.verify(roboter).fahreNachRechts();
	}

	@Test
	public void ausfuehrenPositiv() throws KeinWegGefundenException {
		roboter = Mockito.mock(Roboter.class);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(1, 2));

		Mockito.when(roboterleitsystem.getPfadVonNach(roboter.getPosition(), zielPosition)).thenReturn(wegListe);

		assertEquals(abladen.zustand, 0);

		abladen.ausfuehren(roboter);

		assertEquals(abladen.zustand, 1);
		Mockito.when(roboter.getPosition()).thenReturn(new Position(2, 2));

		abladen.ausfuehren(roboter);

		assertEquals(abladen.zustand, 2);

		abladen.ausfuehren(roboter);

		assertEquals(abladen.status, UnterauftragsStatus.beendet);
		Mockito.verify(roboter).fahreNachRechts();
	}

}