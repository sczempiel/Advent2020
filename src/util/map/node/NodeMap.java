package util.map.node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class NodeMap<K, V> implements Map<K, V> {

	private Map<K, Node<K, V>> nodes = new HashMap<>();
	private Node<K, V> lastAdded;
	private final boolean loops;

	public NodeMap(boolean loops) {
		this.loops = loops;
	}

	@Override
	public V put(K key, V value) {
		return this.put(key, value, null);
	}

	public V put(K key, V value, K after) {

		if (key == null) {
			return null;
		}

		Node<K, V> node = new Node<>();
		node.setKey(key);
		node.setValue(value);

		Node<K, V> nodeAfter;

		if (after != null) {
			nodeAfter = this.nodes.get(after);
			if (nodeAfter == null) {
				return null;
			}
		} else {
			nodeAfter = this.lastAdded;
		}

		return this.put(node, nodeAfter);
	}

	private V put(Node<K, V> node, Node<K, V> target) {

		Node<K, V> previous = this.nodes.get(node.getKey());

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

		if (previous != null) {
			return previous.getValue();
		}

		return null;
	}

	@Override
	public V remove(Object key) {
		Node<K, V> node = this.nodes.remove(key);

		if (node != null) {
			if (node.getLeft() != null) {
				node.getLeft().setRight(node.getRight());
			}

			if (node.getRight() != null) {
				node.getRight().setLeft(node.getLeft());
			}

			return node.getValue();
		}

		return null;
	}

	@Override
	public V get(Object key) {
		Node<K, V> node = this.nodes.get(key);

		if (node == null) {
			return null;
		}

		return node.getValue();
	}

	public K getRight(K key) {
		return this.getAdjustend(key, Node::getRight);
	}

	public K getLeft(K key) {
		return this.getAdjustend(key, Node::getLeft);
	}

	private K getAdjustend(K key, Function<Node<K, V>, Node<K, V>> extractor) {
		Node<K, V> node = this.nodes.get(key);

		if (node == null) {
			return null;
		}

		Node<K, V> adjustend = extractor.apply(node);

		if (adjustend == null) {
			return null;
		}

		return adjustend.getKey();
	}

	@Override
	public int size() {
		return this.nodes.size();
	}

	@Override
	public boolean isEmpty() {
		return this.nodes.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.nodes.containsKey(key);
	}

	@Override
	public void clear() {
		this.nodes.clear();
	}

	@Override
	public boolean containsValue(Object value) {
		for (Entry<K, Node<K, V>> node : this.nodes.entrySet()) {
			if (Objects.equals(node.getValue(), value)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {

		for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}

	}

	@Override
	public Set<K> keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}

}
