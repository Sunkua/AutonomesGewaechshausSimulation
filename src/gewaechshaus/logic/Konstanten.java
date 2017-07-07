package gewaechshaus.logic;

public class Konstanten {
	// TODO: bring this into a config file:
    public static double AkkuEntladungNormal = -0.001;
    public static double AkkuEntladungBeschäftigt = -0.2;
	public static double AkkuAufladung = 0.5;
    public static double roboterSchrittweite = 0.5f;
    public static int beetBreite = 5;
    public static int maximalerFuellstand = 2;
    public static double GewichtGurke = 0.3;
    public static double GewichtTomate = 0.2;
    public static double WachstumGurke = 0.1; // % pro Clock-Tick
    public static double WachstumTomate = 0.1;// % pro Clock-Tick
}
