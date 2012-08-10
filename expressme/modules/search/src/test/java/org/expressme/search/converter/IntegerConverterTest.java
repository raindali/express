package org.expressme.search.converter;

import static org.junit.Assert.*;

import org.expressme.search.converter.IntegerConverter;
import org.junit.Before;
import org.junit.Test;

public class IntegerConverterTest {

    IntegerConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new IntegerConverter();
    }

    @Test
    public void testToStringObject() {
        assertEquals("-99", converter.toString(new Integer(-99)));
        assertEquals("-1", converter.toString(new Integer(-1)));
        assertEquals("0", converter.toString(new Integer(0)));
        assertEquals("1", converter.toString(new Integer(1)));
        assertEquals("2", converter.toString(new Integer(2)));
        assertEquals("99", converter.toString(new Integer(99)));
        assertEquals("0", converter.toString(null));
    }

    @Test
    public void testToObject() {
        assertEquals(new Integer(-99), converter.toObject("-99"));
        assertEquals(new Integer(-1), converter.toObject("-1"));
        assertEquals(new Integer(0), converter.toObject("0"));
        assertEquals(new Integer(1), converter.toObject("1"));
        assertEquals(new Integer(2), converter.toObject("2"));
        assertEquals(new Integer(99), converter.toObject("99"));
        assertEquals(new Integer(0), converter.toObject(null));
        assertEquals(new Integer(0), converter.toObject(""));
        assertEquals(new Integer(0), converter.toObject("x"));
        assertEquals(new Integer(0), converter.toObject("@#$%^"));
    }

}
