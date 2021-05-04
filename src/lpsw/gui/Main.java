package lpsw.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 24.01.2021
 * created on: 12.01.2021 Environment: IntelliJ, JDK 15, MacOS BigSur
 * <p>
 * Dieses Programm soll die GUI einer Bibliothek mit einem Zettelkasten, sowie eine WebEngine von
 * WikiBook realisieren.
 * </p>
 */
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
    primaryStage.setTitle("Mein Wikibooks-Browser");
    primaryStage.setScene(new Scene(root, 1250, 800));
    primaryStage.setMinWidth(1066);
    primaryStage.setMinHeight(800);

    // EventFilter zum abfangen der 'F1'-Taste, zum anzeigen der Programmsinformationen
    root.addEventFilter(KeyEvent.KEY_PRESSED, _keyEvent -> {
      if (_keyEvent.getCode() == KeyCode.F1) {
        WikiBooksController controller = new WikiBooksController();
        controller.showInformations();
      }
    });

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
