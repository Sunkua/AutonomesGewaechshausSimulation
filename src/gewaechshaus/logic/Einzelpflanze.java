package gewaechshaus.logic;

import java.util.Date;
import java.util.logging.Level;

/**
 * Definiert eine einzelne Pflanze und ihre Parameter
 */
public class Einzelpflanze {
	private double gewicht;
	private double reifestatus; // status in 0..100%
	private PflanzenStatus pflanzenStatus; // Status den die Verwalung kennt. zb durch eingabe oder robot Scan
	private Position position;
	private PflanzenArt art;

	public Einzelpflanze() {

		 Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
	}
	
	public Einzelpflanze(PflanzenArt art, Position p, double gewicht, PflanzenStatus pflanzenStatus) {
		this.art = art;
		this.position = p;
		this.gewicht = gewicht;
		this.pflanzenStatus = pflanzenStatus;
		switch(pflanzenStatus){
		case eReif:
			reifestatus = 100.0;
			break;
		case eUnreif:
			reifestatus = 10.0;
			break;
		default:
			reifestatus = 0.0;
		}
		
		 Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
	}

	public double getGewicht() {
		return gewicht;
	}
	public void setGewicht(double gewicht) {
		this.gewicht = gewicht;
	}
	public double getReifestatus(){
		return reifestatus;
	}
	
	public double getReifezeit() {
		switch(art){
		case eTomate:
			return Konstanten.WachstumTomate;
		case eGurke:
			return Konstanten.WachstumGurke;
		}
		return 0; //ToDo Exception
	}
	
	public PflanzenStatus getPflanzenstatus() {
		return pflanzenStatus;
	}
	public void setPflanzenstatus(PflanzenStatus stat) {
		this.pflanzenStatus = stat;
		switch(pflanzenStatus){
		case eReif:
			reifestatus = 100.0;
			break;
		case eUnreif:
			reifestatus = 10.0;
			break;
		default:
			reifestatus = 0.0;
		}
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
	
	public void Wachse(){
		switch(art){
		case eTomate:
			reifestatus += Konstanten.WachstumTomate;
		case eGurke:
			reifestatus += Konstanten.WachstumGurke;
		}

		reifestatus = Math.max(Math.min(reifestatus, 100), 0);
	}
		
}
