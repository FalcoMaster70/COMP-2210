//imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
/**
 * Create a game like boogle!
 * Create a class that implements the interface WordSearch game.
 * Create methods to allow wordSearchGame to operate under the certain criteria.
 * @author Seth Kinsaul (smk0036@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version October 29, 2019
 */
public class myWordSearchGame implements WordSearchGame {
   private boolean lexRan = false;
   private TreeSet<String> lexTree;
   private SortedSet<String> vWords;
   private List<Integer> wordP;
   private List<Integer> anotherWordPath;
   private Boolean[][] alreadyVisited;
   private String[][] newBoard;
   private int boardSize;
   private int wordSize;
   private int length;
   private int nMinLength;
   private String strResult;
   private int intResult;
   private double boardDimensions;
   
   /**
    * Constructor for myWordSearchGame().
    */
   public myWordSearchGame() {
      lexTree = new TreeSet<String>();
      vWords = new TreeSet<String>();
      wordP = new ArrayList<Integer>();
      anotherWordPath = new ArrayList<Integer>();
   }
   
   /**
    * Loads the lexicon into a data structure for later use. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) throws IllegalArgumentException {
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      lexTree = new TreeSet<String>();
      try {
         Scanner scan = 
               new Scanner(new BufferedReader(new FileReader(new File(fileName))));
         while (scan.hasNext()) {
            String str = scan.next();
            lexTree.add(str.toLowerCase());
            scan.nextLine();
         }
         lexRan = true;
      }
      catch (Exception e) {
         throw new IllegalArgumentException("Error loading word list: " + fileName + ": " + e);
      } 
   }
   
   /**
    * Stores the incoming array of Strings in a data structure that will make
    * it convenient to find words.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game board in row-major order. Thus, index 0 stores the contents of board
    *     position (0,0) and index length-1 stores the contents of board position
    *     (N-1,N-1). Note that the board must be square and that the strings inside
    *     may be longer than one character.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */

   public void setBoard(String[] letterArray) throws IllegalArgumentException {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      boardDimensions = Math.sqrt(letterArray.length);
      if (boardDimensions % 1 > 0) {
         throw new IllegalArgumentException();
      }
      else {
         length = (int) boardDimensions;
         newBoard = new String[length][length];
         alreadyVisited = new Boolean[length][length];
         for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
               alreadyVisited[i][j] = false;
               newBoard[i][j] = letterArray[boardSize].toLowerCase();
               boardSize++;
            }
         }
      }
   }
   
   /**
    * Creates a String representation of the board, suitable for printing to
    *   standard out. Note that this method can always be called since
    *   implementing classes should have a default board.
    */
   public String getBoard() {
      for (String[] w : newBoard) {
         for (String w2 : w) {
            strResult = strResult + w2;
         }
      }
      System.out.println("board dimensions is" + strResult);
      return strResult;
   }
   
   /**
    * Retrieves all valid words on the game board, according to the stated game
    * rules.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the board.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game board and in the lexicon.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllValidWords(int minimumWordLength) throws IllegalArgumentException,
      IllegalStateException {
      nMinLength = minimumWordLength;
      vWords.clear();
      
      if (nMinLength < 1) {
         throw new IllegalArgumentException();
      }
      if (lexRan == false) {
         throw new IllegalStateException();
      }
      if (nMinLength > 1 && length == 1) {
         newBoard = new String[length][length];
         for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
               i = 0;
               j = 0;
               findWord(newBoard[i][j], i, j);
            }
         }
      }
      else {
         for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
               findWord(newBoard[i][j], i, j);
            }
         }
      }
      
      return vWords;
   }
   
  /**
   * Computes the cummulative score for the scorable words in the given set.
   * To be scorable, a word must (1) have at least the minimum number of characters,
   * (2) be in the lexicon, and (3) be on the board. Each scorable word is
   * awarded one point for the minimum number of characters, and one point for 
   * each character beyond the minimum number.
   *
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */  
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      
      if (lexRan == false) {
         throw new IllegalStateException();
      }
      
      for (String s: words) {
         wordSize = s.length();
         intResult += (wordSize - minimumWordLength) + 1;
      }
      return intResult;
   }
   
   /**
    * Determines if the given word is in the lexicon.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck) throws IllegalArgumentException,
      IllegalStateException {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexRan == false) {
         throw new IllegalStateException();
      }
      
      if (lexTree.contains(wordToCheck.toLowerCase())) {
         return true;
      }
      return false;
   }  
   
   /**
    * Determines if there is at least one word in the lexicon with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in lexicon, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexRan == false) {
         throw new IllegalStateException();
      }
      String lex = lexTree.ceiling(prefixToCheck);
      if (lex == null) { 
         return false;
      }
     //  if (lexTree.ceiling(prefixToCheck).startsWith(prefixToCheck)) {
   //          return true;
   //       }
      return lex.startsWith(prefixToCheck);
   }
   
   /**
    * Determines if the given word is in on the game board. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer objects with  the path
    *     that makes up the word on the game board. If word is not on the game
    *     board, return an empty list. Positions on the board are numbered from zero
    *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
    *     board, the upper left position is numbered 0 and the lower right position
    *     is numbered N^2 - 1.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      
      if (lexRan == false) {
         throw new IllegalStateException();
      }
      
      wordP.clear();
      anotherWordPath.clear();
      for (int i = 0; i < (int) length; i++) {
         for (int j = 0; j < length; j++) {
            if (Character.toUpperCase(newBoard[i][j].charAt(0)) == Character.toUpperCase(wordToCheck.charAt(0))) {
               int returnValue = j + (i * length);
               wordP.add(returnValue);
               myRecursionMethod(wordToCheck, newBoard[i][j], i, j);
               if (!anotherWordPath.isEmpty()) {
                  return anotherWordPath;
               }
               wordP.clear();
               anotherWordPath.clear();
            }
         }
      }
      return wordP;
   }
   
   
   //Helper Methods
   /**
    * This method finds words for the getAllValidWords() method
    */
   public void findWord(String wordToCheck, int x, int y) {
      if (!isValidPrefix(wordToCheck)) {
         return;
      }
     //  if (x = 0 && y = 0) {
   //       
   //       }
      alreadyVisited[x][y] = true;
      if (isValidWord(wordToCheck) && wordToCheck.length() >= nMinLength) {
         vWords.add(wordToCheck.toUpperCase());
      }
   
      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            if ((x + i) <= ((int) length - 1) && (y + j) <= ((int) length - 1) && (x + i) >= 0 && (y + j) >= 0 && !alreadyVisited[x + i][y + j]) {
               alreadyVisited[x + i][y + j] = true;
               findWord(wordToCheck + newBoard[x + i][y + j], x + i, y + j);
               alreadyVisited[x + i][y + j] = false;
            }
         }
      }
      alreadyVisited[x][y] = false;
   }

   /**
    * The recursion method for isOnBoard().
*/
   public void myRecursionMethod(String wordToCheck, String currentWord, int x, int y) {
      alreadyVisited[x][y] = true;
      if (!isValidPrefix(currentWord)) {
         return;
      }
      
      if (currentWord.toUpperCase().equals(wordToCheck.toUpperCase())) {
         anotherWordPath = new ArrayList(wordP);
         return;
      }
      
      for (int i = -1; i <= 1; i++) {
         for (int j = -1; j <= 1; j++) {
            if (currentWord.equals(wordToCheck)) {
               return;
            }
            if ((x + i) <= (length - 1) && (y + j) <= (length - 1) && (x + i) >= 0 && (y + j) >= 0 && !alreadyVisited[x + i][y + j]) {
               alreadyVisited[x + i][y + j] = true;
               int value = (x + i) * length + y + j;
               wordP.add(value);
               myRecursionMethod(wordToCheck, currentWord + newBoard[x + i][y + j], x + i, y + j);
               alreadyVisited[x + i][y + j] = false;
               wordP.remove(wordP.size() - 1);
            }
         }
      }
      alreadyVisited[x][y] = false;
      return;
   }
}
