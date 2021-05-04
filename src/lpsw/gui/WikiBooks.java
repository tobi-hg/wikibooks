package lpsw.gui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import lpsw.bibliothek.BinaryPersistency;
import lpsw.bibliothek.Medium;
import lpsw.bibliothek.Persistency;
import lpsw.bibliothek.WikiBook;
import lpsw.bibliothek.Zettelkasten;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 24.01.2021
 * created on: 12.01.2021 Environment: IntelliJ, JDK 15, MacOS BigSur
 * <p>
 * Diese Klasse ist zum Verwalten der WikiBooks zuständig.
 * </p>
 */
public class WikiBooks {

  private static Zettelkasten zk = new Zettelkasten();
  private static boolean sortUp = true;

  /**
   * Methode, die die Wiki-URL des gesuchten Elements zurückgibt.
   * @param _searchTerm gesuchtes Element
   * @return Wiki-Url, des Elements
   */
  public static String getUrl(String _searchTerm) {
    return "http://de.wikibooks.org/wiki/" + _searchTerm;
  }

  /**
   * Methode zum hinzufügen eines Wikibooks zum Zettelkasten.
   * @param _searchTerm hinzuzufügender Titel des Wikibooks
   * @return Wahrheitswert, ob WikiBook erfolgreich hinzugefügt werden konnte
   */
  public static boolean addWikibook(String _searchTerm) {
    if (_searchTerm == null || _searchTerm.trim().equals("")) {
      WikiBooksAlerts.printNoInputAlert();
    } else {
      WikiBook wikiBook = zk.findWikiBuch(_searchTerm);
      if (wikiBook != null) {
        zk.addMedium(wikiBook);
        return true;
      } else {
        MyWebException exception = new MyWebException();
        exception.printAlert();
      }
    }
    return false;
  }

  /**
   * Methode zum Sortieren des Zettelkasten nach Titel
   */
  public static void sortWikibook() {
    if (zk.isEmpty()) {
      WikiBooksAlerts.printEmptyAlert();
    } else {
      // "A-Z"-, "Z-A"-Sortierung alternieren
      if (sortUp) {
       zk.sort("AZ");
        sortUp = false;
      } else {
        zk.sort("ZA");
        sortUp = true;
      }
    }
  }

  /**
   * Speichert einen Zettelkasten in einer Datei.
   */
  public static boolean saveWikibook() {
    if (zk.isEmpty()) {
      WikiBooksAlerts.printEmptyAlert();
    } else {
      Persistency binaryPersistency = new BinaryPersistency();
      binaryPersistency.save(zk, "Zettelkasten.txt");
      return true;
    }
    return false;
  }

  /**
   * Liest den gespeicherten Zettelkasten (falls vorhanden) wieder ein.
   */
  public static boolean loadWikibook() {
      Persistency binaryPersistency = new BinaryPersistency();
      File f = new File("Zettelkasten.txt");
      if (f.exists()) {
        zk = binaryPersistency.load("Zettelkasten.txt");
        return true;
      } else {
        WikiBooksAlerts.printFileDoesNotExist();
      }
      return false;
  }

  /**
   * Methode zum holen der Informationen zum Verfasser des WikiBooks
   * @param _searchTerm WikiBook, dessen Verfasser gesucht werden soll
   * @return Verfasser des WikiBooks
   */
  public static String getContributor(String _searchTerm) {
    WikiBook wikiBook = zk.findWikiBuch(_searchTerm);
    // Wenn WikiBook nicht gefunden werden konnte, wird Verfasser der Wikibooks-Hauptseite zurückgegeben
    if (wikiBook == null) {
      return zk.findWikiBuch("Hauptseite").getVerfasser();
    }
    return wikiBook.getVerfasser();
  }

  /**
   * Methode zum holen des letzten Bearbeitungsdatums eines WikiBooks
   * @param _searchTerm WikiBook, dessen letzte Bearbeitung herausgefunden werden soll
   * @return Datum der Letzten Bearbeitung
   */
  public static String getLastChange(String _searchTerm) {
    SimpleDateFormat datumsformat = new SimpleDateFormat("dd.MM.yyyy 'um' HH:mm:ss");
    WikiBook wikiBook = zk.findWikiBuch(_searchTerm);
    if (wikiBook == null) {
      return datumsformat.format(zk.findWikiBuch("Hauptseite").getLetzteBearbeitung());
    }
    return datumsformat.format(wikiBook.getLetzteBearbeitung());
  }

  /**
   * Getter-Methode für alle im Zettelkasten gespeicherten Titel.
   * @return alle im Zettellkasten enthaltenen Titel
   */
  public static ArrayList<String> getTitles() {
    ArrayList<String> titles = new ArrayList<>();
    for (Medium m : zk) {
      titles.add(m.getTitel());
    }
    return titles;
  }
}
