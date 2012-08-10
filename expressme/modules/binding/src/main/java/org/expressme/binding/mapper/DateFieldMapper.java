package org.expressme.binding.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Mapping SQL types DATE, TIME and TIMESTAMP to {@link java.util.Date}.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DateFieldMapper implements FieldMapper<Date> {

    public static final DateFieldMapper INSTANCE = new DateFieldMapper();
    public static final DateFormat ISO_DATETIME_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//    var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
//    var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
    /**
     * The parameter <code>nullable</code> will be ignored, because no default 
     * value for java.util.Date object. If the value is null, "null" will be 
     * returned.
     */
    public Date mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
        String dt = rs.get(key);
        if (dt !=null && dt.length() >= 10) {
        	try {
        		return ISO_DATETIME_TIME_FORMAT.parse(dt);
        	} catch (ParseException e) {
        		try {
					return ISO_DATE_FORMAT.parse(dt);
				} catch (ParseException e1) {
					throw new MappingException("Cannot map String type " + dt + " to java.util.Date.");
				}
        	}
        }
        throw new MappingException("Cannot map String type " + dt + " to java.util.Date.");
    }

}
