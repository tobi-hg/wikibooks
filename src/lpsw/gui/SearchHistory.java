package lpsw.gui;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Tobias Hagemann (Matr.Nr.: 18695)
 * @date 24.01.2021
 * created on: 12.01.2021 Environment: IntelliJ, JDK 15, MacOS BigSur
 * <p>
 * Diese Klasse repräsentiert den Suchverlauf.
 * </p>
 */
public class SearchHistory {

  ArrayList<String> searchHistory = new ArrayList<>();
  private int curIndex = -1;

  /**
   * Methode zum einfügen von neuen Strings in die Suchverlaufs-Liste.
   * @param _searchTerm String, nach dem gesucht wurde
   */
  public void add(String _searchTerm) {
    if (curIndex == searchHistory.size() - 1) {
      searchHistory.add(_searchTerm);
      curIndex++;
    } else if (curIndex < searchHistory.size() - 1) {
      searchHistory.add(++curIndex, _searchTerm);
      searchHistory.subList(curIndex + 1, searchHistory.size()).clear();
    }
  }

  /**
   * Methode zum 'zurück-scrollen', falls möglich.
   * Wird ausgelöst, wenn der 'Zurück'-Button gedrückt wird.
   */
  public void scrollBackward() {
    if (hasPrevious()) {
      curIndex--;
    }
  }

  /**
   * Methode zum 'weiter-scrollen', falls möglich.
   * Wird ausgelöst, wenn der 'Vor'-Button gedrückt wird.
   */
  public void scrollForward() {
    if (hasNext()) {
      curIndex++;
    }
  }

  /**
   * Methode, um die Reihenfolge des Suchverlaufs zu ändern.
   * Wird genutzt, um Reihenfolge 'Neuestes...Letztes' zu realisieren.
   */
  public void reverseOrder() {
    Collections.reverse(searchHistory);
  }

  /**
   * Methode zum Prüfen, ob Suchverlauf leer ist.
   * @return true, wenn leer; sonst false
   */
  public boolean isEmpty() {
    return searchHistory.isEmpty();
  }

  /**
   * Methode zum Prüfen, ob Element einen 'vorherigen' Eintrag hat
   * @return true, wenn curIndex > 0; false, wenn curIndex <= 0
   */
  public boolean hasPrevious() {
    return curIndex > 0;
  }

  /**
   * Methode zum Prüfen, ob Element einen 'nächsten' Eintrag hat
   * @return Wahrheitswert, ob nächster Eintrag vorhanden
   */
  public boolean hasNext() {
    return curIndex < searchHistory.size() - 1;
  }

  /**
   * Getter-Methode für den gesamten Suchverlauf.
   * @return Suchverlauf
   */
  public ArrayList<String> getSearchHistory() {
    return searchHistory;
  }

  /**
   * Getter-Methode für den aktuellen Index des Eintrags.
   * @return aktueller Index
   */
  public String getCurIndex() {
    return searchHistory.get(curIndex);
  }

  /**
   * Setter-Methode zum wetzen des aktuellen Indexes.
   * @param _i zu setzender Index
   */
  public void setCurIndex(int _i) {
    curIndex = _i;
  }

  /**
   * Getter-Methode für den Index eines zu suchenden Elements
   * @param _s zu suchendes Element
   * @return Index des Elements, falls vorhanden, sonst -1
   */
  public int getIndexOfElement(String _s) {
    if (searchHistory.contains(_s)) {
      return searchHistory.indexOf(_s);
    }
    return -1;
  }
}
