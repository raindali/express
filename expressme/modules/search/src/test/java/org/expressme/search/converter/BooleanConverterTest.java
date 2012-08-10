package org.expressme.search.converter;

import static org.junit.Assert.*;

import org.expressme.search.converter.BooleanConverter;
import org.junit.Before;
import org.junit.Test;

public class BooleanConverterTest {

    BooleanConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new BooleanConverter();
    }

    @Test
    public void testToStringObject() {
        assertEquals("true", converter.toString(Boolean.TRUE));
        assertEquals("false", converter.toString(Boolean.FALSE));
        assertEquals("false", converter.toString(null));
    }

    @Test
    public void testToObject() {
        assertEquals(Boolean.TRUE, converter.toObject("true"));
        assertEquals(Boolean.TRUE, converter.toObject("True"));
        assertEquals(Boolean.FALSE, converter.toObject("false"));
        assertEquals(Boolean.FALSE, converter.toObject(null));
        assertEquals(Boolean.FALSE, converter.toObject(""));
        assertEquals(Boolean.FALSE, converter.toObject("x"));
    }

}
