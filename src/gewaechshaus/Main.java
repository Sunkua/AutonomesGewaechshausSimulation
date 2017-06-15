package gewaechshaus;

import gewaechshaus.gui.GUI;
import gewaechshaus.logic.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) throws JAXBException, IOException {

        Logging.log("main", Level.INFO, "main gestartet");
        
        // TODO Auto-generated method stub
        Pflanzenverwaltung pVerwaltung = new Pflanzenverwaltung();
        pVerwaltung.setMaxGröße(10, 10);
        Gitter gitter = new Gitter(10f, 10f, 10, 10);
        Roboterleitsystem leitSystem = new Roboterleitsystem(gitter);
        GUI gui = new GUI(pVerwaltung, leitSystem);

        // Set Observers
        pVerwaltung.addObserver(leitSystem);
        pVerwaltung.addObserver(gui);
        pVerwaltung.addObserver(gitter);
        leitSystem.addObserver(gitter);
        leitSystem.addObserver(gui);

        Roboter r = new Roboter(leitSystem, pVerwaltung);

        // important call with float values or set x and y position
        Position roboPos = new Position(5f, 5f);
        gitter.toKarthesisch(roboPos);
        leitSystem.roboterHinzufuegen(r, roboPos);

        r.addObserver(leitSystem);

        // init Test-Pflanzen
        for (int i = 0; i < gitter.getBreite(); i++) {
            for (int j = 0; j < gitter.getHoehe(); j++) {
                if (i % 5 != 0 && j % 3 != 0) {
                    Einzelpflanze t = new Einzelpflanze(PflanzenArt.eGurke, new Position(i, j), 0.5, PflanzenStatus.eReif, null);
                    pVerwaltung.pflanzeHinzufuegen(t);
                }
            }
        }

        Einzelpflanze t = new Einzelpflanze(PflanzenArt.eGurke, new Position(5, 4), 0.5, PflanzenStatus.eReif, null);
        pVerwaltung.pflanzeHinzufuegen(t);


        Position ziel = new Position(0,0);
        gitter.toKarthesisch(ziel);
        Runnable task = () -> {

        };
        Thread thread = new Thread(task);
        thread.start();
        System.out.println("test");
    }


    public void pflanzenverwaltungZustandInDateiSpeichern(String filename, Pflanzenverwaltung pflanzenverwaltung) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Pflanzenverwaltung.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(pflanzenverwaltung, new File(filename));
    }

    public void leitsystemZustandInDateiSpeichern(String filename, Roboterleitsystem roboterLeitsystem) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Roboterleitsystem.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(roboterLeitsystem, new File(filename));
    }

    public Roboterleitsystem leitsystemZustandAusDateiLesen(String filename) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Bedienterminal.class);
        Unmarshaller m = context.createUnmarshaller();
        Roboterleitsystem leitsystem = (Roboterleitsystem) m.unmarshal(new FileReader(filename));
        return leitsystem;
    }

    public Pflanzenverwaltung pflanzenVerwaltungZustandAusDateiLesen(String filename) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Bedienterminal.class);
        Unmarshaller m = context.createUnmarshaller();
        Pflanzenverwaltung pflanzenverwaltung = (Pflanzenverwaltung) m.unmarshal(new FileReader(filename));
        return pflanzenverwaltung;
    }


}
