package gewaechshaus.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Date;

public class EinzelpflanzeTest {
	Einzelpflanze einzelpflanzeOhne;
	Einzelpflanze einzelpflanzeMit;
	Position position = new Position(4, 4);
	double gewicht = 1.3;

    @Before
    public void init() {
    	einzelpflanzeOhne = new Einzelpflanze();
    	einzelpflanzeMit = new Einzelpflanze(PflanzenArt.eGurke, position, gewicht, PflanzenStatus.eReif);
    }
    
    @Test
    public void erstelle_Einzelpflanze_ohne_Inhalt_ist_nicht_null() throws Exception {
    	assertFalse(einzelpflanzeOhne.equals(null));
    }

    @Test
    public void erstelle_Einzelpflanze_mit_Inhalt_ist_nicht_null() throws Exception {
    	assertFalse(einzelpflanzeMit.equals(null));
    }
}
