package lpsw.bibliothek;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 28.11.2020
 * created on: 24.11.2020 Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Ermöglicht es einen Zettelkasten in BibTex-Fromatierung in eine Datei zu schreiben- und einzulesen.
 * </p>
 */
public class BibTexPersistency implements Persistency {

  /**
   * Schreibt einen Zettelkasten in BibTex-Formatierung in eine Datei
   * @param _zk Zettelkasten
   * @param _dateiname Datei
   */
  @Override
  public void save(Zettelkasten _zk, String _dateiname) {
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(_dateiname));
      for (Medium medium : _zk) {
        out.write(medium.calculateBibTexRepresentation());
        out.write("\n");
      }
      out.close();
    } catch (IOException _e) {
      _e.printStackTrace();
    }
  }

  /**
   * Liest einen Zettelkasten in BibTex-Fromatierung aus einer Datei ein
   * @param _dateiname Datei
   * @return eingelesener Zettelkasten
   */
  @Override
  public Zettelkasten load(String _dateiname) {
    Zettelkasten tmpZk = new Zettelkasten();
    try {
      BufferedReader in = new BufferedReader(new FileReader(_dateiname));
      String line;

      while ((line = in.readLine()) != null) {
        tmpZk.addMedium(tmpZk.parseBibTex(line));
      }
      in.close();
    } catch (IOException _e) {
      _e.printStackTrace();
    }
    return tmpZk;
  }
}
