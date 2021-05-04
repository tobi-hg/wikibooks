package lpsw.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 24.01.2021
 * created on: 12.01.2021 Environment: IntelliJ, JDK 15, MacOS BigSur
 * <p>
 * Diese Klasse ist zum Ausgeben von Alerts, bei fehlerhaften Zugriffen auf WikiBooks
 * Funktionalitäten.
 * </p>
 */
public class WikiBooksAlerts {

  /**
   * Gibt einen Alert aus, wenn versucht wird einen leeren Zettelkasten zu verarbeiten.
   * (Speichern, Sortieren)
   */
  public static void printEmptyAlert() {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setHeaderText("Der Zettelkasten ist leer.");
    alert.showAndWait();
  }

  /**
   * Gibt einen Alert aus, wenn ein nicht existenter Zettelkasten eingelesen werden soll. (Load)
   */
  public static void printFileDoesNotExist() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText("Es wurde noch kein Zettelkasten gespeichert.");
    alert.showAndWait();
  }

  /**
   * Gibt einen Alert aus, wenn ein leerer Suchbegriff als WikiBook gespeichert werden soll. (Add)
   */
  public static void printNoInputAlert() {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setHeaderText("Es wurde noch kein Suchbegriff eingegeben.");
    alert.showAndWait();
  }

  /**
   * Gibt einen Alert aus, wenn ein WikiBook erfolgreich hinzugefügt wurde. (Add)
   */
  public static void printAddSuccesful() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setHeaderText("Das Medium wurde erfolgreich hinzugefügt.");
    alert.showAndWait();
  }

  /**
   * Gibt einen Alert aus, wenn ein WikiBook erfolgreich gespeichert wurde. (Save)
   */
  public static void printSaveSuccesful() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setHeaderText("Der Zettelkasten wurde erfolgreich gespeichert als 'Zettelkasten.txt'.");
    alert.showAndWait();
  }

  /**
   * Gibt einen Alert aus, wenn ein WikiBook erfolgreich eingelesen wurde. (Load)
   */
  public static void printLoadSuccesful() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setHeaderText("Der Zettelkasten wurde erfolgreich eingelesen.");
    alert.showAndWait();
  }
}
