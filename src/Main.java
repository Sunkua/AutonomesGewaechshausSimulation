import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import Gewaechshaus.gui.GUI;

import java.util.logging.*;

import gewaeshaus.logic.Bedienterminal;
import gewaeshaus.logic.Pflanzenverwaltung;
import gewaeshaus.logic.Roboterleitsystem;
import gewaeshaus.logic.Settings;

public class Main {
	
	private static final Logger log = Logger.getLogger( Main.class.getName() );

	public static void main(String[] args) throws JAXBException, IOException {
		Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
		// TODO Auto-generated method stub
		Pflanzenverwaltung pVerwaltung = new Pflanzenverwaltung();
		Roboterleitsystem leitSystem = new Roboterleitsystem();
		GUI gui = new GUI();
		
		
	}
	
	public void pflanzenverwaltungZustandInDateiSpeichern(String filename, Pflanzenverwaltung pflanzenverwaltung) throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(Pflanzenverwaltung.class);
		Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(pflanzenverwaltung, new File(filename));
	}
	
	public void leitsystemZustandInDateiSpeichern(String filename, Roboterleitsystem roboterLeitsystem) throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(Roboterleitsystem.class);
		Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(roboterLeitsystem, new File(filename));
	}
	
	public Roboterleitsystem leitsystemZustandAusDateiLesen(String filename) throws IOException, JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(Bedienterminal.class);
		Unmarshaller m = context.createUnmarshaller();
		Roboterleitsystem leitsystem = (Roboterleitsystem) m.unmarshal(new FileReader(filename));
		return leitsystem;
	}
	public Pflanzenverwaltung pflanzenVerwaltungZustandAusDateiLesen(String filename) throws IOException, JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(Bedienterminal.class);
		Unmarshaller m = context.createUnmarshaller();
		Pflanzenverwaltung pflanzenverwaltung = (Pflanzenverwaltung) m.unmarshal(new FileReader(filename));
		return pflanzenverwaltung;
	}
	
	

}
