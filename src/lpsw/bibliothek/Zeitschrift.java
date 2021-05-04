package lpsw.bibliothek;

import lpsw.bibliothek.Medium;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * Date: 28.11.2020
 * created on: 30.10.2020
 * Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Subklasse von Medium, für das Anlegen einer Zeitschrift zuständig.
 * </p>
 */
public class Zeitschrift extends Medium {
  private String issn = "";
  private int volume, nummer = 0;

  /**
   * Konstruktor zum instanziieren einer Zeitschrift
   *
   * @param _titel  Titel der Zeitschrift
   * @param _issn   ISSN der Zeitschrift
   * @param _volume Volume der Zeitschrift
   * @param _nummer Nummer der Zeitschrift
   */
  public Zeitschrift(String _titel, String _issn, int _volume, int _nummer) {
    super(_titel);
    setIssn(_issn);
    setVolume(_volume);
    setNummer(_nummer);
  }

  /**
   * Methode zum Repräsentieren des Mediums
   *
   * @return formatierter String zum repräsentieren des Mediums
   */
  @Override
  public String calculateRepresentation() {
    StringBuilder sb = new StringBuilder(super.calculateRepresentation()).append("\nISSN: ")
        .append(getIssn())
        .append("\nVolume: ").append(getVolume()).append("\nNummer: ").append(getNummer()).append("\n");
    return sb.toString();
  }

  /**
   * Methode zum Repräsentieren des Mediums in BibTex-Formatierung
   *
   * @return formatierter String zum repräsentieren des Mediums
   */
  @Override
  public String calculateBibTexRepresentation() {
    StringBuilder sb = new StringBuilder("@journal{").append(super.calculateBibTexRepresentation())
        .append(", issn = {").append(getIssn()).append("}, volume = ").append(getVolume())
        .append(", number = ").append(getNummer()).append("}");
    return sb.toString();
  }

  /**
   * Getter-Methode für die ISSN
   *
   * @return ISSN der Zeitschrift
   */
  public String getIssn() {
    return issn;
  }

  /**
   * Setter-Methode für die ISSN
   *
   * @param _issn zu setzende ISSN der Zeitschrift
   */
  public void setIssn(String _issn) {
    if (_issn != null && !_issn.trim().equals("")) {
      this.issn = _issn;
    } else {
      throw new IllegalArgumentException("ISSN darf nicht leer sein!");
    }
  }

  /**
   * Getter-Methode für das Volume
   *
   * @return Volume der Zeitschrift
   */
  public int getVolume() {
    return volume;
  }

  /**
   * Setter-Methode für das Volume
   *
   * @param _volume zu setzende Volume der Zeitschrift
   */
  public void setVolume(int _volume) {
    if (_volume > 0) {
      this.volume = _volume;
    } else {
      throw new IllegalArgumentException("Volume darf nicht negativ sein!");
    }
  }

  /**
   * Getter-Methode für die Nummer
   *
   * @return Nummer der Zeitschrift
   */
  public int getNummer() {
    return nummer;
  }

  /**
   * Setter-Methode für die Nummer
   *
   * @param _nummer zu setzende Nummer der Zeitschrift
   */
  public void setNummer(int _nummer) {
    if (_nummer > 0) {
      this.nummer = _nummer;
    } else {
      throw new IllegalArgumentException("Nummer darf nicht negativ sein!");
    }
  }
}
