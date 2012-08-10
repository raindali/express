package org.expressme.search.converter;

import static org.junit.Assert.*;

import org.expressme.search.converter.LongConverter;
import org.junit.Before;
import org.junit.Test;

public class LongConverterTest {

    LongConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new LongConverter();
    }

    @Test
    public void testToStringObject() {
        assertEquals("-99", converter.toString(new Long(-99)));
        assertEquals("-1", converter.toString(new Long(-1)));
        assertEquals("0", converter.toString(new Long(0)));
        assertEquals("1", converter.toString(new Long(1)));
        assertEquals("2", converter.toString(new Long(2)));
        assertEquals("99", converter.toString(new Long(99)));
        assertEquals("0", converter.toString(null));
    }

    @Test
    public void testToObject() {
        assertEquals(new Long(-99), converter.toObject("-99"));
        assertEquals(new Long(-1), converter.toObject("-1"));
        assertEquals(new Long(0), converter.toObject("0"));
        assertEquals(new Long(1), converter.toObject("1"));
        assertEquals(new Long(2), converter.toObject("2"));
        assertEquals(new Long(99), converter.toObject("99"));
        assertEquals(new Long(0), converter.toObject(null));
        assertEquals(new Long(0), converter.toObject(""));
        assertEquals(new Long(0), converter.toObject("x"));
        assertEquals(new Long(0), converter.toObject("@#$%^"));
    }

}
