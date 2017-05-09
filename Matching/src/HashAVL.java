import java.util.ArrayList;

public class HashAVL {
	private static AVL[] hash;
	public HashAVL(){
		hash = new AVL[100];
		for (int i=0;i<100;i++) hash[i] = new AVL();
	}
	public void remove(int index){
		hash[index].remove();
	}
	public void insert(int index, String str, Pair<Integer, Integer> pos){
		hash[index].insert(str, pos);
	}
	public boolean isEmpty(int index){
		return hash[index].isEmpty();
	}
	public void print(int index){
		hash[index].print();
	}
	public ArrayList<Pair<Integer, Integer>> find(String str){
		ArrayList<Pair<Integer, Integer>> ret = new ArrayList<Pair<Integer, Integer>>();
		/*
		ret = hash[gethash(str.substring(0, 6))].find(str.substring(0, 6));
		for (int i=6;i<str.length();i+=6){
			i = Math.min(i, str.length()-6);
			ArrayList<Pair<Integer, Integer>> newret = new ArrayList<Pair<Integer, Integer>>();
			ArrayList<Pair<Integer, Integer>> v = hash[gethash(str.substring(i,i+6))].find(str.substring(i,i+6));
			for (Pair<Integer, Integer> here : ret){
				for (Pair<Integer, Integer> there : v){
					if ((int)here.l == (int)there.l && (int)here.r + (int)i == (int)there.r){
						newret.add(here);
						break;
					}
				}
			}
			ret = newret;
		}
		*/
		
		int sum=0;
		for (int i=0;i<str.length();i++){
			sum += (int)str.charAt(i);
			if (i > 5) sum += 200-(int)str.charAt(i-6);
			sum %= 100;
			
			if (i%6==5 || i==str.length()-1){
				ArrayList<Pair<Integer, Integer>> v = hash[sum].find(str.substring(i-5, i+1));	
				if (i==5) ret = v;
				else{
					ArrayList<Pair<Integer, Integer>> newret = new ArrayList<Pair<Integer, Integer>>();
					int j,r;
					for (j=0;j<ret.size();j++){
						for (r=0;r<v.size();r++){
							if ((int)ret.get(j).l == (int)v.get(r).l && (int)ret.get(j).r+(int)(i-5) == (int)v.get(r).r){
								newret.add(ret.get(j));
								break;
							}
						}
					}
					ret = newret;
				}
			}
		}
		return ret;
	}
	public int gethash(String str){
		int ret=0;
		for (int i=0;i<str.length();i++){
			ret+=(int)str.charAt(i);
			ret%=100;
		}
		return ret;
	}
}
