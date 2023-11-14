
/*
file name:      LinkedList.java
Authors:        Anh Nguyen
last modified:  3/15/2023
purpose: Set the behavior of the LinkedList class.
*/
import java.util.Iterator; // defines the Iterator interface
import java.util.ArrayList;

public class LinkedList<T> implements Queue<T>, Stack<T>, Iterable<T> {

    // nested class that represents a node in the linked list
    private static class Node<T> {
        /** The next node after this */
        private Node<T> next;
        /** The previous node before this */
        private Node<T> prev;
        /** Data this node holds */
        T data;

        /**
         * The constructor for the Node
         * 
         * @param item the data to store in the node
         * @param next the node after this node
         * @param prev the node before this node
         */
        public Node(T item, Node<T> next, Node<T> prev) {
            this.data = item;
            this.next = next;
            this.prev = prev;
        }

        /**
         * Returns the data stored in this node
         * 
         * @return the data stored in this node
         */
        public T getData() {
            return data;
        }

        /**
         * Sets the next node after this node
         * 
         * @param n the node to set as the next node
         */
        public void setNext(Node<T> n) {
            this.next = n;
        }

        /**
         * Sets the previous node before this node
         * 
         * @param n the node to set as the previous node
         */
        public void setPrev(Node<T> n) {
            prev = n;
        }

        /**
         * Returns the next node after this node
         * 
         * @return the next node after this node
         */
        public Node<T> getNext() {
            return next;
        }

        /**
         * Returns the previous node before this node
         * 
         * @return the previous node before this node
         */
        public Node<T> getPrev() {
            return prev;
        }
    }

    /* The number of items in the list */
    private int size;
    /* The very first node in the list */
    private Node<T> head;
    /* The very last node in the list */
    private Node<T> tail;

    /**
     * Constructor for an empty linked list
     */
    public LinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * Private class that implements the Iterator interface
     * for this linked list
     */
    private class LLIterator implements Iterator<T> {
        private Node<T> walker;

        public LLIterator(Node<T> head) {
            walker = LinkedList.this.head;
        }

        /**
         * Returns true if there is a next node in the iteration
         * 
         * @return true if there is a next node in the iteration
         */
        public boolean hasNext() {
            return walker != null;
        }

        /**
         * Returns the data stored in the next node of the iteration
         * 
         * @return the data stored in the next node of the iteration
         */
        public T next() {
            T toReturn = walker.getData();
            walker = walker.getNext();
            return toReturn;
        }

        /**
         * Not implemented
         */
        public void remove() {
        }
    }

    /**
     * Returns an iterator over the elements in this list.
     *
     * @return an iterator over the elements in this list.
     */
    public Iterator<T> iterator() {
        return new LLIterator(this.head);
    }

    /**
     * Adds an element to the beginning of the list
     *
     * @param item the element to add to the list
     */
    public void addFirst(T item) {
        Node<T> newNode = new Node<T>(item, head, null);
        if (size == 0) {
            tail = newNode;
        } else {
            head.setPrev(newNode);
        }
        head = newNode;
        size++;
    }

    /**
     * Adds an element to the end of the list
     *
     * @param item the element to add to the list
     */
    public void addLast(T item) {
        Node<T> newNode = new Node<T>(item, null, tail);
        if (size == 0) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;

    }

    /**
     * Adds an element to the list at the specified index
     *
     * @param index the index at which to add the element
     * @param item  the element to add to the list
     */
    public void add(int index, T item) {
        // If the index is 0, add the element to the beginning of the list
        if (index == 0) {
            addFirst(item);
            return;
        }
        // If the index is the last element, add the element to the end of the list
        if (index == size) {
            addLast(item);
            return;
        }
        // If the index is closer to the beginning of the list, start from the beginning
        if (index <= size / 2) {
            Node<T> walker = head;
            for (int i = 0; i < index - 1; i++)
                walker = walker.getNext();
            Node<T> newNode = new Node<T>(item, walker.next, walker);
            walker.getNext().setPrev(newNode);
            walker.setNext(newNode);
            size++;
        } else {
            // If the index is closer to the end of the list, start from the end
            Node<T> walker = tail;
            for (int i = size - 1; i > index; i--)
                walker = walker.prev;
            Node<T> newNode = new Node<T>(item, walker, walker.prev);
            walker.getPrev().setNext(newNode);
            walker.setPrev(newNode);
            size++;
        }
    }

    /**
     * Gets the element with the given index
     * 
     * @param index the index of the required element
     * @return the element
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("index out of hand");
        }
        if (index <= size / 2) {
            Node<T> walker = head;
            for (int i = 0; i < index; i++) {
                walker = walker.next;
            } // at the end of this for-loop, walker is the
              // indexth node in the list (accounting for 0-indexing)
            return walker.getData();
        } else {
            Node<T> walker = tail;
            for (int i = size - 1; i > index; i--) {
                walker = walker.getPrev();
            } // at the end of this for-loop, walker is the
              // indexth node in the list (accounting for 0-indexing)
            return walker.getData();
        }
    }

    /**
     * Clears the list
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes the first item
     * 
     * @return the removed first item
     */
    public T removeFirst() {
        T toReturn = head.getData();
        head = head.getNext();
        if (size == 1) {
            tail = null;
        } else {
            head.setPrev(null);
        }
        size--;
        return toReturn;
    }

    /**
     * Removes the last item
     * 
     * @return the removed last item
     */
    public T removeLast() {
        T toReturn = tail.getData();
        tail = tail.getPrev();
        if (size == 1) {
            head = null;
        } else {
            tail.setNext(null);
        }
        size--;
        return toReturn;
    }

    /**
     * Removes a specific item from the list
     * 
     * @param index the index of the item to be removed
     * @return the removed item
     */
    public T remove(int index) {
        if (index == 0) {
            return removeFirst();
        }
        if (index == size - 1) {
            return removeLast();
        }
        if (index <= size / 2) {
            Node<T> walker = head;
            for (int i = 0; i < index; i++)
                walker = walker.getNext();
            walker.getPrev().setNext(walker.getNext());
            walker.getNext().setPrev(walker.getPrev());
            size--;
            return walker.getData();
        } else {
            Node<T> walker = tail;
            for (int i = size - 1; i > index; i--) {
                walker = walker.getPrev();
            }
            walker.getPrev().setNext(walker.getNext());
            walker.getNext().setPrev(walker.getPrev());
            size--;
            return walker.getData();
        }
    }

    /**
     * Checks if the list has the Object o
     * 
     * @param o the Object o
     * @return boolean that represents if o is contained
     */
    public boolean contains(Object o) {
        Node<T> walker = head;
        for (int i = 0; i < size; i++) {
            if (o == walker.getData()) {
                return true;
            }
            walker = walker.getNext();
        }
        return false;
    }

    /**
     * Checks if the Object o and the list are the same
     * 
     * @param o the Object o
     * @return boolean that represents if they're equal
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof LinkedList)) {
            return false;
        }
        // If I have reached this line, o must be a LinkedList
        LinkedList<T> oAsALinkedList = (LinkedList<T>) o;
        // Now I have a reference to something Java knows is a LinkedList!
        int smallestLength = oAsALinkedList.size() < this.size() ? oAsALinkedList.size() : this.size();
        for (int i = 0; i < smallestLength; i++) {
            if (oAsALinkedList.get(i) != this.get(i))
                return false;
        }
        return true;
    }

    /**
     * Returns the size of the linked list
     * 
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * Returns a boolean to represent if its empty
     * 
     * @return the boolean
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the ArrayList represenatation of the LinkedList
     * 
     * @return ArrayList representation
     */
    public ArrayList<T> toArrayList() {
        ArrayList<T> array = new ArrayList<T>();
        for (int i = 0; i < size; i++) {
            array.add(this.get(i));
        }
        return array;
    }

    /**
     * 
     * Adds the specified element to the end of this queue.
     * 
     * @param item the element to add
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void offer(T item) {
        addLast(item);
    }

    /**
     * 
     * Retrieves, but does not remove, the head of queue/stack.
     * 
     * @return the head of this queue/stack, or null if this queue/stack is empty
     */
    @Override
    public T peek() {
        return head.getData();
    }

    /**
     * 
     * Retrieves and removes the head of this queue, or returns null if this queue
     * is empty.
     * 
     * @return the head of this queue, or null if this queue is empty
     */
    @Override
    public T poll() {
        return removeFirst();
    }

    /**
     * 
     * Adds the specified element to the top of this stack.
     * 
     * @param item the element to add
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void push(T item) {
        addFirst(item);
    }

    /**
     * 
     * Retrieves and removes the head of this stack, or returns null if this stack
     * is empty.
     * 
     * @return the head of this stack, or null if this stack is empty
     */
    @Override
    public T pop() {
        return removeFirst();
    }
}