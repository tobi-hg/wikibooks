package lpsw.bibliothek;

import java.io.Serializable;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date: 30.12.2020
 * created on: 30.10.2020
 * Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Basisklasse, von der die Subklassen erben.
 * </p>
 */
public abstract class Medium implements Comparable, Serializable {
  private String titel = "";

  /**
   * Konstruktor, zum instanziieren eines Mediums
   *
   * @param _titel zu setzender Titel des Mediums
   */
  public Medium(String _titel) {
    setTitel(_titel);
  }

  /**
   * Funktion zum formatierten Ausgeben des Mediums
   *
   * @return formatierter String für die Ausgabe
   */
  public String calculateRepresentation() {
    StringBuilder sb = new StringBuilder("Titel: ").append(getTitel());
    return sb.toString();
  }

  /**
   * Funktion zum formatierten Ausgeben des Mediums im BibTex-Format
   *
   * @return formatierter String für die Ausgabe
   */
  public String calculateBibTexRepresentation() {
    StringBuilder sb = new StringBuilder("title = {").append(getTitel()).append("}");
    return sb.toString();
  }

  /**
   * Getter-Methode für Titel
   *
   * @return Titel des Mediums
   */
  public String getTitel() {
    return titel;
  }

  /**
   * Setter-Methode für Titel
   *
   * @param _titel zu setzender Titel des Mediums
   */
  public void setTitel(String _titel) {
    if (_titel != null && !_titel.trim().equals("")) {
      this.titel = _titel;
    } else {
      throw new IllegalArgumentException("Titel darf nicht leer sein!");
    }
  }

  /**
   * Methode zum Vergleichen der Titel von zwei Objekten, falls die Objekte den gleichen Titel haben,
   * wird die Klasse verglichen.
   * @param _o Objekt, das mit "diesem" Objekt verglichen werden soll
   * @return Wert >, < oder = 0, wenn "dieser"-Titel größer, kleiner oder gleich dem anderen Titel ist
   */
  @Override
  public int compareTo(Object _o) {
    Medium other = (Medium) _o;
    int comparison = this.getTitel().compareTo(other.getTitel());
    if (comparison == 0) {
      return this.getClass().getCanonicalName().compareTo(other.getClass().getCanonicalName());
    }
    return comparison;
  }
}
