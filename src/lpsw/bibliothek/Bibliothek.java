package lpsw.bibliothek;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 28.11.2020
 * created on: 12.11.2020 Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Dieses Programm soll einen Zettelkasten über diverse Medien digitalisieren.
 * </p>
 */
public class Bibliothek {

  public static void main(String[] args) {
    Zettelkasten zettelkasten = new Zettelkasten();
    zettelkasten.addMedium(new Buch("-","Duden 01. Die deutsche Rechtschreibung",
        "Bibliographisches Institut, Mannheim", 2004, "3-411-04013-0"));
    zettelkasten.addMedium(new Zeitschrift("Der Spiegel", "0038-7452",
        54, 6));
    zettelkasten.addMedium(new CD("Live At Wembley","Queen",
        "Parlophone (EMI)"));
    zettelkasten.addMedium(new ElektronischesMedium("Hochschule Stralsund",
        "http://www.hochschule-stralsund.de"));

    if (args.length > 0) {
      StringBuilder arguments = new StringBuilder(args[0]);
      for (int i = 1; i < args.length; i++) {
        if (args[i] != null) {
          arguments.append(" ").append(args[i]);
        }
      }
      zettelkasten.addMedium(zettelkasten.findWikiBuch(arguments.toString()));
    }

    for (Medium medium : zettelkasten) {
      System.out.println(medium.calculateRepresentation());
    }

    //Zettelkasten mit binärer Kodierung in Datei schreiben
    Persistency binaryPersistency = new BinaryPersistency();
    binaryPersistency.save(zettelkasten, "BinaryZettelkasten.txt");

    System.out.println("----------------------Titel-Duplikate einfügen------------------------");
    zettelkasten.addMedium(new Buch("-","Der Spiegel",
        "Bibliographisches Institut, Mannheim",2004,"3-411-04013-0"));
    zettelkasten.addMedium(new Zeitschrift("Der Spiegel", "0038-7452",
        54, 6));

    //Zettelkasten absteigend sortieren ("ZA" um aufsteigend zu sortieren)
    zettelkasten.sort("AZ");

    for (Medium medium : zettelkasten) {
      System.out.println(medium.calculateRepresentation());
    }

    //Zettelkasten mit bibTex-Formatierung in Datei schreiben
    Persistency bibTexPersistency = new BibTexPersistency();
    bibTexPersistency.save(zettelkasten, "BibTexZettelkasten.txt");

    System.out.println("----------------------Titel-Duplikate löschen------------------------");
    if (zettelkasten.dropMedium("Der Spiegel")) {
      for (Medium medium : zettelkasten) {
        System.out.println(medium.calculateRepresentation());
      }
    }

    //Zettelkasten mit entsprechender Formatierung in Datei schreiben
    Persistency humanReadable = new HumanReadablePersistency();
    humanReadable.save(zettelkasten, "Zettelkasten.txt");

    System.out.println("------------------Binärkodierten Zettelkasten einlesen-------------------");
    Zettelkasten newZk = binaryPersistency.load("BinaryZettelkasten.txt");
    for (Medium medium : newZk) {
      System.out.println(medium.calculateRepresentation());
    }

    System.out.println("------------------BibTex-Zettelkasten einlesen-------------------");
    newZk = bibTexPersistency.load("BibTexZettelkasten.txt");
    for (Medium medium : newZk) {
      System.out.println(medium.calculateBibTexRepresentation());
    }
  }
}
