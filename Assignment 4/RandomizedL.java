import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
/**
 * This class implements Randomized List
 * @author Seth Kinsaul (smk0036@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version October 20, 2019
 */
public class RandomizedL<T> implements RandomizedList<T> {
   
   private T[] elements;
   private int size;
   private static final int DEFAULT_LENGTH = 1;
   
    @SuppressWarnings("unchecked")
   public RandomizedL() {
   
      size = 0;
      elements = (T[]) new Object[DEFAULT_LENGTH];
   }
   @Override
   public void add (T element) throws IllegalArgumentException {
      if (element == null) {
         throw new IllegalArgumentException("element can't be null");
      }
      
      if (size == elements.length) {
         resize(elements.length * 2);
      }
   
      elements[size()] = element;
      size++;
   }

   @Override
   public T remove() {
      if (size() == 0) {
         return null;
      }
        
      int r = new Random().nextInt(size());
      
      T deleted = elements[r];
      elements[r] = elements[size() - 1];
      elements[size() - 1] = null;
      size--;
      
      if (size() > 0 && size() < elements.length / 4) {
         resize(elements.length / 2);
      }
      
      return deleted;
   }
   @Override
   public T sample() {
      if (size == 0) {
         return null;
      }
      
      int r = new Random().nextInt(size());
      return elements[r];
   }
   @Override
   public int size() {
      return size;
   }
   @Override
   public boolean isEmpty() {
      return (size == 0);
   }
   /**
   *@return tr
   */
   public Iterator<T> iterator() {
      Iteration2 itr = new Iteration2(elements, size());
      return itr;
   }
   @SuppressWarnings("unchecked")
   private void resize(int capacity) {
   
      T[] array = (T[]) new Object[capacity];
      for (int i = 0; i < size(); i++) {
         array[i] = elements[i];
      }
      elements = array;
   }
   
   //subclass
   private class Iteration2 implements Iterator<T> {
  private T[] items;
      private int count;
      private int current;
      
      /**
      * creates special iterator, "Iteration," for this program.
      */
      Iteration2(T[] array, int amount) {
      
         items = array;
         count = amount;
         current = 0;
      }
   
      /**
      * checks if the iterator has a next element.
      * @return if it does.
      */
      public boolean hasNext() {
      
         return (current < size());
      }
      
      /**
      * returns next element of the list.
      * @return the next element.
      */
      public T next() {
      
         if	(!hasNext()) {
            throw new NoSuchElementException();
         }
         return items[current++];
      }
      
      /**
      * gets overrided :'(.
      */
      public void remove() {
      
         throw new UnsupportedOperationException();
      }
   
   }   
}