package lpsw.bibliothek;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lpsw.gui.MyWebException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 29.11.2020
 * created on: 24.11.2020 Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Ermöglicht es einen Zettelkasten zu erstellen und Medien einzufügen, sowie zu löschen.
 * </p>
 */
public class Zettelkasten implements Iterable<Medium>, Serializable {

  private final ArrayList<Medium> myZettelkasten = new ArrayList<>();

  /**
   * Fügt ein Medium zum Zettelkasten hinzu.
   * @param _medium hinzuzufügendes Medium
   */
  public void addMedium(Medium _medium) {
    if (_medium == null) {
      throw new IllegalArgumentException("Titel darf nicht leer sein!");
    }
      myZettelkasten.add(_medium);
  }

  /**
   * Methode zum suchen, sowie parsen eines Wikibuchs
   * @param _title zu suchender Titel des Wikibuchs
   * @return erstelltes Wikibuch
   */
  public WikiBook findWikiBuch(String _title) {
    WikiBook newWikiBook = null;
    StringBuilder urlString = new StringBuilder("https://de.wikibooks.org/wiki/Spezial:Exportieren/");
    urlString.append(_title.replace(" ", "_"));
    try {
      URL url = new URL(urlString.toString());
      URLConnection connection = url.openConnection();
      connection.setDoInput(true);
      InputStream inStream = connection.getInputStream();

      // XMLReader erzeugen
      XMLReader xmlReader = XMLReaderFactory.createXMLReader();

      InputSource inputSource = new InputSource(inStream);
      WikibookContentHandler wikiBookHandler = new WikibookContentHandler(url.toString());

      // PersonenContentHandler wird übergeben
      xmlReader.setContentHandler(wikiBookHandler);

      // Parsen wird gestartet
      xmlReader.parse(inputSource);
      newWikiBook = wikiBookHandler.getWikiBook();
      if (newWikiBook != null) {
        newWikiBook.setUrl(urlString.toString());
      }
    } catch (IOException | SAXException e) {
      e.printStackTrace();
      MyWebException exception = new MyWebException();
      exception.printAlert();
    }
    return newWikiBook;
  }

  /**
   * Löscht ein Medium aus dem Zettelkasten.
   * @param _titel Titel, dessen Medium gelöscht werden soll
   * @return Wahrheitswert, ob Medium erfolgreich gelöscht wurde
   */
  public Boolean dropMedium(String _titel) {
    if (_titel == null || _titel.trim().equals("")) {
      throw new IllegalArgumentException("Titel darf nicht leer sein!");
    }
    Zettelkasten tmpZk = findMedium(_titel);
    if (!tmpZk.isEmpty()) {
      for (Medium tmpMedium : tmpZk) {
        myZettelkasten.removeIf(medium -> medium == tmpMedium);
      }
      return true;
    } else {
      System.out.println("Titel konnte nicht gefunden werden.");
      return false;
    }
  }

  /**
   * Sucht ein Medium/ Medien im Zettelkasten und gibt diese(s) zurück.
   * @param _titel Titel, des/der zu suchenden Mediums / Medien
   * @return gefundene(s) Medien/Medium
   */
  public Zettelkasten findMedium(String _titel) {
    if (_titel == null || _titel.trim().equals("")) {
      throw new IllegalArgumentException("Titel darf nicht leer sein!");
    }
    Zettelkasten tmpZk = new Zettelkasten();
    for (Medium medium : myZettelkasten) {
      if (medium.getTitel().equals(_titel)) {
        tmpZk.addMedium(medium);
      }
    }
    return tmpZk;
  }

  /**
   * Sortiert einen Zettelkasten auf- oder absteigend
   * @param _s angabe, ob Zettelkasten auf- oder absteigend sortiert werden soll
   */
  public void sort(String _s) {
    switch (_s) {
      case "AZ" -> myZettelkasten.sort(Medium::compareTo);
      case "ZA" -> myZettelkasten.sort(Collections.reverseOrder());
      default -> throw new IllegalArgumentException(
          "Es muss entweder 'AZ' zum absteigend- oder 'ZA' "
              + "zum aufsteigend sortieren übergeben werden!");
    }
  }

  /**
   * Methode zum prüfen, ob Zettelkasten leer ist
   * @return Wahrheitswert, ob Zettelkasten leer ist
   */
  public boolean isEmpty() {
    for (Medium ignored : this) {
      return false;
    }
    return true;
  }

  /**
   * Iterator, um durch den Zettelkasten iterieren zu können. Die Methode remove() wird explizit
   * nicht mit implementiert, um zu Gewährleisten, dass fremder Code den Zettelkasten nicht
   * verändern kann.
   */
  @Override
  public Iterator<Medium> iterator() {
    return new Iterator<>() {
      private int currentIndex = 0;

      @Override
      public boolean hasNext() {
        return currentIndex < myZettelkasten.size() && myZettelkasten.get(currentIndex) != null;
      }

      @Override
      public Medium next() {
        return myZettelkasten.get(currentIndex++);
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  /**
   * Methode, die einen Bibtex-Input in ein Medium umwandelt und dieses zurückgibt.
   *
   * @param _input Bibtex-Input
   * @return Medium mit gefüllten Attributen
   */
  public Medium parseBibTex(String _input) {
    if (_input == null || _input.equals("")) {
      throw new IllegalArgumentException("Die Eingabe darf nicht leer sein!");
    }
    Medium tmpMedium = null;
    try {
      String[] attributes = getAttributes(_input);
      tmpMedium = switch (attributes[0]) {
        case "book" -> new Buch(attributes[1], attributes[2], attributes[3],
            Integer.parseInt(attributes[4]), attributes[5]);
        case "journal" -> new Zeitschrift(attributes[1], attributes[2],
            Integer.parseInt(attributes[3]), Integer.parseInt(attributes[4]));
        case "cd" -> new CD(attributes[1], attributes[2], attributes[3]);
        case "elMed" -> new ElektronischesMedium(attributes[1], attributes[2]);
        case "wikiBook" -> new WikiBook(attributes[1], attributes[2]);
        default -> null;
      };
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    return tmpMedium;
  }

  /**
   * Hilfs-Methode zum Prüfen, ob der übergebene BibTex-Input einem gültigen Format entspricht.
   *
   * @param _input  BibTex-Input
   * @param _medium Zu überprüfendes Medium
   * @return true, wenn die Eingabe dem gewünschten Format entspricht; false, wenn nicht
   */
  private boolean checkPattern(String _input, String _medium) {
    Pattern pattern = switch (_medium) {
      case "book" -> Pattern.compile(
          "@book[{]author = [{].*[}], title = [{].*[}], publisher = [{].*[}], year = [0-9]*, isbn = [{][0-9-]*[}][}]");
      case "journal" -> Pattern.compile(
          "@journal[{]title = [{].*[}], issn = [{].*[}], volume = [0-9]*, number = [0-9]*[}]");
      case "cd" -> Pattern.compile(
          "@cd[{]title = [{].*[}], artist = [{].*[}], label = [{].*[}][}]");
      case "elMed" -> Pattern.compile(
          "@elMed[{]title = [{].*[}], URL = [{].*[}][}]");
      case "wikiBook" -> Pattern.compile(
          "@wikiBook[{]title = [{].*[}], URL = [{].*[}][}]");
      default -> null;
    };
    if (pattern == null) {
      return false;
    }
    Matcher matcher = pattern.matcher(_input);
    return matcher.matches();
  }

  /**
   * Methode, die aus einem übergebenen BibTex-Input die Attribute des Mediums rausfiltert und in
   * einem String-Array zurückgibt.
   *
   * @param _input BibTex-Input eines Mediums
   * @return String-Array mit den Attributen des Mediums
   */
  private String[] getAttributes(String _input) {
    //StringBuilder mit Namen des Mediums initialisieren (z.B. book)
    StringBuilder strMedium = new StringBuilder(
        _input.substring(_input.indexOf("@") + 1, _input.indexOf("{")));
    if (!checkPattern(_input, strMedium.toString())) {
      throw new IllegalArgumentException("Das Eingabeformat ist ungültig!");
    }
    //Char-Array aus dem Bibtex-Input erstellen
    char[] charArr = _input.toCharArray();
    boolean isAttributeNum = false, isAttribute = false;
    strMedium.append(';');
    /*In einer Schleife durch das Char-Array iterieren und den StringBuilder, mit durch ';'
    getrennten Attributen befuellen*/
    for (int i = 0; i < charArr.length; i++) {
      //Wenn '=', dann folgt ein Attribut
      if (charArr[i] == '=') {
        /*Anhand der Formatierungsvorgaben pruefen ob es sich um ein 'Zeichenliterales-Attribut'
        oder ein 'numerisches-Attribut' handelt*/
        if (charArr[i + 2] == '{') {
          isAttribute = true;
        } else {
          isAttributeNum = true;
        }
      }
      //Ende des Attributs erkennen
      if ((isAttribute && charArr[i] == '}') || (isAttributeNum && charArr[i] == ',')) {
        //Alle bis auf das letzte Attribut mit ';' trennen
        if (charArr[i + 1] != '}') {
          strMedium.append(';');
        }
        isAttribute = false;
        isAttributeNum = false;
      }
      //Zeichen des Attributs an den StringBuilder anhaengen
      if ((isAttribute || isAttributeNum) && "{=} ".indexOf(charArr[i]) == -1) {
        strMedium.append(charArr[i]);
      }
    }
    return strMedium.toString().split(";");
  }
}
