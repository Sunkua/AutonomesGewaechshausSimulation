package gewaechshaus.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Auftragsgenerator {

    private static final Logger log = Logger.getLogger(Auftragsgenerator.class.getName());

    private Pflanzenverwaltung pVerwaltung;

    public Auftragsgenerator(Pflanzenverwaltung pVerwaltung, Gitter gitter) throws SecurityException, IOException {
        this.pVerwaltung = pVerwaltung;

    }

    public Auftrag pflanzenVonArtScannen(PflanzenArt pArt) {
        Auftrag auftrag = new Auftrag();
        List<Unterauftrag> unterauftragsListe = new ArrayList<Unterauftrag>();
        Map<Position, Einzelpflanze> pflanzen = pVerwaltung.getPflanzenMapVonTyp(pArt);
        pflanzen.forEach((k, v) -> unterauftragsListe.add(new Einzelscan(v)));
        auftrag.setUnterauftraege(unterauftragsListe);
        return auftrag;
    }


    public Auftrag pflanzenVonStatusScannen(PflanzenStatus pStatus) {
        Auftrag auftrag = new Auftrag();
        List<Unterauftrag> unterauftragsListe = new ArrayList<Unterauftrag>();
        Map<Position, Einzelpflanze> pflanzen = pVerwaltung.getPflanzenMapVonStatus(pStatus);
        pflanzen.forEach((k, v) -> unterauftragsListe.add(new Einzelscan(v)));
        auftrag.setUnterauftraege(unterauftragsListe);
        return auftrag;
    }


    public Auftrag pflanzenVonArtErnten(PflanzenArt pflanzenArt) {
        Auftrag auftrag = new Auftrag();
        List<Unterauftrag> unterauftragsListe = new ArrayList<Unterauftrag>();
        Map<Position, Einzelpflanze> pflanzen = pVerwaltung.getPflanzenMapVonTyp(pflanzenArt);
        pflanzen.forEach((k, v) -> unterauftragsListe.add(new Einzelernte(v)));
        auftrag.setUnterauftraege(unterauftragsListe);
        return auftrag;
    }

    public Auftrag pflanzenVonStatusErnten(PflanzenStatus pStatus) {
        Auftrag auftrag = new Auftrag();
        List<Unterauftrag> unterauftragsListe = new ArrayList<Unterauftrag>();
        Map<Position, Einzelpflanze> pflanzen = pVerwaltung.getPflanzenMapVonStatus(pStatus);
        pflanzen.forEach((k, v) -> unterauftragsListe.add(new Einzelernte(v)));
        auftrag.setUnterauftraege(unterauftragsListe);
        return auftrag;
    }


}
