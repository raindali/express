package org.expressme.binding.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.expressme.binding.BindingExecutor;
import org.expressme.binding.mapper.BeanRowMapper;
import org.expressme.binding.mapper.MappingException;
import org.expressme.binding.mapper.RowMapper;
import org.expressme.binding.trie.AcTrie;
import org.expressme.binding.trie.AcTrieBuilder;
import org.expressme.binding.trie.AcTrieMatcher;
import org.expressme.binding.trie.AcTrieNode;

public class TestBinding {
	public static void main(String[] args) throws MappingException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "---");
		params.put("name", "889898");
		params.put("xyz", "889898");
		params.put("haha", "888,999");
//		parameter.put("dog.name", "kkk");
//		parameter.put("dog.color", "black");
		params.put("dog.watch", "ttttt");
		params.put("dog.watch.id", "56789");
		AcTrie acTrie = AcTrieBuilder.DEFAULT.build(params);
		Watch watch = matcher(acTrie, acTrie.getRootNode(), Watch.class);
		System.out.println(watch);
		params.put("id", "456");
		watch =  BindingExecutor.bindParameters(Watch.class, params);
		System.out.println(watch);
	}

	static <T> T matcher(AcTrie acTrie, AcTrieNode acTrieNode, Class<?> clazz) throws MappingException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		AcTrieMatcher acTrieMatcher = acTrie.analyze(acTrieNode);
		Map<String, String> leaves = acTrieMatcher.getLeavesResults();
		RowMapper rowMapper = new BeanRowMapper(clazz);
		T result = rowMapper.mapRow(leaves, leaves.keySet().toArray(new String[leaves.size()]));
		List<String> branches = acTrieMatcher.getBranchesResults();
		for (String branch : branches) {
			Method method = findSetterByPropertyName(clazz, branch);
			Class<?>[] parameterTypes = method.getParameterTypes();
			if (method == null)
				continue;
			rowMapper = new BeanRowMapper(method.getParameterTypes()[0]);
			Object o = matcher(acTrie, acTrieNode.getChild(branch), method.getParameterTypes()[0]);
			method.invoke(result, o);
		}
		return result;
	}

	static Method findSetterByPropertyName(Class<?> clazz, String prop) {
		String n = Character.toUpperCase(prop.charAt(0)) + prop.substring(1);
		String setter1 = "set" + n;
		Method[] ms = clazz.getMethods();
		for (Method m : ms) {
			Class<?> rt = m.getReturnType();
			if (m.getParameterTypes().length > 0 && rt.equals(void.class)) {
				if (setter1.equals(m.getName())) {
					return m;
				}
			}
		}
		throw new MappingException("Cannot find getter of property '" + prop + "' of class: " + clazz.getName());
	}
}
