package lpsw.bibliothek;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 28.11.2020
 * created on: 24.11.2020 Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Ermöglicht es einen Zettelkasten in eine Datei zu schreiben- und einzulesen.
 * </p>
 */
public class HumanReadablePersistency implements Persistency {

  /**
   * Schreibt einen Zettelkasten in eine Datei
   * @param _zk Zettelkasten
   * @param _dateiname Datei
   */
  @Override
  public void save(Zettelkasten _zk, String _dateiname) {
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(_dateiname));
      for (Medium medium : _zk) {
        out.write(medium.calculateRepresentation());
        out.write("\n");
      }
      out.close();
    } catch (IOException _e) {
      _e.printStackTrace();
    }
  }

  /**
   * Soll nach Implementierung einen Zettelkasten aus einer Datei einlesen
   * @param _dateiname Datei
   * @return UnsupportedOperationException, bis Methode vollständig implementiert wird
   */
  @Override
  public Zettelkasten load(String _dateiname) {
    throw new UnsupportedOperationException();
  }
}
