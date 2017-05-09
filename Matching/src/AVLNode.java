import java.util.ArrayList;

public class AVLNode {
	public String key;
	private AVLNode left,right;
	public int h;
	private ArrayList<Pair<Integer, Integer>> list;
	
	
	public ArrayList<Pair<Integer, Integer>> getList() {
		return list;
	}
	public void addlist(Pair<Integer, Integer> item){
		list.add(item);
	}
	public AVLNode(){
		list = new ArrayList<Pair<Integer, Integer>>();
		key = null; left = null; right = null; h=0;
	}
	public AVLNode getRight() {
		return right;
	}
	public AVLNode getLeft() {
		return left;
	}
	public void setRight(AVLNode right) {
		this.right = right;
	}
	public void setLeft(AVLNode left) {
		this.left = left;
	}
	public AVLNode(AVLNode there){
		key = there.key;
		left = there.left;
		right = there.right;
		h = there.h;
		list = there.list;
	}
	public AVLNode(String item, Pair<Integer, Integer> pos){
		list = new ArrayList<Pair<Integer, Integer>>();
		left = null; right = null; h=1;
		key = item;
		list.add(pos);
	}
}
