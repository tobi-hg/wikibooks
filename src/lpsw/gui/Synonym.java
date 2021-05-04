package lpsw.gui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 24.01.2021
 * created on: 12.01.2021 Environment: IntelliJ, JDK 15, MacOS BigSur
 * <p>
 * Diese Klasse repräsentiert die Synonyme.
 * </p>
 */
public class Synonym {

  /**
   * Methode zum holen der synonyme eines Strings mittels des Wortschatz-Services
   * der Universität Leipzig.
   * @param _s String, dessen Synonyme gesucht werden sollen
   * @return Liste mit den gefundenen Synonymen, leere Liste, wenn keine gefunden
   */
  public static ArrayList<String> getSynonyms(String _s) {
    String BasisUrl = "http://api.corpora.uni-leipzig.de/ws/similarity/";
    String Corpus = "deu_news_2012_1M";
    String Anfragetyp = "/coocsim/";
    String Parameter = "?minSim=0.1&limit=50";
    URL myURL;
    String synonym;
    ArrayList<String> synonyms = new ArrayList<>();
    try {
      myURL = new URL(BasisUrl + Corpus + Anfragetyp + _s + Parameter);
      JSONParser jsonParser = new JSONParser();
      String jsonResponse = streamToString(myURL.openStream());
      // den gelieferten String in ein Array parsen
      JSONArray jsonResponseArr = (JSONArray) jsonParser
          .parse(jsonResponse); // das erste Element in diesem Array
      if (jsonResponseArr.size() != 0) {
        JSONObject firstEntry = (JSONObject) jsonResponseArr.get(0);
        // aus dem Element den Container für das Synonym beschaffen
        JSONObject wordContainer;

        for (Object el : jsonResponseArr) {
          wordContainer = (JSONObject) ((JSONObject) el).get("word");
          synonym = (String) wordContainer.get("word");
          synonyms.add(synonym);
        }
        // Nach Alphabet sortieren (Groß-/Klein-Schreibung wird ignoriert)
        synonyms.sort(String.CASE_INSENSITIVE_ORDER);
      }
    } catch (ParseException | IOException _e) {
      _e.printStackTrace();
      Alert alert = new Alert(AlertType.ERROR);
      alert.setHeaderText("Fehler beim Zugriff auf den Wortschatzserver.");
      alert.showAndWait();
    }
    return synonyms;
  }

  /**
   * Reads InputStream's contents into String *
   *
   * @param is - the InputStream
   * @return String representing is' contents * @throws IOException
   */
  public static String streamToString(InputStream is) throws IOException {
    String result;
    try (Scanner s = new Scanner(is)) {
      s.useDelimiter("\\A");
      result = s.hasNext() ? s.next() : "";
      is.close();
    }
    return result;
  }
}
