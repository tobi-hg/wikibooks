package lpsw.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 24.01.2021
 * created on: 12.01.2021 Environment: IntelliJ, JDK 15, MacOS BigSur
 * <p>
 * Exception-Klasse, welche immer dann ausgelöst wird, wenn das Laden der Metadaten eines Artikels
 * von der Export-Seite fehlschlägt.
 * </p>
 */
public class MyWebException {

  public void printAlert() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText("Das gesuchte WikiBook konnte nicht gefunden werden.");
    alert.showAndWait();
  }
}
