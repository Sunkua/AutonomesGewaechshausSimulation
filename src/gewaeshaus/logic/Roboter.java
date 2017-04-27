package gewaeshaus.logic;

public class Roboter {
    private double Batteriestatus;
    private double Fuellstand;
    private RoboterStatus Status;
    private Roboterleitsystem roboterleitsystem;

    private boolean GoTo(RelativePosition RelativePos) {
        return false;

    }

    private boolean Greife() {
        return false;

    }

    private boolean Schneide() {
        return false;

    }

    private boolean Lade_Auf() {
        return false;

    }

    private RelativePosition GetPosition() {
        return null;

    }

    private Pflanzenverwaltung pflanzeAnalysieren() {
        return null;

    }

    private double getGewicht() {
        return Batteriestatus;

    }

    private void setFuellstand() {

    }

    private void AddStoerung(Stoerung stoerung) {

    }

    public void setAuftrag(Unterauftrag unterauftrag) {

    }

    public RoboterStatus GetStatus() {
        return Status;

    }

    private void ErledigeUnterauftrag() {

    }

    private void BerechneWeg(Position position) {

    }

    private void NeuerAuftrag(Unterauftrag unterauftrag) {

    }
}
