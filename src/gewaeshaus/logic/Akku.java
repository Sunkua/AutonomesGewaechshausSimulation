package gewaeshaus.logic;

public class Akku {
    private double Ladestand;
    private double kritischeGrenze;


    public Akku(double Ladestand, double kritGrenze) {
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
