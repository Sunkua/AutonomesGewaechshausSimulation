package gewaechshaus;

import gewaechshaus.gui.GUI;
import gewaechshaus.logic.Bedienterminal;
import gewaechshaus.logic.Pflanzenverwaltung;
import gewaechshaus.logic.Roboterleitsystem;
import gewaechshaus.logic.Settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Main {
	
	private static final Logger log = Logger.getLogger( Main.class.getName() );

	public static void main(String[] args) throws JAXBException, IOException {
		Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
		// TODO Auto-generated method stub
		Pflanzenverwaltung pVerwaltung = new Pflanzenverwaltung();
		pVerwaltung.setMaxGröße(6, 20);
		Roboterleitsystem leitSystem = new Roboterleitsystem();
		GUI gui = new GUI(pVerwaltung);
		pVerwaltung.setGui(gui);
		
		
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
