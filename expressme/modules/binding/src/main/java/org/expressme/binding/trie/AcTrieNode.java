package org.expressme.binding.trie;

import java.util.ArrayList;
import java.util.List;

public class AcTrieNode {
	private final String name;
	private String value;
	private AcTrieNode parent;
	private List<AcTrieNode> children;

	public AcTrieNode(String name) {
		this.name = name;
	}

	public void addChild(AcTrieNode child) {
		if (children == null) {
			children = new ArrayList<AcTrieNode>();
		}
		child.parent = this;
		children.add(child);
	}

	public AcTrieNode getChild(String name) {
		if (children == null)
			return null;
		for (AcTrieNode child : children) {
			if (child.getName().equals(name)) {
				return child;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public AcTrieNode getParent() {
		return parent;
	}

	public List<AcTrieNode> getChildren() {
		return children;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isRoot() {
		return parent == null;
	}
	
	public boolean isLeaf() {
		return children == null;
	}
}
