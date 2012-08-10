package org.expressme.binding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.binding.mapper.BeanRowMapper;
import org.expressme.binding.mapper.MappingException;
import org.expressme.binding.mapper.RowMapper;
import org.expressme.binding.trie.AcTrie;
import org.expressme.binding.trie.AcTrieBuilder;
import org.expressme.binding.trie.AcTrieMatcher;
import org.expressme.binding.trie.AcTrieNode;

/**
 * Execute Binding.
 * 
 * @author Xiaoli (mengfan0871@gmail.com)
 */
public class BindingExecutor {

	private static final Log log = LogFactory.getLog(BindingExecutor.class);
	private static final BindingExecutor INSTANCE = new BindingExecutor();
	private static final Map<Class<?>, RowMapper> mapper = new HashMap<Class<?>, RowMapper>();

	private BindingExecutor() {
	}

	public static <T> T bindParameters(Class<T> clazz, Map<String, String> params) throws MappingException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		AcTrie acTrie = AcTrieBuilder.DEFAULT.build(params);
		if (log.isDebugEnabled()) {
		}
		T result = INSTANCE.matcher(acTrie, acTrie.getRootNode(), clazz);
		return result;
	}

	<T> T matcher(AcTrie acTrie, AcTrieNode acTrieNode, Class<?> clazz) throws MappingException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		AcTrieMatcher acTrieMatcher = acTrie.analyze(acTrieNode);
		// 缓存clazz
		RowMapper rowMapper = mapper.get(clazz);
		if (rowMapper == null) {
			rowMapper = new BeanRowMapper(clazz);
			mapper.put(clazz, rowMapper);
		}
		// 对root节点单独处理
		if (acTrieMatcher == null && acTrie.getRootNode().equals(acTrieNode)) {
			T result = rowMapper.mapRow(null, new String[] {});
			return result;
		}
		Map<String, String> leaves = acTrieMatcher.getLeavesResults();
		T result = rowMapper.mapRow(leaves, leaves.keySet().toArray(new String[leaves.size()]));
		List<String> branches = acTrieMatcher.getBranchesResults();
		for (String branch : branches) {
			Method method = findSetterByPropertyName(clazz, branch);
			if (method == null)
				continue;
			rowMapper = new BeanRowMapper(method.getParameterTypes()[0]);
			Object o = matcher(acTrie, acTrieNode.getChild(branch), method.getParameterTypes()[0]);
			method.invoke(result, o);
		}
		return result;
	}

	Method findSetterByPropertyName(Class<?> clazz, String prop) {
		String n = Character.toUpperCase(prop.charAt(0)) + prop.substring(1);
		String setter1 = "set" + n;
		Method[] ms = clazz.getMethods();
		for (Method m : ms) {
			if (isSetter(m) && setter1.equals(m.getName())) {
				return m;
			}
		}
		return null;
	}

	boolean isSetter(Method m) {
		if (!m.getName().startsWith("set"))
			return false;
		if (m.getReturnType() != void.class)
			return false;
		if (m.getParameterTypes() == null)
			return false;
		int mod = m.getModifiers();
		return Modifier.isPublic(mod) && !Modifier.isStatic(mod);
	}
}
