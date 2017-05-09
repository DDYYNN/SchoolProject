import java.util.ArrayList;

public class Node{ // 각 vertex가 가지고 있는 정보.
	public String num,name,color; // 고유번호, 역 이름, 호선.
	public ArrayList<Edge> edge; // current vertex와 연결되어 있는 vertex들의 집합.
	Node(){
		num = name = color = null;
		edge = new ArrayList<Edge>();
	}
	Node(String num, String name, String color){
		this.num = num; this.name = name; this.color = color;
		edge = new ArrayList<Edge>();
	}
}
