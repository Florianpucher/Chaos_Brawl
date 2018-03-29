package com.strategy_bit.chaos_brawl;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleIntegrationTest {
    @Test
    public void addition_isCorrect() {
        com.strategy_bit.chaos_brawl.Test test = new com.strategy_bit.chaos_brawl.Test();

        assertEquals(test.test(5), true);
        assertEquals(test.test(3), false);
    }
}