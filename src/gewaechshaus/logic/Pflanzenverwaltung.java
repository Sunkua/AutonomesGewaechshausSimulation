package gewaechshaus.logic;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import gewaechshaus.gui.GUI;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@XmlRootElement(namespace = "gewaeshaus.logic")
public class Pflanzenverwaltung {

    private static final Logger log = Logger.getLogger(Pflanzenverwaltung.class.getName());
    GUI gui;
    /**
     * Hält die Liste von Pflanzen inkl. deren Positionen
     */
    @XmlElement
    private HashMap<Position, Einzelpflanze> pflanzenListe;
    @XmlElement
    private Position maxGröße = new Position(0, 0);

    public Pflanzenverwaltung() throws SecurityException, IOException {
        pflanzenListe = new HashMap<Position, Einzelpflanze>();
        Handler handler = new FileHandler(Settings.loggingFilePath);
        log.addHandler(handler);

        log.info("Pflanzenverwaltun initialisiert.");
    }

    public void pflanzeHinzufuegen(Einzelpflanze ep) {
        pflanzenListe.put(ep.getPosition(), ep);
        gui.updateGewächshaus();

        log.info("Pflanze " + ep.toString() + "an Position " + ep.getPosition().toString() + "Hinzugefügt");
    }

    public void pflanzeEntfernen(Position p) {
        // ToDo Entferne die Pflanze
        Einzelpflanze pflanze = pflanzenListe.get(p);
        pflanzenListe.remove(p);
        gui.updateGewächshaus();

        log.info("Pflanze " + pflanze.toString() + " an Position " + p.toString() + " entfernt.");
    }

    public void setMaxGröße(int zeilen, int spalten) {
        maxGröße = new Position(spalten, zeilen);
    }

    public Position getMaxGröße() {
        return maxGröße;
    }

    public ArrayList<Einzelpflanze> holePflanzenVonArt(PflanzenArt pa) {
        ArrayList<Einzelpflanze> einzelpflanzen = new ArrayList<Einzelpflanze>();

        for (Map.Entry<Position, Einzelpflanze> entry : pflanzenListe.entrySet()) {
            Einzelpflanze pflanze = entry.getValue();
            if (pflanze.getArt() == pa) {
                einzelpflanzen.add(pflanze);
            }
        }

        return einzelpflanzen;
    }

    public ArrayList<Einzelpflanze> holePflanzenVonStatus(PflanzenStatus ps) {
        ArrayList<Einzelpflanze> einzelpflanzen = new ArrayList<Einzelpflanze>();

        for (Map.Entry<Position, Einzelpflanze> entry : pflanzenListe.entrySet()) {
            Einzelpflanze pflanze = entry.getValue();
            if (pflanze.getPflanzenstatus() == ps) {
                einzelpflanzen.add(pflanze);
            }
        }

        return einzelpflanzen;
    }

    public Map<Position, Einzelpflanze> getPflanzenMapVonTyp(PflanzenArt pArt) {
        return pflanzenListe.entrySet().stream()
                .filter(map -> {
                    return map.getValue().getArt().equals(pArt);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Position, Einzelpflanze> getPflanzenMapVonStatus(PflanzenStatus pStatus) {
        return pflanzenListe.entrySet().stream()
                .filter(map -> {
                    return map.getValue().getPflanzenstatus().equals(pStatus);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Position, Einzelpflanze> getAllePflanzen() {
        return pflanzenListe;
    }


    public Einzelpflanze holePflanzeVonPosition(Position p) {
        Einzelpflanze pflanze = pflanzenListe.get(p);

        log.info("Pflanze an Position " + p.toString() + " geladen.");

        return pflanze;
    }

    public void pflanzenverwaltungZustandInDateiSpeichern() {
        try {
            JAXBContext context = JAXBContext.newInstance(Pflanzenverwaltung.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(this, new File("zustand"));
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }

    public void pflanzenVerwaltungZustandAusDateiLesen() {
        try {
            JAXBContext context = JAXBContext.newInstance(Pflanzenverwaltung.class);
            Unmarshaller m = context.createUnmarshaller();
            Pflanzenverwaltung pflanzenverwaltung = (Pflanzenverwaltung) m.unmarshal(new FileReader("zustand"));
            this.pflanzenListe = pflanzenverwaltung.pflanzenListe;
            this.maxGröße = pflanzenverwaltung.maxGröße;
        } catch (Exception e) {
            log.severe(e.getMessage());
        }

    }


    public void setGui(GUI g) {
        gui = g;
    }

}
