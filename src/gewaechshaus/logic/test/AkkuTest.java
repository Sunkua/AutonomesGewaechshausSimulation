package gewaechshaus.logic.test;

import gewaechshaus.logic.Akku;
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

    @Rule
    public ExpectedException exceptions = ExpectedException.none();


    @Before
    public void init() {
        akku = new Akku(100,50);
    }

    @Test
    public void getLadestand() throws Exception {
fail();


    }

    @Test(expected = IllegalArgumentException.class)
    public void setLadestand() throws Exception {
        exceptions.expect(IllegalArgumentException.class);
        akku.setLadestand(200);

    }

    @Test
    public void istKritisch() throws Exception {
        fail();
    }

}