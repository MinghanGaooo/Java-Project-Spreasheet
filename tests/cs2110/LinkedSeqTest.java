package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedSeqTest {

    // Helper functions for creating lists used by multiple tests.  By constructing strings with
    // `new`, more likely to catch inadvertent use of `==` instead of `.equals()`.

    /**
     * Creates [].
     */
    static Seq<String> makeList0() {
        return new LinkedSeq<>();
    }

    /**
     * Creates ["A"].  Only uses prepend.
     */
    static Seq<String> makeList1() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B"].  Only uses prepend.
     */
    static Seq<String> makeList2() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B", "C"].  Only uses prepend.
     */
    static Seq<String> makeList3() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("C"));
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "A", "B", "C"].  Only uses prepend.
     */
    private static Seq<String> makeList4() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("C"));
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates a list containing the same elements (in the same order) as array `elements`.  Only
     * uses prepend.
     */
    static <T> Seq<T> makeList(T[] elements) {
        Seq<T> ans = new LinkedSeq<>();
        for (int i = elements.length; i > 0; i--) {
            ans.prepend(elements[i - 1]);
        }
        return ans;
    }

    @Test
    void testConstructorSize() {
        Seq<String> list = new LinkedSeq<>();
        assertEquals(0, list.size());
    }

    @Test
    void testPrependSize() {
        // List creation helper functions use prepend.
        Seq<String> list;

        list = makeList1();
        assertEquals(1, list.size());

        list = makeList2();
        assertEquals(2, list.size());

        list = makeList3();
        assertEquals(3, list.size());
    }


    @Test
    void testToString() {
        Seq<String> list;

        list = makeList0();
        assertEquals("[]", list.toString());

        list = makeList1();
        assertEquals("[A]", list.toString());

        list = makeList2();
        assertEquals("[A, B]", list.toString());

        list = makeList3();
        assertEquals("[A, B, C]", list.toString());
    }


    @Test
    void testContains(){
        Seq<String> list;

        //the list does not contain elem
        list = makeList1();
        assertEquals(false, list.contains("B"));

        //contain elem once
        list = makeList2();
        assertEquals(true, list.contains("B"));

        //contain elem more than once
        list = makeList4();
        assertEquals(true, list.contains("A"));
    }


    @Test
    void testGet(){
        Seq<String> list;
        list = makeList3();

        //get from index of 0
        assertEquals("A", list.get(0));

        //get from index of 1
        assertEquals("B", list.get(1));

        //get from index of 2
        assertEquals("C", list.get(2));
    }


    @Test
    void testAppend(){
        Seq<String> list;

        //append to the list of size 0
        list = makeList0();
        list.append("A");
        assertEquals("[A]", list.toString());

        //append to the list of size 1
        list = makeList1();
        list.append("B");
        assertEquals("[A, B]", list.toString());

        //append to the list of size 2
        list = makeList2();
        list.append("C");
        assertEquals("[A, B, C]", list.toString());
    }


    @Test
    void testInsertBefore(){
        Seq<String> list;

        //when successor is at position 1
        list = makeList3();
        list.insertBefore("A", "A");
        assertEquals("[A, A, B, C]", list.toString());

        //when successor is at position 3
        list.insertBefore("B", "B");
        assertEquals("[A, A, B, B, C]", list.toString());

        //when successor is at the last position
        list.insertBefore("C", "C");
        assertEquals("[A, A, B, B, C, C]", list.toString());
    }


    @Test
    void testRemove(){
        Seq<String> list;

        //the list does not contain elem
        list = makeList0();
        assertEquals(false, list.remove("A"));

        //the list contains elem once
        list = makeList3();
        assertEquals(true, list.remove("A"));
        assertEquals("[B, C]", list.toString());

        //the list contains elem more than once
        list = makeList4();
        assertEquals(true, list.remove("A"));
        assertEquals("[A, B, C]", list.toString());
    }


    @Test
    void testEquals(){
        Seq<String> list;

        //compare the list with itself
        list = makeList0();
        assertEquals(true, list.equals(list));

        //compare the list with another list
        list = makeList1();
        assertEquals(false, list.equals(makeList2()));

        //compare the list with another list
        assertEquals(false, list.equals(makeList3()));
    }


    /*
     * There is no need to read the remainder of this file for the purpose of completing the
     * assignment.  We have not yet covered `hashCode()` or `assertThrows()` in class.
     */
    @Test
    void testHashCode() {
        assertEquals(makeList0().hashCode(), makeList0().hashCode());

        assertEquals(makeList1().hashCode(), makeList1().hashCode());

        assertEquals(makeList2().hashCode(), makeList2().hashCode());

        assertEquals(makeList3().hashCode(), makeList3().hashCode());
    }

    @Test
    void testIterator() {
        Seq<String> list;
        Iterator<String> it;

        list = makeList0();
        it = list.iterator();
        assertFalse(it.hasNext());
        Iterator<String> itAlias = it;
        assertThrows(NoSuchElementException.class, () -> itAlias.next());

        list = makeList1();
        it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertFalse(it.hasNext());

        list = makeList2();
        it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertTrue(it.hasNext());
        assertEquals("B", it.next());
        assertFalse(it.hasNext());
    }
}
