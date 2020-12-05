package com.example.tictactoe;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void checkWinner() {
        MainActivity testMainActivity = new MainActivity();
        assertFalse(testMainActivity.checkWinner());
        testMainActivity.gameState = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2};
        assertTrue(testMainActivity.checkWinner());
        testMainActivity.gameState = new int[]{2, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2};
        assertTrue(testMainActivity.checkWinner());
        testMainActivity.gameState = new int[]{2, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2};
        assertFalse(testMainActivity.checkWinner());
    }
}