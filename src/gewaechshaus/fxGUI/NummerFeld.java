package gewaechshaus.fxGUI;

import javafx.scene.control.TextField;

/**
 * Klasse zur Realisierung eines Feldes mit ausschließlicher Nummerneingabe
 */
public class NummerFeld extends TextField {

    /**
     * Ersetzt einen Text mit Nummern oder Leertext
     *
     * @param start Anfang für die Ersetzung, muss größer als Null sein
     * @param end   Ende für die Ersetzung
     * @param text  Text, der in dem Bereich ersetzt werden soll
     */
    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]") || text.equals("")) {
            super.replaceText(start, end, text);
        }
    }

    /**
     * Ersetzt einen Text durch den gegebenen String, falls er Nummern oder
     * Leertext enthält.
     *
     * @param text Text, durch welchen Ersetzt werden soll
     */
    @Override
    public void replaceSelection(String text) {
        if (text.matches("[0-9]") || text.equals("")) {
            super.replaceSelection(text);
        }
    }
}
