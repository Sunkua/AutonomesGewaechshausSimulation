package gewaechshaus.logic;

import java.util.Date;

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

	}
	
	public Einzelpflanze(PflanzenArt art, Position p, double gewicht, PflanzenStatus reifegrad, Date reifezeit) {
		this.art = art;
		this.position = p;
		this.gewicht = gewicht;
		this.reifegrad = reifegrad;
		this.reifezeit = reifezeit;
	}

	public double getGewicht() {
		return gewicht;
	}
	
	public void setGewicht(double gewicht)
	{
		this.gewicht = gewicht;
	}
	
	public PflanzenStatus getPflanzenstatus()
	{
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
	
	
	
	
	

}
