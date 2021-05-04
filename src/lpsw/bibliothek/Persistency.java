package lpsw.bibliothek;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 28.11.2020
 * created on: 24.11.2020 Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Interface zum Persistieren.
 * </p>
 */
public interface Persistency {

  /**
   * Schreibt einen Zettelkasten in eine Datei
   * @param _zk Zettelkasten
   * @param _dateiname Datei
   */
  public void save(Zettelkasten _zk, String _dateiname);

  /**
   * Liest einen Zettelkasten aus einer Datei ein
   * @param _dateiname Datei
   * @return eingelesener Zettelkasten
   */
  public Zettelkasten load(String _dateiname);
}
