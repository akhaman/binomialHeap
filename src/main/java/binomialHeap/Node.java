package binomialHeap;

public class Node<Key extends Comparable<Key>> {
    Key key;
    int degree;
    Node<Key> parent;
    Node<Key> son;
    Node<Key> sibling;

    public Node(Key key) {
        this.key = key;
        this.degree = 0;
    }

    public Node(Node<Key> keyNode) {
        this.key = keyNode.key;
        this.degree = keyNode.degree;
        this.son = keyNode.son;
    }

}
