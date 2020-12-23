package day23;

import java.util.HashMap;
import java.util.Map;

public class NodeMap<K, V> {

	private Map<K, Node<K, V>> nodes = new HashMap<>();
	private Node<K, V> lastAdded;
	private final boolean loops;

	public NodeMap(boolean loops) {
		this.loops = loops;
	}

	public void add(K key) {
		this.add(key, null);
	}

	public void add(K key, V value) {

		Node<K, V> node = new Node<>();
		node.setKey(key);
		node.setValue(value);

		this.add(node);
	}

	public void add(Node<K, V> node) {
		this.add(node, this.lastAdded);
	}

	public void add(Node<K, V> node, Node<K, V> target) {
		if (node == null) {
			return;
		}
		this.nodes.put(node.getKey(), node);

		if (target != null) {

			if (target.getRight() != null) {
				target.getRight().setLeft(node);
				node.setRight(target.getRight());
			}

			target.setRight(node);
			node.setLeft(target);

		} else if (this.loops) {
			node.setRight(node);
			node.setLeft(node);
		}

		this.lastAdded = node;
	}

	public Node<K, V> remove(K key) {
		Node<K, V> node = this.nodes.remove(key);

		if (node != null) {
			if (node.getLeft() != null) {
				node.getLeft().setRight(node.getRight());
			}

			if (node.getRight() != null) {
				node.getRight().setLeft(node.getLeft());
			}
		}

		return node;
	}

	public boolean remove(Node<K, V> node) {

		if (node == null) {
			return false;
		}

		return this.remove(node.getKey()) != null;
	}

	public Node<K, V> get(K key) {
		return this.nodes.get(key);
	}

}
