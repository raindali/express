package org.expressme.binding.trie;

public class AcTrie {
	private final AcTrieNode root = new AcTrieNode("#");

	public AcTrieNode getRootNode() {
		return root;
	}

	public AcTrieMatcher analyze(AcTrieNode node) {
		final AcTrieMatcher matcher = new AcTrieMatcher();
		if (node == null || node.getChildren() == null)
			return null;
		for (AcTrieNode child : node.getChildren()) {
			if (child.isLeaf())
				matcher.addLeaf(child);
			else
				matcher.addBranch(child);
		}
		return matcher;
	}
}
