/**
 * 
 */
package gewaechshaus.logic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author benny
 *
 */
public class AbladestationTest {

	Position position = new Position(4, 4);
	Abladestation abladestation;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void init() throws Exception {
		abladestation = new Abladestation(position);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		abladestation = null;
	}

	/**
	 * Test method for {@link gewaechshaus.logic.Abladestation#Abladestation(gewaechshaus.logic.Position)}.
	 */
	@Test
	public void testAbladestation() {
		assertNotNull(abladestation);
	}


	/**
	 * Test method for {@link gewaechshaus.logic.Abladestation#leeren()}.
	 */
	@Test
	public void testLeeren() {
		abladestation.leeren();
		assertEquals(0, abladestation.getFuellstand(), 0);
	}

}
