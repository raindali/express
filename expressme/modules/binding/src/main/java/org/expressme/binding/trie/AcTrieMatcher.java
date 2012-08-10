package org.expressme.binding.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcTrieMatcher {

	private final List<AcTrieNode> leaves = new ArrayList<AcTrieNode>();
	private final List<AcTrieNode> branches = new ArrayList<AcTrieNode>();

	public Map<String, String> getLeavesResults() {
		Map<String, String> results = new HashMap<String, String>();
		for (AcTrieNode leaf : leaves) {
			results.put(leaf.getName(), leaf.getValue());
		}
		return results;
	}

	public List<String> getBranchesResults() {
		List<String> results = new ArrayList<String>();
		for (AcTrieNode leaf : branches) {
			results.add(leaf.getName());
		}
		return results;
	}

	public void addLeaf(AcTrieNode leaf) {
		leaves.add(leaf);
	}

	public void addBranch(AcTrieNode branch) {
		branches.add(branch);
	}
}
