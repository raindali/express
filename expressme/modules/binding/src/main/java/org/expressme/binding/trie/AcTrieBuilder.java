package org.expressme.binding.trie;

import java.util.Map;

public class AcTrieBuilder {

	public static final AcTrieBuilder DEFAULT = new AcTrieBuilder();

	private AcTrieBuilder() {
	}

	public AcTrie build(Map<String, String> params) {
		final AcTrie acTrie = new AcTrie();
		buildToTransfer(acTrie, params);
		return acTrie;
	}

	private void buildToTransfer(AcTrie prTrie, Map<String, String> params) {
		for (String key : params.keySet()) {
			AcTrieNode currentNode = prTrie.getRootNode();
			String[] keyArray = key.split("\\.");
			for (int i = 0; i < keyArray.length; i++) {
				String aKey = keyArray[i];
				AcTrieNode childNode = currentNode.getChild(aKey);
				if (null != childNode) {
					if (i == keyArray.length - 1)
						childNode.setValue(params.get(key));
					else
						currentNode = childNode;
				} else {
					AcTrieNode newNode = new AcTrieNode(aKey);
					if (i == keyArray.length - 1)
						newNode.setValue(params.get(key));
					currentNode.addChild(newNode);
					currentNode = newNode;
				}
			}
		}
	}
}
