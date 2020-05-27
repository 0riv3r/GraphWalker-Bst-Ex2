package com.cyberark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("to enable view of the MBT coverage")
public class UnitTests {

    public static Bst<Integer> target;
    public static int expectedTreeSize;
    public static int initialValue1, initialValue2, initialValue3;
    public static int initialValue4, initialValue5, initialValue6;
    public static int initialValue7, initialValue8, initialValue9;
    public static int initialTreeSize;

    @BeforeEach
    public void setup() {
        target = new Bst<Integer>();
        initialValue1 = 8;
        initialValue2 = 14;
        initialValue3 = 4;
        initialValue4 = 13;
        initialValue5 = 6;
        initialValue6 = 7;
        initialValue7 = 10;
        initialValue8 = 1;
        initialValue9 = 3;
        
        target.add(initialValue1);
        target.add(initialValue2);
        target.add(initialValue3);
        target.add(initialValue4);
        target.add(initialValue5);
        target.add(initialValue6);
        target.add(initialValue7);
        target.add(initialValue8);
        target.add(initialValue9);

        initialTreeSize = 9;
    }

    @Test
    public void addShouldIncreaseTreeSize() {
        target.add(42);
        assertEquals(initialTreeSize+1, target.nodes().size(), "After ading a value the tree size must be increased by 1");
    }

    @Test
    public void addDuplicatesShouldKeepTreeSize() {
        target.add(initialValue1);
        assertEquals(initialTreeSize, target.nodes().size(), "After ading a duplicate value the tree size must remain unchanged");
    }

    @Test
    public void deleteLeafShouldReturnTrueAndDecreseTreeSize() {
        target.delete(initialValue9);
        assertEquals(initialTreeSize-1, target.nodes().size(), "Deleting a leaf value, must return 'true' and the tree size must be decreased by 1");
    }

    @Test
    public void deleteNotLeafShouldReturnFalseAndKeepTreeSize() {
        target.delete(initialValue1);
        assertEquals(initialTreeSize, target.nodes().size(), "Deleting a non-leaf value, must return 'false' and the tree size must remain unchanged");
    }

    @Test
    public void insertedValueMustBeFound() {
        assertTrue(target.find(initialValue7), "inserted value must be found");
    }

    @Test
    public void notInsertedValueMustNotBeFound() {
        assertFalse(target.find(42), "Not inserted value must not be found");
    }

    @Test
    public void isEmptyMustReturnTrueForEmptyTree() {
        Bst<Integer> empty = new Bst<Integer>();
        assertTrue(empty.isEmpty(), "isEmpty() must return true for an empty tree");
    }

    @Test
    public void isEmptyMustReturnFalseForNonEmptyTree() {
        assertFalse(target.isEmpty(), "isEmpty() must return false for a non-empty tree");
    }

}