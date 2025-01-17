package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Assignment metadata
 * Name(s) and NetID(s): Minghan Gao, mg2328
 * Hours spent on assignment: 10
 */

/**
 * A list of elements of type `T` implemented as a singly linked list.  Null elements are not
 * allowed.
 */
public class LinkedSeq<T> implements Seq<T> {

    /**
     * Number of elements in the list.  Equal to the number of linked nodes reachable from `head`.
     */
    private int size;

    /**
     * First node of the linked list (null if list is empty).
     */
    private Node<T> head;

    /**
     * Last node of the linked list starting at `head` (null if list is empty).  Next node must be
     * null.
     */
    private Node<T> tail;


    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        assert size >= 0;
        if (size == 0) {
            assert head == null;
            assert tail == null;
        } else {
            assert head != null;
            assert tail != null;

            int countNodes = 0;
            Node<T> current = head;
            while(current != null){
                countNodes++;
                current = current.next();
                }

            assert countNodes == size;

            Node<T> lastNode = head;
            while(lastNode.next() != null){
                lastNode = lastNode.next();
            }
            assert lastNode.equals(tail);
        }
    }


    /**
     * Create an empty list.
     */
    public LinkedSeq() {
        size = 0;
        head = null;
        tail = null;

        assertInv();
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public void prepend(T elem) {
        assertInv();
        assert elem != null;

        head = new Node<>(elem, head);
        // If list was empty, assign tail as well
        if (tail == null) {
            tail = head;
        }
        size += 1;

        assertInv();
    }


    /**
     * Return a text representation of this list with the following format: the string starts with
     * '[' and ends with ']'.  In between are the string representations of each element, in
     * sequence order, separated by ", ".
     * <p>
     * Example: a list containing 4 7 8 in that order would be represented by "[4, 7, 8]".
     * <p>
     * Example: a list containing two empty strings would be represented by "[, ]".
     * <p>
     * The string representations of elements may contain the characters '[', ',', and ']'; these
     * are not treated specially.
     */
    @Override
    public String toString() {
        String str = "[";
        Node<T> eachNode = head;
        while(eachNode != null){
            str+=eachNode.data();
            eachNode = eachNode.next();
            if(eachNode!=null){
                str += ", ";
            }
        }
        str += "]";
        return str;
    }


    @Override
    public boolean contains(T elem) {
        assert elem != null;

        Node<T> node = head;
        while(node != null){
            if(node.data().equals(elem)){return true;}
            node = node.next();
        }
        return false;
    }


    @Override
    public T get(int index) {
        assert index >= 0;
        assert index < size;

        int accum = 0;
        Node<T> node = head;
        while(node != null){
            if(index == accum){return node.data();}
            accum++;
            node = node.next();
        }
        throw new RuntimeException();
    }


    @Override
    public void append(T elem) {
        assert elem != null;

        if(size == 0){
            Node<T> newNode = new Node<>(elem, null);
            head = newNode;
            tail = newNode;
            size++;
        }else{
            Node<T> newNode = new Node<>(elem, null);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }

        assertInv();
    }


    @Override
    public void insertBefore(T elem, T successor) {
        assert contains(successor);
        assert elem != null;
        assert successor != null;

        Node<T> newNode = head;
        Node<T> previous = null;
        while(newNode!=null && !(newNode.data().equals(successor))){
            previous = newNode;
            newNode = newNode.next();
        }
        if(newNode != null){
            if(previous != null){
                Node<T> node = new Node<>(elem, newNode);
                previous.setNext(node);
            }else{
                Node<T> node = new Node<>(elem, newNode);
                head = node;
            }
        }
        size++;
        assertInv();
    }


    @Override
    public boolean remove(T elem) {
        assert elem != null;

        boolean remove = false;

        Node<T> node = head;
        Node<T> previous = null;
        while ((node != null) && (!node.data().equals(elem))) {
            previous = node;
            node = node.next();
        }
        if(node != null){
            if(previous != null){
                previous.setNext(node.next());
            }else{
                head = node.next();
            }
            size--;

            remove = true;
        }
        assertInv();
        return remove;
    }


    /**
     * Return whether this and `other` are `LinkedSeq`s containing the same elements in the same
     * order.  Two elements `e1` and `e2` are "the same" if `e1.equals(e2)`.  Note that `LinkedSeq`
     * is mutable, so equivalence between two objects may change over time.  See `Object.equals()`
     * for additional guarantees.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof LinkedSeq)) {
            return false;
        }
        LinkedSeq otherSeq = (LinkedSeq) other;
        Node<T> currNodeThis = head;
        Node currNodeOther = otherSeq.head;

        boolean equal = true;
        if(size != otherSeq.size()){
            return false;
        }
        while(currNodeThis != null && currNodeOther != null){
            equal = equal && currNodeThis.data().equals(currNodeOther.data());
            currNodeThis = currNodeThis.next();
            currNodeOther = currNodeOther.next();
        }
        return equal;
    }

    /*
     * There is no need to read the remainder of this file for the purpose of completing the
     * assignment.  We have not yet covered the implementation of these concepts in class.
     */

    /**
     * Returns a hash code value for the object.  See `Object.hashCode()` for additional
     * guarantees.
     */
    @Override
    public int hashCode() {
        // Whenever overriding `equals()`, must also override `hashCode()` to be consistent.
        // This hash recipe is recommended in _Effective Java_ (Joshua Bloch, 2008).
        int hash = 1;
        for (T e : this) {
            hash = 31 * hash + e.hashCode();
        }
        return hash;
    }

    /**
     * Return an iterator over the elements of this list (in sequence order).  By implementing
     * `Iterable`, clients can use Java's "enhanced for-loops" to iterate over the elements of the
     * list.  Requires that the list not be mutated while the iterator is in use.
     */
    @Override
    public Iterator<T> iterator() {
        assertInv();

        // Return an instance of an anonymous inner class implementing the Iterator interface.
        // For convenience, this uses Java features that have not eyt been introduced in the course.
        return new Iterator<>() {
            private Node<T> next = head;

            public T next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T result = next.data();
                next = next.next();
                return result;
            }

            public boolean hasNext() {
                return next != null;
            }
        };
    }
}
