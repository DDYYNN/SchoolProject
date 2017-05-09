import java.util.ArrayList;

public class Heap<T extends Comparable<T>>{ // 제너럴 힙.
	private ArrayList<T> heap; // private로 보호.
	Heap(){
		heap = new ArrayList<T>();
	}
	boolean isEmpty(){
		return heap.isEmpty();
	}
	void insert(T item){ // insert item.
		heap.add(item);
		
		int here = heap.size()-1,there;
		while(here!=0){
			there = (here-1)>>1;
			if (heap.get(here).compareTo(heap.get(there)) < 0){
				T temp = heap.get(here);
				heap.set(here, heap.get(there));
				heap.set(there, temp);
			}
			else break;
			
			here = there;
		}
	}
	void pop(){ // pop item.
		heap.set(0, heap.get(heap.size()-1)); heap.remove(heap.size()-1); 
		
		int here=0,there;
		while(true){
			if (2*here+1 >= heap.size()) break;
			else if (2*here+2 == heap.size()) there = 2*here+1;
			else if (heap.get(2*here+1).compareTo(heap.get(2*here+2)) < 0) there = 2*here+1;
			else there = 2*here+2;
			
			if (heap.get(here).compareTo(heap.get(there)) > 0){
				T temp = heap.get(here);
				heap.set(here, heap.get(there));
				heap.set(there, temp);				
			}
			else break;
			
			here = there;
		}
	}
	T peek(){ // peek.
		if (heap.isEmpty()) return null;
		return heap.get(0);
	}
}
