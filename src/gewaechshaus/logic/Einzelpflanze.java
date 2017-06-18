package gewaechshaus.logic;

import java.util.Date;
import java.util.logging.Level;

/**
 * Definiert eine einzelne Pflanze und ihre Parameter
 */
public class Einzelpflanze {
	private double gewicht;
	private Date reifezeit;
	private PflanzenStatus reifegrad;
	private Position position;
	private PflanzenArt art;

	public Einzelpflanze() {

		 Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
	}
	
	public Einzelpflanze(PflanzenArt art, Position p, double gewicht, PflanzenStatus reifegrad, Date reifezeit) {
		this.art = art;
		this.position = p;
		this.gewicht = gewicht;
		this.reifegrad = reifegrad;
		this.reifezeit = reifezeit;
		
		 Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
	}

	public double getGewicht() {
		return gewicht;
	}
	public void setGewicht(double gewicht) {
		this.gewicht = gewicht;
	}
	
	public Date getReifezeit() {
		return reifezeit;
	}
	public void setReifezeit(Date reifezeit) {
		this.reifezeit = reifezeit;
	}
	
	public PflanzenStatus getPflanzenstatus() {
		return reifegrad;
	}
	public void setPflanzenstatus(PflanzenStatus stat) {
		this.reifegrad = stat;
	}
	
	public void setPflanzenart(PflanzenArt art) {
		this.art = art;
	}
	
	public void setPosition(Position p) {
		this.position = p;
	}
	public Position getPosition() {
		return position;
	}

	public PflanzenArt getArt() {
		return art;
	}
	public void setArt(PflanzenArt art) {
		this.art = art;
	}
	
	/**
	 * Gibt die Informationen der Pflanze in lesbarer Form zurück
	 * @return String Gibt es in der Form "Art: <> Reifegrad <> Position: <>" zurück
	 */
	public String toString() {
		return "Art: "+this.getArt().toString()+" Reifegrad: "+this.getPflanzenstatus().toString()+" Position: "+this.getPosition().toString();
	}
}
