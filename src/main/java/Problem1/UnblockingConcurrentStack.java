package Problem1;

import java.util.concurrent.atomic.AtomicReference;

/**
 */
public class UnblockingConcurrentStack<E> {

    private final AtomicReference<Node<E>> top = new AtomicReference<>(null);

    public E push(E item) {
        Node<E> newTop = new Node<>(item, null);
        while (true) {
            Node<E> oldTop = top.get();
            newTop.next = oldTop;
            if (top.compareAndSet(oldTop, newTop)) {
                return item;
            }
        }
    }

    public E pop() {
        while(true) {
            Node<E> oldTop = top.get();
            if(oldTop == null)
                return null;
            Node<E> newTop = oldTop.next;
            if(top.compareAndSet(oldTop, newTop))
                return oldTop.item;
        }
    }

    public E peek() {
        Node<E> node = top.get();
        if(node == null)
            return null;
        else
            return node.item;
    }

    public boolean empty() {
        if(top.get() == null)
            return true;
        else
            return false;
    }

    public int search(E o) {
        int count = 0;
        for (Node<E> node = top.get(); node != null; node = node.next) {
            E item = node.item;
            if (item.equals(o))
                return count;
            count++;
        }
        return -1;
    }

    static class Node<E> {
        private final E item;
        private Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }
}
