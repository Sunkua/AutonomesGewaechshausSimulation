package gewaeshaus.logic;
import java.rmi.UnexpectedException;
import java.util.List;

public class Roboterleitsystem {

private List<Roboter> roboterListe;
private List<Auftrag> auftragsListe;
private Abladestation abladestation;
private Ladestation ladestation;
private Abladestation abladestation2;


public Roboterleitsystem()
{

}

public boolean operationAbbrechen(int ID)
{

    return true;
}

public void abladeStationDefinieren()
{

}

public Unterauftrag getUnterauftrag(int ID)
{
	return null;


}


public void setRoboterStatus(Roboter roboter, RoboterStatus status)
{

}


public int neuerAuftrag(Auftrag auftrag)
{
	return 0;

}


private void warte()
{

}

private void sendeMeldung()
{

}


public void addAuftrag(Auftrag auftrag)
{

}








}
