/**
 * Provides a factory method for creating word search games. 
 *
 * @author Seth Kinsaul (smk0036@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version October 31, 2019
 */
public class WordSearchGameFactory {

   /**
    * Returns an instance of a class that implements the WordSearchGame
    * interface.
    */
   public static WordSearchGame createGame() {
      // You must return an instance of your solution class here instead of null.
      return new myWordSearchGame();
   }

}
