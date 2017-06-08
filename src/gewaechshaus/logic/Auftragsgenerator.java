package gewaechshaus.logic;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.logging.Logger;

public class Auftragsgenerator {

    private static final Logger log = Logger.getLogger(Auftragsgenerator.class.getName());

    private Pflanzenverwaltung pVerwaltung;
    private Roboterleitsystem roboterleitsystem;

    public Auftragsgenerator(Pflanzenverwaltung pVerwaltung, Roboterleitsystem roboterleitsystem, Gitter gitter) {
        this.pVerwaltung = pVerwaltung;
        this.roboterleitsystem = roboterleitsystem;
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
        pflanzen.forEach((k, v) -> unterauftragsListe.add(new Einzelernte(v, roboterleitsystem)));
        auftrag.setUnterauftraege(unterauftragsListe);
        return auftrag;
    }

    public Auftrag pflanzenVonStatusErnten(PflanzenStatus pStatus) {
        Auftrag auftrag = new Auftrag();
        List<Unterauftrag> unterauftragsListe = new ArrayList<Unterauftrag>();
        Map<Position, Einzelpflanze> pflanzen = pVerwaltung.getPflanzenMapVonStatus(pStatus);
        pflanzen.forEach((k, v) -> unterauftragsListe.add(new Einzelernte(v, roboterleitsystem)));
        auftrag.setUnterauftraege(unterauftragsListe);
        return auftrag;
    }


}
