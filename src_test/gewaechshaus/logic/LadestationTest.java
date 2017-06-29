package gewaechshaus.logic;

import org.junit.Before;
import org.junit.Test;

public class LadestationTest {
	Ladestation ladestation;
	
	@Before
	public void init() {
		ladestation = new Ladestation(new Position(4, 4));
	}
	
	@Test
	public void verbinde_ein_Roboter_kein_ohne_exception() throws Exception {
		ladestation.verbinden();
	}
	
	@Test(expected = Exception.class)
	public void verbinde_zwei_Roboter_exception() throws Exception {
		ladestation.verbinden();
		ladestation.verbinden();
	}
	
	@Test
	public void trenne_einen_Roboter_mit_Roboter_ohne_exception() throws Exception {
		ladestation.verbinden();
		ladestation.trennen();
	}
	
	@Test(expected = Exception.class)
	public void trenne_einen_Roboter_ohne_Roboter_exception() throws Exception {
		ladestation.trennen();
	}
}
