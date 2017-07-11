package gewaechshaus.logic;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.logging.Logger;

public class Auftragsgenerator {

    private static final Logger log = Logger.getLogger(Auftragsgenerator.class.getName());

    private Pflanzenverwaltung pVerwaltung;
    private Roboterleitsystem roboterleitsystem;
    private Uhr uhr;

    /**
     * Klasse zum Erstellen von Aufträgen für das Leitsystem
     *
     * @param pVerwaltung       Pflanzenverwaltung um Referenzen auf die Pflanzen zu haben
     * @param roboterleitsystem Leitsystem um einem erstellten Auftrag das Leitsystem zuzuweisen
     * @param uhr               Uhr zum triggern der Ereignisse im jeweiligen Auftrag
     */
    public Auftragsgenerator(Pflanzenverwaltung pVerwaltung, Roboterleitsystem roboterleitsystem, Uhr uhr) {
        this.pVerwaltung = pVerwaltung;
        this.roboterleitsystem = roboterleitsystem;
        this.uhr = uhr;
    }

    /**
     * Gibt einen Auftrag zurück, der eine Liste von Unteraufträgen enthält, die Pflanzen einer Art scannen
     *
     * @param pArt die Pflanzenart, die gescannt werden soll
     * @return Auftrag mit entsprechenden Unteraufträgen
     */
    public Auftrag pflanzenVonArtScannen(PflanzenArt pArt) {
        Auftrag auftrag = new Auftrag(uhr, roboterleitsystem);
        List<Unterauftrag> unterauftragsListe = new ArrayList<Unterauftrag>();
        Map<Position, Einzelpflanze> pflanzen = pVerwaltung.getPflanzenMapVonTyp(pArt);
        pflanzen.forEach((k, v) -> unterauftragsListe.add(new Einzelscan(v)));
        auftrag.setUnterauftraege(unterauftragsListe);
        return auftrag;
    }


    /**
     * Gibt einen Auftrag zurück, der eine Liste von Unteraufträgen enthält, die Pflanzen mit einem bestimmten Status scannen
     * @param pStatus Status der Pflanzen die gescannt werden sollen
     * @return Auftrag mit entsprechenden Unteraufträgen
     */
    public Auftrag pflanzenVonStatusScannen(PflanzenStatus pStatus) {
        Auftrag auftrag = new Auftrag(uhr, roboterleitsystem);
        List<Unterauftrag> unterauftragsListe = new ArrayList<Unterauftrag>();
        Map<Position, Einzelpflanze> pflanzen = pVerwaltung.getPflanzenMapVonStatus(pStatus);
        pflanzen.forEach((k, v) -> unterauftragsListe.add(new Einzelscan(v)));
        auftrag.setUnterauftraege(unterauftragsListe);
        return auftrag;
    }


    /**
     * Gibt einen Auftrag zurück, der eine Liste von Unteraufträgen enthält, die Pflanzen einer Art ernten
     * @param pflanzenArt Die Art der Pflanzen die geerntet werden sollen
     * @return Auftrag mit entsprechenden Unteraufträgen
     */
    public Auftrag pflanzenVonArtErnten(PflanzenArt pflanzenArt) {
        Auftrag auftrag = new Auftrag(uhr, roboterleitsystem);
        List<Unterauftrag> unterauftragsListe = new ArrayList<Unterauftrag>();
        Map<Position, Einzelpflanze> pflanzen = pVerwaltung.getPflanzenMapVonTyp(pflanzenArt);
        pflanzen.forEach((k, v) -> unterauftragsListe.add(new Einzelernte(v, roboterleitsystem)));
        auftrag.setUnterauftraege(unterauftragsListe);
        return auftrag;
    }

    /**
     * Gibt einen Auftrag zurück, der eine Liste von Unteraufträgen enthält, die Pflanzen mit einem bestimmten Status scannen
     * @param pStatus Der Status der Pflanzen die geerntet werden sollen
     * @return Auftrag mit entsprechenden Unteraufträgen
     */
    public Auftrag pflanzenVonStatusErnten(PflanzenStatus pStatus) {
        Auftrag auftrag = new Auftrag(uhr, roboterleitsystem);
        List<Unterauftrag> unterauftragsListe = new ArrayList<Unterauftrag>();
        Map<Position, Einzelpflanze> pflanzen = pVerwaltung.getPflanzenMapVonStatus(pStatus);
        pflanzen.forEach((k, v) -> unterauftragsListe.add(new Einzelernte(v, roboterleitsystem)));
        auftrag.setUnterauftraege(unterauftragsListe);
        return auftrag;
    }


}
