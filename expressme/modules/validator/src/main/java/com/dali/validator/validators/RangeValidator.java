/**
 * 
 */
package com.dali.validator.validators;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dali.validator.data.Valid;

/**
 * 类描述
 *
 * @author xiaoli
 * @since  2009-8-20
 */
public class RangeValidator extends AbstractValidator {
	public RangeValidator() {
		super("range");
	}
	@Override
	public boolean validatorInternal(Object object, String property, Valid valid) {
		Object obj = getProperty(object, property);
		if (isNull(obj))
			return false;
		String[] num = getParmeter().split(":");
		if (obj instanceof Number && num.length == 2) {
			BigDecimal b = new BigDecimal(obj.toString());
			if (!"".equals(num[0])) {
				BigDecimal b1 = new BigDecimal(num[0]);
				if (b.compareTo(b1) <= 0) {
					return false;
				}
			}
			if (!"".equals(num[1])) {
				BigDecimal b2 = new BigDecimal(num[1]);
				if (b.compareTo(b2) >= 0) {
					return false;
				}
			}
		} else if (obj instanceof Date && num.length == 2) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				if (!"".equals(num[0])) {
					if (((Date)obj).compareTo(format.parse(num[0])) <=0) {
						return false;
					}
				}
				if (!"".equals(num[1])) {
					if (((Date)obj).compareTo(format.parse(num[1])) >=0) {
						return false;
					}
				}
			} catch (ParseException e) {return false;}
		}
		return true;
	}
	public static void main(String[] args) {
		args = ":90".split(":");
		System.out.println(args[0]);
		System.out.println(args[1]);
	}
}
