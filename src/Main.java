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
	
	public void saveStateToFile(String filename, Bedienterminal bedienTerminal) throws JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(Bedienterminal.class);
		Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(bedienTerminal, new File(filename));
	}
	
	public Bedienterminal readStateFromFile(String filename) throws IOException, JAXBException
	{
		JAXBContext context = JAXBContext.newInstance(Bedienterminal.class);
		Unmarshaller m = context.createUnmarshaller();
		Bedienterminal bedienterminal = (Bedienterminal) m.unmarshal(new FileReader(filename));
		return bedienterminal;
	}

}
