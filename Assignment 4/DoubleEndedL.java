import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * This class implements DoubleEndedList
 * @author Seth Kinsaul (smk0036@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version October 20, 2019
 */
public class DoubleEndedL<T> implements DoubleEndedList<T> {
   private int size;
   private T[] arr;
   int size1 = 1;
   private Node front;
   private Node last;
   int element;
   
   @SuppressWarnings("unchecked")
   public DoubleEndedL() {
      front = null;
      size = 0;
      element = 0;
   }
   @Override
   public void addFirst(T element) {
      if (element == null) {
         throw new IllegalArgumentException();
      }
   
      Node n = new Node(element);
      
      if (size() == 0) {
      
         front = n;
         last = n;
      }
      
      else {
         n.next = front;
         front = n;
      }
      
      size++;  
   }         
   @Override
   public void addLast(T element) {
      if (element == null) {
         throw new IllegalArgumentException();
      }
   
      Node n = new Node(element);
      n.element = element;
      
      if (size == 0) {
         front = n;
         last = n;
      }
      
      else {
         last.next = n;
         last = n;
      }
      
      size++;
   }   
   @Override
   public T removeLast() {
      if (size == 0) {
         return null;
      }
      
      else if (size == 1) {
      
         T deleted = front.element;
         front = null;
         last = null;
         size--;
         return deleted;
      }
      
      else {
         Node n = front;
      
         while (n.next.next != null) {
            n = n.next;
         }
      
      
         T deleted = n.next.element;
         n.next = null;
         last = n;
         size--;
         return deleted;
      }
      
   }   
   @Override
   public T removeFirst() {
      if (size == 0) {
         return null;
      }
      
      T deleted = front.element;
      front = front.next;
      size--;
   
      return deleted;
   }
   @Override
   public int size() {
      return element;
   }

   @Override
   public boolean isEmpty() {
      return (element == 0);
   }

   @Override
   public Iterator<T> iterator() {
      return new ChainIterator();
   }

   private class Node {
      private T element;
      private Node next;
   
      public Node(T t) {
      
         element = t;
      }
   
      public Node(T t, Node node) {
      
         element = t;
         next = node;
      }
   
   
   }

   private class ChainIterator implements Iterator<T> {
      private Node current = front;
   
      public T next() {
      
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         
         T result = current.element;
         current = current.next;
         return result;
      }
   
      public boolean hasNext() {
      
         return current != null;
      }
   
      public void remove() {
      
         throw new UnsupportedOperationException();
      }
   }

}