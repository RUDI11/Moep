
package MoepClient;

import Moep.Karte;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Die Spielverwaltung auf Clientseite, vor allem Basis für die GUI
 * @author Markus Klar & Christian Diller
 */

public class Moep
{
    private List<Karte> hand;
    private String[][] spieler;
    private String spielerAmZug;
    private ArrayList<String> nachrichten;
    private int zuLegenIndex;
    
    public Moep()
    {
        hand = Collections.synchronizedList(new ArrayList<Karte>());
        spieler = new String[4][2];
        nachrichten = new ArrayList<String>();
    }
    
    public Moep(ArrayList<Karte> h)
    {
        hand = Collections.synchronizedList(h);
        spieler = new String[4][2];
        nachrichten = new ArrayList<String>();
        Collections.sort(hand);
    }
    
    public void ziehen(Karte karte)
    {
        hand.add(karte);
        Collections.sort(hand);
    }
    
    public void legen()
    {
        hand.remove(zuLegenIndex);
        Collections.sort(hand);   
    }
        
    public void zuLegen(int index)
    {
        zuLegenIndex = index;
    }
    
    public List<Karte> gibHand()
    {
        return hand;
    }
    
    public void setzeHand(ArrayList<Karte> h)
    {
        hand = h;
    }
    
    public Karte gibKarteAt (int indexKarte){
        return hand.get(indexKarte);
    }
    
    public void mitspielerLogin(String name)
    {
        for(int i = 0; i < 4; i++)
            if(spieler[i][0] == null) {
                spieler[i][0] = name;
                return;
            }
    }
    
    public void mitspielerLogout(String name)
    {
        for(int i = 0; i < 4; i++)
            if(spieler[i][0] == name) {
                spieler[i][0] = null;
                spieler[i][1] = null;
            }
    }
    
    public String gibSpielerliste()
    {
        if(keineSpieler())
            return "";
        String ausgabe = "<html><body><center><h1>Spielerliste</h1><br/><h2>";
        for(int i = 0; i < 4; i++)
        {
            if(spieler[i][0] != null && spieler[i][0].equals(spielerAmZug))
                ausgabe += ("<i>" + spieler[i][0]  + " (" + spieler[i][1] + " Karten)" + "</i>" + "<br/><br/>");
            else
                ausgabe += (spieler[i][0]  + " (" + spieler[i][1] + " Karten)" + "<br/><br/>");
        }
        ausgabe = ausgabe + "<h2></center></body></html>";
        return umlautFix(ausgabe);
    }
    
    public void addNachricht(String status)
    {
        nachrichten.add(status);
        if(nachrichten.size() > 5)
            nachrichten.remove(0);
    }
    
    public String gibStatus()
    {
        if(nachrichten.isEmpty())
            return "";
        String ausgabe = "<html><body>";
        for(String n : nachrichten)
        {
            ausgabe += (n + "<br/>");
        }
        ausgabe = ausgabe + "</body></html>";
        return umlautFix(ausgabe);
    }
    
    private String umlautFix(String input)
    {
        input.replaceAll("ä", "&auml;");
        input.replaceAll("ö", "&ouml;");
        input.replaceAll("ü", "&uuml;");
        input.replaceAll("ß", "&szlig;");
        return input;
    }

    public void statusReset() {
        nachrichten.clear();
        for(int i = 0; i < 4; i++) {
            spieler[i][0] = null;
            spieler[i][1] = null;
        }
    }

    public void mitspielerAmZug(String spielername) {
        spielerAmZug = spielername;
        for(int i = 0; i < 4; i++)
            if(spieler[i][0] == spielername) {
            }
    }

    private boolean keineSpieler() {
        for(int i = 0; i < 4; i++)
            if(spieler[i][0] != null)
                return false;
        return true;
    }

    public void mitspielerKartenzahlUpdate(String spielername, int kartenzahl) {
        for(int i = 0; i < 4; i++)
            if(spieler[i][0] == spielername) {
                spieler[i][1] = kartenzahl+"";
                return;
            }
    }
}
