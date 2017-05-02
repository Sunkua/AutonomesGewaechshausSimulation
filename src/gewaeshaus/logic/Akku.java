package gewaeshaus.logic;

import java.io.IOException;

import java.util.logging.*;

public class Akku {
	
    private static final Logger log = Logger.getLogger( Akku.class.getName() );
    
    private double Ladestand;
    private double kritischeGrenze;

    public Akku(double Ladestand, double kritGrenze) throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
        this.Ladestand = Ladestand;
        this.kritischeGrenze = kritGrenze;
    }

    public double getLadestand() {
        return this.Ladestand;
    }

    public void setLadestand(double ladestand) {
        this.Ladestand = ladestand;
    }

    public boolean istKritisch() {
        return (Ladestand < kritischeGrenze);
    }
}
