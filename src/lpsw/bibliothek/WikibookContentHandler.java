package lpsw.bibliothek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date: 30.12.2020
 * created on: 20.12.2020
 * Environment: IntelliJ, JDK 14, MacOS BigSur
 * <p>
 * Content Handler, zum Parsen eines Wikibuchs zuständig.
 * </p>
 */
public class WikibookContentHandler implements ContentHandler {

  private StringBuilder currentValue = new StringBuilder();
  private String url = "";
  private WikiBook wikibook = null;

  /**
   * Konstruktor, setzt die URL des Wikibuchs
   * @param _url URL des Wikibuchs
   */
  public WikibookContentHandler(String _url) {
    setUrl(_url);
  }

  // Aktuelle Zeichen die gelesen werden, werden in eine Zwischenvariable
  // gespeichert
  public void characters(char[] ch, int start, int length)
      throws SAXException {
    currentValue.append(ch, start, length);
  }

  // Methode wird aufgerufen wenn der Parser zu einem Start-Tag kommt
  public void startElement(String uri, String localName, String qName,
      Attributes atts) throws SAXException {
  }

  // Methode wird aufgerufen wenn der Parser zu einem End-Tag kommt
  public void endElement(String uri, String localName, String qName)
      throws SAXException {
    final String content = currentValue.toString().trim();
    currentValue.setLength(0);

    // Titel setzen
    if (localName.equals("title")) {
      wikibook = new WikiBook(content, getUrl());
    }

    if (localName.equals("timestamp")) {
      SimpleDateFormat datumsformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.GERMANY);
      try {
        Date date = datumsformat.parse(content);
        wikibook.setLetzteBearbeitung(date);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    if (localName.equals("text")) {
      //System.out.println(content);
      parseString(content);
    }

    // Urheber setzen
    if (localName.equals("ip") || localName.equals("username")) {
      wikibook.setVerfasser(content);
    }
  }

  /**
   * Überprüft den bergebenen String auf gängige Formatierungen des Wikibuchs und
   * filtert entsprechend Daten für die Zuweisungen von Regalen und/oder Kapiteln der Wikibücher.
   * @param _content zu filternder String
   */
  public void parseString(String _content) {
    String[] lines = _content.split("\n");
    for (String line : lines) {
      StringBuilder tmpStr = new StringBuilder(line);
      if (tmpStr.toString().startsWith("{{Regal|")) {
        tmpStr.replace(0, tmpStr.indexOf("|") + 1, "");
        if (tmpStr.toString().startsWith("ort=")) {
          tmpStr.replace(0, tmpStr.indexOf("=") + 1, "");
        }
        while (tmpStr.toString().contains("|")) {
          wikibook.setRegal(tmpStr.substring(0, tmpStr.indexOf("|")));
          tmpStr.replace(0, tmpStr.indexOf("|") + 1, "");
        }
        tmpStr.replace(tmpStr.indexOf("}"), tmpStr.indexOf("}") + 2, "");
        wikibook.setRegal(tmpStr.toString());
      }
      if (line.startsWith("==") && !line.startsWith("===")) {
        tmpStr.replace(0, tmpStr.indexOf("=")+2, "");
        tmpStr.replace(tmpStr.indexOf("="), tmpStr.indexOf("=")+2, "");
        wikibook.setKapitel(tmpStr.toString().trim());
      }
    }
  }

  public void endDocument() throws SAXException {
  }

  public void endPrefixMapping(String prefix) throws SAXException {
  }

  public void ignorableWhitespace(char[] ch, int start, int length)
      throws SAXException {
  }

  public void processingInstruction(String target, String data)
      throws SAXException {
  }

  public void setDocumentLocator(Locator locator) {
  }

  public void skippedEntity(String name) throws SAXException {
  }

  public void startDocument() throws SAXException {
  }

  public void startPrefixMapping(String prefix, String uri)
      throws SAXException {
  }

  public WikiBook getWikiBook() {
    return wikibook;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String _url) {
    url = _url;
  }
}
