package org.child;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vinodm1986 on 7/4/18.
 */
public class AddTwoNumbersTest {


    @Test
    public void add() throws Exception {
        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        assert addTwoNumbers.add(1,2) == 3;
    }

    @Test
    public void addZero() throws Exception {
        AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
        assert addTwoNumbers.add(1,0) == 1;
    }

}