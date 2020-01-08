package com.alirnp.salizsalon;

import com.alirnp.salizsalon.Utils.Constants;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        //assertEquals(4, Constants.state.MAIN.getStatus());
        assertEquals(4, Constants.state.HEADER.getStatus());
    }
}