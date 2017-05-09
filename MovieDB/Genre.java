import java.util.Iterator;

public class Genre extends Node<String> implements Comparable<Genre> {
	
	MyLinkedList<MovieDBItem> movielist;
	
	public Genre(String name) {
		super(name);
		movielist = new MyLinkedList<MovieDBItem>();
	}
	
	@Override
	public int compareTo(Genre o) {
		return movielist.first().compareTo(o.movielist.first());
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException("not implemented yet"); // 사용 안함.
	}

	@Override
	public boolean equals(Object obj) {
		return this.getItem().equals(obj);
	}
}

class MovieList implements ListInterface<String> { // 사용 안함.
	MyLinkedList<String> list;
	public MovieList() {
		list = new MyLinkedList<String>();
	}

	@Override
	public Iterator<String> iterator() {
		return list.iterator();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void add(String item) {
		
	}

	@Override
	public String first() {
		throw new UnsupportedOperationException("not implemented yet");
	}

	@Override
	public void removeAll() {
		throw new UnsupportedOperationException("not implemented yet");
	}
}