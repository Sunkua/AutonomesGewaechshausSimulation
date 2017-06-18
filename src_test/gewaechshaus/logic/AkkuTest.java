package gewaechshaus.logic;

import gewaechshaus.logic.Akku;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    public void getLadestand() throws Exception {
        akku.setLadestand(50);
        assertTrue(50f== akku.getLadestand());
    }


    @Test(expected = IllegalArgumentException.class)
    public void setLadestandZuHoch() throws Exception {
        akku.setLadestand(200);
    }

    @Test(expected = IllegalArgumentException.class)
    public void  setLadestandZuTief() throws Exception {
        akku.setLadestand(-100);
    }
    @Test
    public void setLadestandKorrekt() throws Exception {
        akku.setLadestand(50f);
        assertTrue(akku.getLadestand() == 50f);
    }

    @Test
    public void istKritischKorrekt() throws Exception {
        akku = new Akku(40,50);
        assertTrue(akku.istKritisch());
    }

    @Test
    public void istKritischNichtKorrekt()
    {
        akku = new Akku(40,30);
        assertFalse(akku.istKritisch());
    }


    @After
    public void reset()
    {
        akku = null;
    }

}