import java.util.ArrayList;

public class AVL {
	private AVLNode root;
	public AVL(){
		root = null;
	}
	public void remove(){
		root = null;
	}
	public boolean isEmpty(){
		if (root==null) return true;
		else return false;
	}
	public ArrayList<Pair<Integer, Integer>> find(String item){
		return find(root, item);
	}
	public ArrayList<Pair<Integer, Integer>> find(AVLNode here, String item){
		if (here==null) return new ArrayList<Pair<Integer, Integer>>();
		else{
			int comp = here.key.compareTo(item);
			if (comp==0) return here.getList();
			else if (comp < 0) return find(here.getRight(), item);
			else return find(here.getLeft(), item);
		}
	}
	public void insert(String item, Pair<Integer, Integer> pos){
		if (root==null) root = new AVLNode(item, pos);
		else root = insertavl(root, item, pos);
	}
	public AVLNode insertavl(AVLNode here, String item, Pair<Integer, Integer> pos){
        if (here == null) {
            here = new AVLNode(item, pos);
            return here;
        } else {
            int comp = here.key.compareTo(item);
            if (comp==0) here.addlist(pos); 
            else if (comp > 0) here.setLeft(insertavl(here.getLeft(), item, pos));
            else here.setRight(insertavl(here.getRight(), item, pos));
          
            here.h = Math.max(geth(here.getLeft()), geth(here.getRight())) + 1;
            int heightDiff = hdiff(here);
            if (heightDiff < -1) {
                if (hdiff(here.getRight()) > 0) here.setRight(rightRotate(here.getRight()));
                return leftRotate(here);
            } else if (heightDiff > 1) {
                if (hdiff(here.getLeft()) < 0) here.setLeft(leftRotate(here.getLeft()));
                return rightRotate(here);
            }
        }
        return here;
	}
    private AVLNode leftRotate(AVLNode here) {
        AVLNode there = here.getRight();
        here.setRight(there.getLeft());
        there.setLeft(here);
        here.h = Math.max(geth(here.getLeft()), geth(here.getRight())) + 1;
        there.h = Math.max(geth(there.getLeft()), geth(there.getRight())) + 1;
        return there;
    }

    private AVLNode rightRotate(AVLNode here) {
        AVLNode there = here.getLeft();
        here.setLeft(there.getRight());
        there.setRight(here);
        here.h = Math.max(geth(here.getLeft()), geth(here.getRight())) + 1;
        there.h = Math.max(geth(there.getLeft()), geth(there.getRight())) + 1;
        return there;
    }

    private int hdiff(AVLNode here) {
        if (here == null) return 0;
        return geth(here.getLeft()) - geth(here.getRight());
    }
    private int geth(AVLNode here) {
        if (here == null) return 0;
        return here.h;
    }
	public void print(){
		if (root==null) return;
		System.out.print(root.key);
		printavl(root.getLeft());
		printavl(root.getRight());
	}
	public void printavl(AVLNode here){
		if (here!=null){
			System.out.print(" "+here.key);
			printavl(here.getLeft());
			printavl(here.getRight());
		}
	}
}
