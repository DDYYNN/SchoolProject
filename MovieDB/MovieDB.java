import java.util.Iterator;
import java.util.NoSuchElementException;

public class MovieDB {
	MyLinkedList<Genre> database; // 장르 클래스를 리스트로 가지고 있는 데이터베이스.
	
    public MovieDB() { // 생성자.
    	database = new MyLinkedList<Genre>();
    }
    
    public void insert(MovieDBItem item) { // item 삽입.
    	for (Genre gr : database){ // 추가하는 item이 기존 장르에 있을 경우.
    		if (gr.getItem().equals(item.getGenre())){
    			for (MovieDBItem i : gr.movielist){
    				if (i.equals(item)) return; // 이미 똑같은 item이 있으면 패스.
    			}
    			gr.movielist.add(item);
    			return;
    		}
    	}
    	
    	Genre newgr = new Genre(item.getGenre()); // 없을 경우.
    	newgr.movielist.add(item);
    	database.add(newgr);
    }
    
    public void delete(MovieDBItem item) { // item 삭제.
    	Iterator<Genre> it2 = database.iterator(); // iterator<Genre>  접
    	while(it2.hasNext()){
    		Genre gr = it2.next();
    		
    		if (gr.getItem().equals(item.getGenre())){
    			Iterator<MovieDBItem> it = gr.movielist.iterator();
    			while(it.hasNext()){
    				MovieDBItem now = it.next();
    				if (now.getTitle().equals(item.getTitle())){ // item이랑 똑같은 영화가 있다면.
    					it.remove(); // 제거.
    					if (gr.movielist.numItems == 0){ // 장르에 포함된 마지막 영화가 제거됬다면.
    						it2.remove(); // 제거.
    					}
    					break;
    				}
    			}
    		}
    	}
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        for (Genre gr : database){ // 모든 장르.
        	for (MovieDBItem i : gr.movielist){ // 모든 타이틀.
        		if (i.getTitle().contains(term)) results.add(i); // 포함되어 있다면 추가.
        	}
        }
        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
    	MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        for (Genre gr : database){ // 모든 장르.
        	for (MovieDBItem i : gr.movielist){ // 모든 타이틀.
        		results.add(i); // 추가.
        	}
        }
    	return results;
    }
}
