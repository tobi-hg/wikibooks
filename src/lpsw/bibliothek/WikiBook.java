package lpsw.bibliothek;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date: 30.12.2020
 * created on: 20.12.2020
 * Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Subklasse von ElektronischesMedium, für das Anlegen eines Wikibuchs zuständig.
 * </p>
 */
public class WikiBook extends ElektronischesMedium {

  private String verfasser = "";
  private final ArrayList<String> alleRegale = new ArrayList<>();
  private final ArrayList<String> alleKapitel = new ArrayList<>();
  private Date letzteBearbeitung = null;

  /**
   * Konstruktor zum instanziieren eines Elektronischen Mediums
   *
   * @param _titel Titel des Elektronischen Mediums
   */
  public WikiBook(String _titel, String _url) {
    super(_titel, _url);
  }

  /**
   * Methode zum Rerpäsentieren des Mediums
   *
   * @return formatierter String zum repräsentieren des Mediums
   */
  @Override
  public String calculateRepresentation() {
    StringBuilder sb = new StringBuilder(getTitel()).append("\n");
    int i = 0;
    if (alleRegale.size() == 1) {
      sb.append("Regal: ");
      sb.append(alleRegale.get(0)).append("\n");
    } else {
      sb.append("Regale: \n");
      for (String s : alleRegale) {
        sb.append(++i).append(" ").append(s).append("\n");
      }
    }
    sb.append("Kapitel:\n");
    i = 0;
    for (String s : alleKapitel) {
      sb.append(++i).append(" ").append(s).append("\n");
    }
    SimpleDateFormat datumsformat = new SimpleDateFormat("dd.MM.yyyy 'um' HH:mm:ss");
    sb.append("Letzte Änderung: ").append(datumsformat.format(getLetzteBearbeitung())).append("\n");
    sb.append("Urheber: ").append(getVerfasser());
    return sb.toString();
  }

  /**
   * Methode zum Rerpäsentieren des Mediums in BibTex-Formatierung
   *
   * @return formatierter String zum repräsentieren des Mediums
   */
  @Override
  public String calculateBibTexRepresentation() {
    return super.calculateBibTexRepresentation().replace("elMed", "wikiBook");
  }

  public String getVerfasser() {
    return verfasser;
  }

  public void setVerfasser(String _verfasser) {
    verfasser = _verfasser;
  }


  public Date getLetzteBearbeitung() {
    return letzteBearbeitung;
  }

  public void setLetzteBearbeitung(Date _letzteBearbeitung) {
    letzteBearbeitung = _letzteBearbeitung;
  }

  public void setRegal(String _regal) {
    alleRegale.add(_regal);
  }

  public void setKapitel(String _kapitel) {
    alleKapitel.add(_kapitel);
  }
}
