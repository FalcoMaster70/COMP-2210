import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * MarkovModel.java Creates an order K Markov model of the supplied source
 * text. The value of K determines the size of the "kgrams" used to generate
 * the model. A kgram is a sequence of k consecutive characters in the source
 * text.
 *
 * @author     Set Kinsaul (smk0036@auburn.edu)
 * @author     Dean Hendrix (dh@auburn.edu)
 * @version    2018-04-17
 *
 */
public class MarkovModel {

   // Map of <kgram, chars following> pairs that stores the Markov model.
   private HashMap<String, String> model;

   // add other fields as you need them ...
   int X = 0;
   int Y = 0;
   int index = 0;
   String First = "";
   String emptyString = "";
   String kGram = "";
   String tString = "";
   Random ran = new Random();

   /**
    * Reads the contents of the file sourceText into a string, then calls
    * buildModel to construct the order K model.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, File sourceText) {
      model = new HashMap<>();
      try {
         String text = new Scanner(sourceText).useDelimiter("\\Z").next();
         buildModel(K, text);
      }
      catch (IOException e) {
         System.out.println("Error loading source text: " + e);
      }
   }


   /**
    * Calls buildModel to construct the order K model of the string sourceText.
    *
    * DO NOT CHANGE THIS CONSTRUCTOR.
    *
    */
   public MarkovModel(int K, String sourceText) {
      model = new HashMap<>();
      buildModel(K, sourceText);
   }


   /**
    * Builds an order K Markov model of the string sourceText.
    */
   private void buildModel(int K, String sourceText) {
      int K2 = K;
      X = 0;
      Y = 0;
      First = sourceText.substring(0 , K2);
      while ((X + K2) <= sourceText.length()) {
         kGram = sourceText.substring(X, X + K2);
         if (!(model.containsKey(kGram))) {
            int K3 = K;
            while ((Y + K3) < sourceText.length()) {
               tString = sourceText.substring(Y, Y + K3);
               if ((Y + K2) >= sourceText.length()) {
                  emptyString += '\u0000';
               }
               if (kGram.equals(tString)) {
                  emptyString += sourceText.substring(Y + K3, Y + K3 + 1);
               }
               Y++;
            }
            model.put(kGram, emptyString);
         }
         Y = 0;
         X++;
      } 
   }


   /** Returns the first kgram found in the source text. */
   public String getFirstKgram() {
      return First;
   }


   /** Returns a kgram chosen at random from the source text. */
   public String getRandomKgram() {
      X = model.size();
      Y = 0;
      index = ran.nextInt(X);
      for (String s : model.keySet()) {
         if (index == Y) {
            return s;
         }
         Y++;
      }
      return null;
   }


   /**
    * Returns the set of kgrams in the source text.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   public Set<String> getAllKgrams() {
      return model.keySet();
   }


   /**
    * Returns a single character that follows the given kgram in the source
    * text. This method selects the character according to the probability
    * distribution of all characters that follow the given kgram in the source
    * text.
    */
   public char getNextChar(String kgram) {
      X = 0;
      for (String s : model.keySet()) {
         if (s.equals(kgram)) {
            emptyString = model.get(kgram);
            Y = emptyString.length();
            if (Y > 0) {
               X = ran.nextInt(Y) + 1;
            }
         }
      }
      Y = X - 1;
      if (!(emptyString.equals(""))) {
         return emptyString.charAt(Y);
      } 
      return '\u0000';
   }


   /**
    * Returns a string representation of the model.
    * This is not part of the provided shell for the assignment.
    *
    * DO NOT CHANGE THIS METHOD.
    *
    */
   @Override
    public String toString() {
      return model.toString();
   }

}
