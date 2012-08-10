package org.expressme.examples.typecho.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.expressme.examples.typecho.entity.Comments;
import org.expressme.examples.typecho.entity.Contents;
import org.expressme.examples.typecho.entity.Relationships;
import org.junit.Test;

public final class SqlG {

	public static String select(String[] name, String table, String prefix) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		for (int i = 0; i < name.length; i++) {
			sb.append(name[i]);
			sb.append(", ");
		}
		sb.delete(sb.lastIndexOf(","), sb.length());
		sb.append(" from ");
		sb.append(table);
		sb.append(" where ");
		return sb.toString();
	}

	public static String update(String[] name, String table, String prefix) {
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(table);
		sb.append(" set ");
		for (int i = 0; i < name.length; i++) {
			sb.append(name[i]);
			sb.append("=");
			sb.append(":");
			if (prefix != null) {
				sb.append(prefix);
				sb.append(".");
			}
			sb.append(name[i]);
			sb.append(", ");
		}
		sb.delete(sb.lastIndexOf(","), sb.length());
		sb.append(" where ");
		return sb.toString();
	}

	public static String insert(String[] name, String table, String prefix) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sb1 = new StringBuilder();
		sb.append("insert into ");
		sb.append(table);
		sb.append("(");
		sb1.append(" values (");
		for (int i = 0; i < name.length; i++) {
			sb.append(name[i]);
			sb.append(", ");
			sb1.append(":");
			if (prefix != null) {
				sb1.append(prefix);
				sb1.append(".");
			}
			sb1.append(name[i]);
			sb1.append(", ");
		}
		sb.delete(sb.lastIndexOf(","), sb.length());
		sb1.delete(sb1.lastIndexOf(","), sb1.length());
		sb.append(")");
		sb1.append(")");
		return sb.append(sb1).toString();
	}

	public static String delete(String[] name, String table, String prefix) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(table);
		sb.append(" where ");
		for (int i = 0; i < name.length; i++) {
			sb.append(name[i]);
			sb.append("=");
			sb.append(":");
			if (prefix != null) {
				sb.append(prefix);
				sb.append(".");
			}
			sb.append(name[i]);
			sb.append(" or ");
		}
		sb.delete(sb.lastIndexOf(" or "), sb.length());
		return sb.toString();
	}

	public static String[] listField(Class<?> clazz){
		List<String> fields = new ArrayList<String>();
		for (Field field : clazz.getDeclaredFields()) {
			fields.add(field.getName());
		}
		return fields.toArray(new String[fields.size()]);
	}
	
	public static void main(String[] args) {
		System.out.println(select(new String[] { "name", "mail" }, "user", "u"));
		System.out.println(select(new String[] { "name", "mail" }, "user", null));

		System.out.println(update(new String[] { "name", "mail" }, "user", "u"));
		System.out.println(update(new String[] { "name", "mail" }, "user", null));

		System.out.println(insert(new String[] { "name", "mail" }, "user", "u"));
		System.out.println(insert(new String[] { "name", "mail" }, "user", null));

		System.out.println(delete(new String[] { "name", "mail" }, "user", "u"));
		System.out.println(delete(new String[] { "name", "mail" }, "user", null));
	}

	@Test
	public void users() {
		String QUERY = "uid, username, password, mail, url, screenname, created, activated, logged, group, authcode";
		String[] QUERY_ARRAY = QUERY.replaceAll(" ", "").split(",");
		System.out.println(insert(QUERY_ARRAY, "typecho_users", "u"));
		System.out.println(update(QUERY_ARRAY, "typecho_users", "u"));
	}
	
	@Test
	public void options() {
		String QUERY = "name, user, value";
		String[] QUERY_ARRAY = QUERY.replaceAll(" ", "").split(",");
		System.out.println(select(QUERY_ARRAY, "typecho_options", "o"));
		System.out.println(insert(QUERY_ARRAY, "typecho_options", "o"));
		System.out.println(update(QUERY_ARRAY,  "typecho_options", "o"));
		System.out.println(delete(QUERY_ARRAY,  "typecho_options", "o"));
		System.out.println(Integer.MAX_VALUE + " - " + System.currentTimeMillis());
	}
	
	@Test
	public void metas() {
		String QUERY = "min, name, slug, type, description, counts, orders";
		String[] QUERY_ARRAY = QUERY.replaceAll(" ", "").split(",");
		System.out.println(select(QUERY_ARRAY, "typecho_metas", "m"));
		System.out.println(insert(QUERY_ARRAY, "typecho_metas", "m"));
		System.out.println(update(QUERY_ARRAY,  "typecho_metas", "m"));
		System.out.println(delete(QUERY_ARRAY,  "typecho_metas", "m"));
	}
	
	@Test
	public void relationships() {
		String[] QUERY_ARRAY = listField(Relationships.class);
		System.out.println(select(QUERY_ARRAY, "typecho_relationships", "r"));
		System.out.println(insert(QUERY_ARRAY, "typecho_relationships", "r"));
		System.out.println(update(QUERY_ARRAY,  "typecho_relationships", "r"));
		System.out.println(delete(QUERY_ARRAY,  "typecho_relationships", "r"));
	}

	@Test
	public void contents() {
		String[] QUERY_ARRAY = listField(Contents.class);
		System.out.println(select(QUERY_ARRAY, "typecho_contents", "c"));
		System.out.println(insert(QUERY_ARRAY, "typecho_contents", "c"));
		System.out.println(update(QUERY_ARRAY,  "typecho_contents", "c"));
		System.out.println(delete(QUERY_ARRAY,  "typecho_contents", "c"));
	}
	
	@Test
	public void comments() {
		String[] QUERY_ARRAY = listField(Comments.class);
		System.out.println(select(QUERY_ARRAY, "typecho_comments", "c"));
		System.out.println(insert(QUERY_ARRAY, "typecho_comments", "c"));
		System.out.println(update(QUERY_ARRAY,  "typecho_comments", "c"));
		System.out.println(delete(QUERY_ARRAY,  "typecho_comments", "c"));
	}
	
}
