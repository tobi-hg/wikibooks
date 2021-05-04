package lpsw.bibliothek;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 28.11.2020
 * created on: 30.10.2020
 * Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Subklasse von Medium, für das Anlegen eines Buches zuständig.
 * </p>
 */
public class Buch extends Medium {
  private String verlag, isbn, verfasser = "";
  private int erscheinungsjahr = 0;

  /**
   * Konstruktor zum instanziieren eines Buches
   *
   * @param _titel            Titel des Buches
   * @param _verlag           Verlag des Buches
   * @param _isbn             ISBN des Buches
   * @param _verfasser        Verfasser des Buches
   * @param _erscheinungsjahr Erscheinungsjahr des Buches
   */
  public Buch(String _verfasser, String _titel, String _verlag, int _erscheinungsjahr, String _isbn) {
    super(_titel);
    setVerlag(_verlag);
    setIsbn(_isbn);
    setVerfasser(_verfasser);
    setErscheinungsjahr(_erscheinungsjahr);
  }

  /**
   * Methode zum Rerpäsentieren des Mediums
   *
   * @return formatierter String zum repräsentieren des Mediums
   */
  @Override
  public String calculateRepresentation() {
    StringBuilder sb = new StringBuilder(super.calculateRepresentation())
        .append("\nErscheinungsjahr: ")
        .append(getErscheinungsjahr()).append("\nVerlag: ").append(getVerlag()).append("\nISBN: ")
        .append(getIsbn()).append("\nVerfasser: ").append(getVerfasser()).append("\n");
    return sb.toString();
  }

  /**
   * Methode zum Rerpäsentieren des Mediums in BibText-Formatierung
   *
   * @return formatierter String zum repräsentieren des Mediums
   */
  @Override
  public String calculateBibTexRepresentation() {
    StringBuilder sb = new StringBuilder("@book{author = {").append(getVerfasser()).append("}, ")
        .append(super.calculateBibTexRepresentation()).append(", publisher = {").append(getVerlag())
        .append("}, year = ").append(getErscheinungsjahr()).append(", isbn = {").append(getIsbn())
        .append("}}");
    return sb.toString();
  }

  /**
   * Methode zum Prüfen einer 10-stelligen ISBN
   *
   * @param _isbn Array mit den Zahlen der ISBN
   * @return true, wenn ISBN zulässig; false, wenn ISBN fehlerhaft
   */
  public static boolean checkISBN10(int[] _isbn) {
    int sum = 0;
    for (int i = 1; i <= _isbn.length; i++) {
      sum += i * _isbn[i - 1];
    }
    return sum % 11 == 0;
  }

  /**
   * Methode zum Prüfen einer 13-stelligen ISBN
   *
   * @param _isbn Array mit den Zahlen der ISBN
   * @return true, wenn ISBN zulässig; false, wenn ISBN fehlerhaft
   */
  public static boolean checkISBN13(int[] _isbn) {
    int sum = 0;
    for (int i = 1; i < _isbn.length; i++) {
      if (i % 2 == 0) {
        sum += _isbn[i - 1] * 3;
      } else {
        sum += _isbn[i - 1];
      }
    }
    int lastDigit = sum % 10;
    int check = (10 - lastDigit) % 10;
    return _isbn[_isbn.length - 1] == check;
  }

  /**
   * Getter-Methode für den Verlag
   *
   * @return Verlag des Buches
   */
  public String getVerlag() {
    return verlag;
  }

  /**
   * Setter-Methode für den Verlag
   *
   * @param _verlag zu setzender Verlag des Buches
   */
  public void setVerlag(String _verlag) {
    if (_verlag != null && !_verlag.trim().equals("")) {
      this.verlag = _verlag;
    } else {
      throw new IllegalArgumentException("Verlagname darf nicht leer sein!");
    }
  }

  /**
   * Getter-Methode für die ISBN
   *
   * @return ISBN des Buches
   */
  public String getIsbn() {
    return isbn;
  }

  /**
   * Setter-Methode für die ISBN. Prüft die ISBN auf Gültigkeit.
   *
   * @param _isbn zu setzende ISBN des Buches
   */
  public void setIsbn(String _isbn) {
    if (_isbn != null && !_isbn.trim().equals("")) {

      //Temporärer String mit der übergebenen ISBN, ohne "-" Sonderzeichen
      String tmpIsbn = _isbn.replace("-", "");
      //Anlegen eines Arrays mit der länge, welche der Stellenzahl der ISBN entspricht
      int[] isbnArr = new int[tmpIsbn.length()];
      //Array mit Ziffern der ISBN befüllen
      for (int i = 0; i < tmpIsbn.length(); i++) {
        isbnArr[i] = Character.getNumericValue(tmpIsbn.charAt(i));
      }
      //ISBN auf laenge überprüfen und dann enstprechenden Algorithmus aufrufen.
      //Wenn ISBN zulässig ist, wird sie übernommen, sonst wird eine Fehlermeldung auf der Konsole ausgegeben.
      switch (isbnArr.length) {
        case 10:
          if (checkISBN10(isbnArr)) {
            this.isbn = _isbn;
            break;
          }
        case 13:
          if (checkISBN13(isbnArr)) {
            this.isbn = _isbn;
            break;
          }
        default:
          throw new IllegalArgumentException("ISBN muss entweder 10 oder 13 Ziffern lang sein!");
      }
    } else {
      throw new IllegalArgumentException("ISBN darf nicht leer sein!");
    }
  }

  /**
   * Getter-Methode für den Verfasser
   *
   * @return Verfasser des Buches
   */
  public String getVerfasser() {
    return verfasser;
  }

  /**
   * Setter-Methode für den Verfasser
   *
   * @param _verfasser zu setzender Verfasser des Buches
   */
  public void setVerfasser(String _verfasser) {
    if (_verfasser != null && !_verfasser.trim().equals("")) {
      this.verfasser = _verfasser;
    } else {
      throw new IllegalArgumentException("Verfassername darf nicht leer sein!");
    }
  }

  /**
   * Getter-Methode für das Erscheinungsjahr
   *
   * @return Erscheinungsjahr des Buches
   */
  public int getErscheinungsjahr() {
    return erscheinungsjahr;
  }

  /**
   * Setter-Methode für das Erscheinungsjahr
   *
   * @param _erscheinungsjahr zu setzendes Erscheinungsjahr des Buches
   */
  public void setErscheinungsjahr(int _erscheinungsjahr) {
    if (_erscheinungsjahr > 0) {
      this.erscheinungsjahr = _erscheinungsjahr;
    } else {
      throw new IllegalArgumentException("Erscheinungsjahr darf nicht negativ sein!");
    }
  }
}
