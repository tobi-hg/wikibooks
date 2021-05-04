package lpsw.bibliothek;

import java.net.URL;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date: 28.11.2020
 * created on: 30.10.2020
 * Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Subklasse von Medium, für das Anlegen eines Elektronischen Mediums zuständig.
 * </p>
 */
public class ElektronischesMedium extends Medium {

  private String url = "";

  /**
   * Konstruktor zum instanziieren eines Elektronischen Mediums
   *
   * @param _titel Titel des Elektronischen Mediums
   * @param _url   URL des Elektronischen Mediums
   */
  public ElektronischesMedium(String _titel, String _url) {
    super(_titel);
    setUrl(_url);
  }

  /**
   * Methode zum Rerpäsentieren des Mediums
   *
   * @return formatierter String zum repräsentieren des Mediums
   */
  @Override
  public String calculateRepresentation() {
    StringBuilder sb = new StringBuilder(super.calculateRepresentation()).append("\nURL: ")
        .append(getUrl()).append("\n");
    return sb.toString();
  }

  /**
   * Methode zum Rerpäsentieren des Mediums in BibTex-Formatierung
   *
   * @return formatierter String zum repräsentieren des Mediums
   */
  @Override
  public String calculateBibTexRepresentation() {
    StringBuilder sb = new StringBuilder("@elMed{").append(super.calculateBibTexRepresentation())
        .append(", URL = {").append(getUrl()).append("}}");
    return sb.toString();
  }

  /**
   * Methode zum überprüfen der URL.
   *
   * @param _urlString zu uüerprüfende URL
   * @return true, wenn URL zulässig; false, wenn URL fehlerhaft
   */
  public static boolean checkURL(String _urlString) {
    try {
      URL url = new URL(_urlString);
      url.toURI();
      return true;
    } catch (Exception exception) {
      return false;
    }
  }

  /**
   * Getter-Methode für die URL des Elektronischen Mediums
   *
   * @return URL des Elektronischen Mediums
   */
  public String getUrl() {
    return url;
  }

  /**
   * Setter-Methode für die URL des Elektronischen Mediums. Prüft die URL auf Korrektheit.
   *
   * @param _url zu setzende URL
   */
  public void setUrl(String _url) {
    if (_url != null && !_url.trim().equals("")) {
      if (checkURL(_url)) {
        this.url = _url;
      } else {
        throw new IllegalArgumentException("URL ist fehlerhaft!");
      }
    } else {
      throw new IllegalArgumentException("URL darf nicht leer sein!");
    }
  }
}
