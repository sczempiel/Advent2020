package util.map.node;

public class Node<K, V> {

	private K key;
	private V value;

	private Node<K, V> left;
	private Node<K, V> right;

	public K getKey() {
		return this.key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return this.value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public Node<K, V> getLeft() {
		return this.left;
	}

	public void setLeft(Node<K, V> left) {
		this.left = left;
	}

	public Node<K, V> getRight() {
		return this.right;
	}

	public void setRight(Node<K, V> right) {
		this.right = right;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Node [");
		builder.append("key=");
		builder.append(this.key);
		builder.append(", ");
		if (this.value != null) {
			builder.append("value=");
			builder.append(this.value);
			builder.append(", ");
		}
		if (this.left != null) {
			builder.append("left=");
			builder.append(this.left.key);
			builder.append(", ");
		}
		if (this.right != null) {
			builder.append("right=");
			builder.append(this.right.key);
		}
		builder.append("]");
		return builder.toString();
	}

}
