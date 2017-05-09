
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T extends Comparable<T>> implements ListInterface<T> {
	// dummy head
	Node<T> head;
	int numItems;
	
	
	public MyLinkedList() {
		head = new Node<T>(null); // 다음 노드 생성.
	}
    public final Iterator<T> iterator() {
    	return new MyLinkedListIterator<T>(this); // iterator 반환.
    }

    
	@Override
	public boolean isEmpty() {
		return head.getNext() == null;
	}

	@Override
	public int size() {
		return numItems;
	}

	@Override
	public T first() {
		return head.getNext().getItem();
	}

	@Override
	public void add(T item) { // 맨 뒤에 <T> item 추가.
		Node<T> last = head;
		while (last.getNext() != null) {		
			if (last.getNext().getItem().compareTo(item) > 0) break;
	
			last = last.getNext();
		}
		last.insertNext(item);
		numItems += 1;
	}

	@Override
	public void removeAll() { // numitems=0 추가.
		head.setNext(null);
		numItems = 0;
	}
}

class MyLinkedListIterator<T extends Comparable<T>> implements Iterator<T> {
	private MyLinkedList<T> list;
	private Node<T> curr;
	private Node<T> prev;

	public MyLinkedListIterator(MyLinkedList<T> list) {
		this.list = list;
		this.curr = list.head;
		this.prev = null;
	}

	@Override
	public boolean hasNext() {
		return curr.getNext() != null;
	}

	@Override
	public T next() {
		if (!hasNext())
			throw new NoSuchElementException();

		prev = curr;
		curr = curr.getNext();

		return curr.getItem();
	}

	@Override
	public void remove() {
		if (prev == null)
			throw new IllegalStateException("next() should be called first");
		if (curr == null)
			throw new NoSuchElementException();
		prev.removeNext();
		list.numItems -= 1;
		curr = prev;
		prev = null;
	}
}