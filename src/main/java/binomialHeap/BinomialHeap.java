package binomialHeap;

import java.util.NoSuchElementException;

public class BinomialHeap<Key extends Comparable<Key>> {
    Node<Key> head;

    public BinomialHeap(Key key) {
       this.head = new Node<Key>(key);
    }

    public BinomialHeap() {
    }

    public BinomialHeap(Node<Key> keyNode) {
        this.head = new Node<Key>(keyNode);
    }

    public void insert(Key key) {
        BinomialHeap<Key> newHeap = new BinomialHeap<Key>(key);
        merge(newHeap);
    }

    public void insert(Node<Key> keyNode) {
        BinomialHeap<Key> newHeap = new BinomialHeap<Key>(keyNode);
        merge(newHeap);
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public int size(){
        Node<Key> cur = this.head;
        int result = 0;

        while(cur != null) {
            result += Math.pow(2,cur.degree);
            cur = cur.sibling;
        }
        return result;
    }

    public void clear() {
        this.head = null;
    }

    public BinomialHeap<Key> union(BinomialHeap<Key> newHeap) {

        BinomialHeap<Key> result = mergeRootList(this, newHeap);


        if (result.isEmpty() || result.head.sibling == null) { // if list is empty or consist of 1 node -> return null
            return result;
        }

        //Подговка к слиянию деревьев
        Node<Key> prev = null;
        Node<Key> cur = result.head;
        Node<Key> next = result.head.sibling;

        while (next != null) {
            if (cur.degree != next.degree || (next.sibling != null && next.sibling.degree == cur.degree)) {
                prev = cur;
                cur = next;
            }
            else {
                if (cur.key.compareTo(next.key) < 1) {
                    cur.sibling = next.sibling;
                    relink(cur, next);
                }
                else {   //next is parent
                    if ( prev == null) {    //next is parent and header
                        result.head = next;
                    }
                    else {  //next is parent and not header
                        prev.sibling = next;
                    }
                    relink(next, cur);
                    cur = next;
                }
            }
            next = cur.sibling;

        }
        return result;
    }

    public void merge(BinomialHeap<Key> binomialHeap) {
        this.head = union(binomialHeap).head;
    }

    public Key min() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Key> cur = this.head;
        Key min = cur.key;

        cur = cur.sibling;
        while (cur != null) {
            if (cur.key.compareTo(min) < 0) {
                min = cur.key;
            }
            cur = cur.sibling;
        }
        return min;
    }

    public void delMin() {
        if (this.isEmpty())
            return;

        Key min = this.min();

        Node<Key> cur;
        Node<Key> son;

        if (this.head.key == min) {
            son = this.head.son;
            this.head = this.head.sibling;
        }
        else {
            cur = this.head;
            Node<Key> prev = null;

            while (cur.key != min) {
                prev = cur;
                cur = cur.sibling;
            }
            son = cur.son;
            prev.sibling = cur.sibling;
        }


        while (son != null) {

            BinomialHeap<Key> tmpHeap = new BinomialHeap<Key>(son);
            this.merge(tmpHeap);

            son = son.sibling;
        }

    }

    /*  Helpers methods    */

    private void relink(Node<Key> node1, Node<Key> node2) {
        node2.sibling = node1.son;
        node2.parent = node1;
        node1.son = node2;
        node1.degree++;
    }

    private BinomialHeap<Key> mergeRootList(BinomialHeap<Key> heap1, BinomialHeap<Key> heap2) {
        // Если один из списков пустой
        if (heap1.isEmpty()) {
            return heap2;
        }
        else {
            if (heap2.isEmpty()) {
                return heap1;
            }
        }

        //создаем указатели для слияния
        Node<Key> cur1 = heap1.head;
        Node<Key> cur2 = heap2.head;

        BinomialHeap<Key> result = new BinomialHeap<Key>();

        //инициализируем голову списка
        if (cur1.degree <= cur2.degree) {
            result.head = cur1;
            cur1 = cur1.sibling;
        }
        else {
            result.head = cur2;
            cur2 = cur2.sibling;
        }

        //добавляем хвост списка
        Node<Key> teal = result.head;

        //слияние
        while (cur1 != null || cur2 != null) {
            if (cur2 == null) {
                teal.sibling = cur1;
                teal = teal.sibling;
                cur1 = cur1.sibling;
            }
            else {
                if (cur1 == null) {
                    teal.sibling = cur2;
                    teal = teal.sibling;
                    cur2 = cur2.sibling;
                }
                else {
                    if (cur1.degree <= cur2.degree) {
                        teal.sibling = cur1;
                        teal = teal.sibling;
                        cur1 = cur1.sibling;
                    }
                    else {
                        teal.sibling = cur2;
                        teal = teal.sibling;
                        cur2 = cur2.sibling;
                    }
                }
            }
        }

        return result;
    }

}