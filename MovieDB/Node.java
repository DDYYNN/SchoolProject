public class Node<T> {
    private T item;
    private Node<T> next;

    public Node(T obj) {
        this.item = obj;
        this.next = null;
    }
    
    public Node(T obj, Node<T> next) {
    	this.item = obj;
    	this.next = next;
    }
    
    public final T getItem() {
    	return item;
    }
    
    public final void setItem(T item) {
    	this.item = item;
    }
    
    public final void setNext(Node<T> next) {
    	this.next = next;
    }
    
    public Node<T> getNext() {
    	return this.next;
    }
    
    public final void insertNext(T obj) { // 현재 위치에서 item을 다음 원소로 추가.
    	Node<T> temp = new Node<T>(obj); // 새로운 노드 생성.
    	temp.setNext(next); // 기존의 노드의 포인터를 새로운 노드의 포인터로 이동.
    	this.setNext(temp); // 기존의 노드의 포인터를 새로운 노드로 설정.
    }
    
    public final void removeNext() {
    	this.setNext(next.next); // 기존의 노드의 포인터를 포인터의 포인터로 변경.
    }
}