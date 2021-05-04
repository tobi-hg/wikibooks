package lpsw.bibliothek;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 28.11.2020
 * created on: 24.11.2020 Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Ermöglicht es einen Zettelkasten in Binärer-Kodierung in eine Datei zu schreiben- und einzulesen.
 * </p>
 */
public class BinaryPersistency implements Persistency {

  /**
   * Schreibt einen Zettelkasten in Binärer-Kodierung in eine Datei
   * @param _zk Zettelkasten
   * @param _dateiname Datei
   */
  @Override
  public void save(Zettelkasten _zk, String _dateiname) {
    try {
      ObjectOutput out = new ObjectOutputStream(new FileOutputStream(_dateiname));
      out.writeObject(_zk);
      out.close();
    } catch (IOException _e) {
      _e.printStackTrace();
    }
  }

  /**
   * Liest einen Zettelkasten in Binärer-Kodierung aus einer Datei ein
   * @param _dateiname Datei
   * @return eingelesener Zettelkasten
   */
  @Override
  public Zettelkasten load(String _dateiname) {
    Zettelkasten tmpZk = new Zettelkasten();
    try {
      ObjectInput in = new ObjectInputStream(new FileInputStream(_dateiname));
      tmpZk = (Zettelkasten) in.readObject();
      in.close();
    } catch (IOException | ClassNotFoundException _e) {
      _e.printStackTrace();
    }
    return tmpZk;
  }
}
