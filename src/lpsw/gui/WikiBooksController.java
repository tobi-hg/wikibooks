package lpsw.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 24.01.2021
 * created on: 12.01.2021 Environment: IntelliJ, JDK 15, MacOS BigSur
 * <p>
 * Diese Klasse ist zum Verwalten der GUI-Applikation zuständig.
 * </p>
 */
public class WikiBooksController {

  @FXML
  private ComboBox<String> searchHistoryBox;
  @FXML
  private Button btnSearchSynonyms;
  @FXML
  private Button btnForward;
  @FXML
  private Button btnBackward;
  @FXML
  private Button btnDelete;
  @FXML
  private ListView<String> titleslistView;
  @FXML
  private ListView<String> synonymsListView;
  @FXML
  private WebView browser;
  @FXML
  private TextField tfSearchTermf;
  @FXML
  private Label contributor;
  @FXML
  private Label lastChange;
  private SearchHistory searchHistory;

  /**
   * Methode zum Initiliesieren der Default-Seite
   */
  @FXML
  private void initialize() {
    WebEngine engine = browser.getEngine();
    engine.load("http://de.wikibooks.org");
    searchHistory = new SearchHistory();
    disableAll();
    printTitles();
    printContributorInformation("Hauptseite");
  }

  /**
   * Disabled beim initialisieren alle nicht benoetigten Objekte
   */
  private void disableAll() {
    btnDelete.setDisable(true);
    searchHistoryBox.setDisable(true);
    btnForward.setDisable(true);
    btnBackward.setDisable(true);
    disableSynonymsButtons(true);
  }

  /**
   * Methode zum Laden einer neuen Seite des Webservers
   * @param _searchTerm zu suchender Begriff
   */
  private void navigateBrowser(String _searchTerm) {
    if (_searchTerm != null && !_searchTerm.trim().equals("")) {
      WebEngine engine = browser.getEngine();
      engine.load(WikiBooks.getUrl(_searchTerm));
      printSynonyms();
      printContributorInformation(_searchTerm);
    } else {
      WebEngine engine = browser.getEngine();
      engine.load(WikiBooks.getUrl("Hauptseite"));
    }
  }

  /* ----------------------------------- Eingabefeld - Suche ----------------------------------- */

  /**
   * Button zum Suchen eines eingegebenen Begriffs
   */
  @FXML
  private void btnSearch() {
    navigateBrowser(tfSearchTermf.getText());
    searchHistory.add(tfSearchTermf.getText());
    printSearchHistory();
  }

  /**
   * Textfeld zum Suchen eines eingegebenen Begriffs
   */
  @FXML
  private void tfSearchTermf() {
    navigateBrowser(tfSearchTermf.getText());
    searchHistory.add(tfSearchTermf.getText());
    printSearchHistory();
  }


  /**
   * Zeigt die Informationen des Verfassers und der Letzten Bearbeitung an
   * @param _searchTerm Suchbegriff, dessen Verfasser und letzte Bearbeitung angezeigt werden sollen
   */
  private void printContributorInformation(String _searchTerm) {
    contributor.setText("Letzter Bearbeiter: " + WikiBooks.getContributor(_searchTerm));
    lastChange.setText("Letztes Änderungsdatum: " + WikiBooks.getLastChange(_searchTerm));
  }

  /* ---------------------------------------- Medien ---------------------------------------- */

  /**
   * Zeigt alle Titel der im Zettelkasten vorhandenen Medien an
   */
  private void printTitles() {
    ObservableList<String> observableList = FXCollections.observableArrayList();
    observableList.addAll(WikiBooks.getTitles());
    titleslistView.getItems().clear();
    if (observableList.isEmpty()) {
      titleslistView.getItems().add("<keine>");
      titleslistView.setDisable(true);
    } else {
      titleslistView.setDisable(false);
      titleslistView.getItems().addAll(observableList);
    }
  }

  /**
   * Fügt einen neuen Titel, zusätzlich zu den bereits vorhandenen Titeln hinzu
   */
  private void printTitle() {
    if (titleslistView.getItems().contains("<keine>")) {
      titleslistView.setDisable(false);
      titleslistView.getItems().clear();
    }
    titleslistView.getItems().add(tfSearchTermf.getText());
  }

  /* ---------------------------------------- Synonyme ---------------------------------------- */

  /**
   * Zeigt alle gefundenen Synonyme zu einem Suchbegriff an.
   */
  private void printSynonyms() {
    ObservableList<String> observableList = FXCollections.observableArrayList();
    observableList.addAll(Synonym.getSynonyms(tfSearchTermf.getText()));
    synonymsListView.getItems().clear();
    // Wenn keine Synonyme gefunden wurden -> "<keine>" ausgeben
    // Wenn Synonyme gefunden -> Liste der Synonyme ausgeben
    if (observableList.isEmpty()) {
      synonymsListView.getItems().add("<keine>");
      // Buttons deaktivieren
      disableSynonymsButtons(true);
    } else {
      synonymsListView.getItems().addAll(observableList);
      // EventListener: Doppelklick
      synonymsListView.setOnMouseClicked(_mouseEvent -> {
        if (_mouseEvent.getClickCount() == 2) {
          btnSearchSynonym();
        }
      });
      // EventListener: ENTER
      synonymsListView.setOnKeyPressed(_keyEvent -> {
        if (_keyEvent.getCode() == KeyCode.ENTER) {
          btnSearchSynonym();
        }
      });
      // Buttons aktivieren
      disableSynonymsButtons(false);
    }
  }

  /**
   * Button zum Suchen der Synonyme zu einem Suchbegriff
   */
  @FXML
  private void btnSearchSynonym() {
    String selected = synonymsListView.getSelectionModel().getSelectedItem();
    if (selected != null && !selected.equals("")) {
      tfSearchTermf.setText(selected);
      navigateBrowser(tfSearchTermf.getText());
      searchHistory.add(selected);
      printSearchHistory();
    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setHeaderText("Fehler beim Zugriff auf den Wortschatzserver.");
      alert.showAndWait();
    }
  }

  /**
   * Schaltet den Button zur Synonymssuche, sowie die Synonym ListView an/aus
   * @param _b Wahrheitswert, ob Objekte aus/an geschaltet werden sollen
   */
  private void disableSynonymsButtons(boolean _b) {
    synonymsListView.setDisable(_b);
    btnSearchSynonyms.setDisable(_b);
  }

  /* --------------------------------------- Suchverlauf --------------------------------------- */

  /**
   * Zeigt den Suchverlauf innerhalb der ComboBox an
   */
  private void printSearchHistory() {
    if (searchHistory.isEmpty()) {
      searchHistoryBox.setDisable(true);
    } else {
      // ComboBox "anschalten", sowie Vor-/Zurueck Buttons entsprechend des aktuellen indexes an/ausschalten
      searchHistoryBox.setDisable(false);
      handleScrollingOptions();
      // Combobox leeren und mit 'Neueste...Letzte' Reihenfolge der Suchbegriffe fuellen
      searchHistoryBox.getItems().clear();
      searchHistory.reverseOrder();
      searchHistoryBox.getItems().addAll(searchHistory.getSearchHistory());
      searchHistory.reverseOrder();
      // Aktuellen Suchbegriff selektieren
      searchHistoryBox.getSelectionModel().select(searchHistory.getCurIndex());

      // EventListener zum selektieren von Suchbegriffen aus der Liste
      searchHistoryBox.getSelectionModel().selectedItemProperty()
          .addListener((options, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(searchHistory.getCurIndex())) {
              int idx = searchHistory.getIndexOfElement(newValue);
              searchHistory.setCurIndex(idx);
              tfSearchTermf.setText(newValue);
              navigateBrowser(newValue);
              printSearchHistory();
            }
          });
    }
  }

  /**
   * Button um einen Suchbegriff nach vorne zu 'scrollen'
   */
  @FXML
  private void btnForward() {
    if (searchHistory.hasNext()) {
      searchHistory.scrollForward();
      tfSearchTermf.setText(searchHistory.getCurIndex());
      navigateBrowser(tfSearchTermf.getText());
      printSearchHistory();
      handleScrollingOptions();
    } else {
      btnForward.setDisable(true);
    }
  }

  /**
   * Button um einen Suchbegriff zurueck zu 'scrollen'
   */
  @FXML
  private void btnBackward() {
    if (searchHistory.hasPrevious()) {
      searchHistory.scrollBackward();
      tfSearchTermf.setText(searchHistory.getCurIndex());
      navigateBrowser(tfSearchTermf.getText());
      printSearchHistory();
      handleScrollingOptions();
    } else {
      btnBackward.setDisable(true);
    }
  }

  /**
   * Schaltet die Buttons zum Vor/Zurueck-Scrollen an/aus
   */
  private void handleScrollingOptions() {
    btnBackward.setDisable(!searchHistory.hasPrevious());
    btnForward.setDisable(!searchHistory.hasNext());
  }

  /* ------------------------ Hinzufuegen - Sortieren - Sichern - Laden ------------------------ */

  /**
   * Button zum hinzufuegen eines WikiBooks-Eintrags zum Zettelkasten.
   */
  @FXML
  private void btnAdd() {
    if (WikiBooks.addWikibook(tfSearchTermf.getText())) {
      printTitle();
      WikiBooksAlerts.printAddSuccesful();
    }
  }

  /**
   * Button zum Sortieren des Zettelkastens.
   */
  @FXML
  private void btnSort() {
    WikiBooks.sortWikibook();
    printTitles();
  }

  /**
   * Button zum Speichern der Zettelkastens in einer Datei.
   */
  @FXML
  private void btnSave() {
    if (WikiBooks.saveWikibook()) {
      WikiBooksAlerts.printSaveSuccesful();
    }
  }

  /**
   * Button zum Laden eines Zettelkastens aus einer Datei.
   */
  @FXML
  private void btnLoad() {
    if (WikiBooks.loadWikibook()) {
      printTitles();
      WikiBooksAlerts.printLoadSuccesful();
    }
  }

  /* ----------------------------------- Informationsausgabe ----------------------------------- */

  /**
   * Gibt die Informationen ueber dies Programm aus.
   */
  public void showInformations() {
    Alert alert = new Alert(AlertType.INFORMATION);
    Text informations = new Text("""
        Alle redaktionellen Inhalte stammen von den Internetseiten der Projekte Wikibooks und Wortschatz.

        Die von Wikibooks bezogenen Inhalte unterliegen seit dem 22. Juni 2009 unter der Lizenz CC-BY-SA 3.0 Unported zur Verfügung. 
        Eine deutschsprachige Dokumentation für Weiternutzer findet man in den Nutzungsbedingungen der Wikimedia Foundation. 
        Für alle Inhalte von Wikibooks galt bis zum 22. Juni 2009 standardmäßig die GNU FDL (GNU Free Documentation License, 
        engl. für GNU-Lizenz für freie Dokumentation). Der Text der GNU FDL ist unter 
        http://de.wikipedia.org/wiki/Wikipedia:GNU_Free_Documentation_License verfügbar.

        Die von Wortschatz (http://wortschatz.uni-leipzig.de/) bezogenen Inhalte sind urheberrechtlich geschützt. 
        Sie werden hier für wissenschaftliche Zwecke eingesetzt und dürfen darüber hinaus in keiner Weise genutzt werden.

        Dieses Programm ist nur zur Nutzung durch den Programmierer selbst gedacht. Dieses Programm dient der Demonstration 
        und dem Erlernen von Prinzipien der Programmierung mit Java. Eine Verwendung des Programms für andere Zwecke verletzt 
        möglicherweise die Urheberrechte der Originalautoren der redaktionellen Inhalte und ist daher untersagt.
        """);
    informations.setWrappingWidth(500);
    alert.getDialogPane().setHeader(informations);
    alert.showAndWait();
  }
}
