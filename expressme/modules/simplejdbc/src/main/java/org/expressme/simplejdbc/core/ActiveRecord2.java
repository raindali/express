package org.expressme.simplejdbc.core;

import java.io.Serializable;
import java.util.List;

import org.expressme.simplejdbc.UserModel;

public class ActiveRecord2 {

	public static <T extends ActiveRecord<?, ?>> T _new(Class<T> clazz) {
		return null;
	}
	
	public static <T, ID extends Serializable> T find(ID id) {
		return null;
	}
	
	public static <T, ID extends Serializable> T find(ID... id) {
		return null;
	}
	
	public static <T, ID extends Serializable> List<T> where(String sql, ID... params) {
		return null;
	}
	
	public static void main(String[] args) {
		ActiveRecord2._new(UserModel.class).selectById(1);
	}
//	.all, .first, .last， offset
//	limit
//	order
//	select
}
