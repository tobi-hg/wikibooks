package lpsw.bibliothek;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date: 28.11.2020
 * created on: 30.10.2020 Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Subklasse von Medium, für das Anlegen einer CD zuständig.
 * </p>
 */
public class CD extends Medium {

  private String label, kuenstler = "";

  /**
   * Konstruktor zum instanziieren einer CD
   *
   * @param _titel     Titel der CD
   * @param _label     Label der CD
   * @param _kuenstler Künstler der CD
   */
  public CD(String _titel, String _kuenstler, String _label) {
    super(_titel);
    setLabel(_label);
    setKuenstler(_kuenstler);
  }

  /**
   * Methode zum Rerpäsentieren des Mediums
   *
   * @return formatierter String zum repräsentieren des Mediums
   */
  @Override
  public String calculateRepresentation() {
    StringBuilder sb = new StringBuilder(super.calculateRepresentation()).append("\nLabel: ")
        .append(getLabel()).append("\nKünstler: ").append(getKuenstler()).append("\n");
    return sb.toString();
  }

  /**
   * Methode zum Rerpäsentieren des Mediums in BibTex-Formatierung
   *
   * @return formatierter String zum repräsentieren des Mediums
   */
  @Override
  public String calculateBibTexRepresentation() {
    StringBuilder sb = new StringBuilder("@cd{").append(super.calculateBibTexRepresentation())
        .append(", artist = {").append(getKuenstler()).append("}, label = {").append(getLabel())
        .append("}}");
    return sb.toString();
  }

  /**
   * Getter-Methode für das Label
   *
   * @return Label der CD
   */
  public String getLabel() {
    return label;
  }

  /**
   * Setter-Methode für das Label
   *
   * @param _label zu setzendes Label der CD
   */
  public void setLabel(String _label) {
    if (_label != null && !_label.trim().equals("")) {
      this.label = _label;
    } else {
      throw new IllegalArgumentException("Label darf nicht leer sein!");
    }
  }

  /**
   * Getter-Methode für den Künstler
   *
   * @return Künstler der CD
   */
  public String getKuenstler() {
    return kuenstler;
  }

  /**
   * Setter-Methode für den Künstler
   *
   * @param _kuenstler zu setzender Künstler der CD
   */
  public void setKuenstler(String _kuenstler) {
    if (_kuenstler != null && !_kuenstler.trim().equals("")) {
      this.kuenstler = _kuenstler;
    } else {
      throw new IllegalArgumentException("Künstlername darf nicht leer sein!");
    }
  }
}
