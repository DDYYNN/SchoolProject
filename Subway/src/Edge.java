public class Edge implements Comparable<Edge>{ // 간선 정보.
	public int pos, cost; // 도착점 고유번호, 가는데 드는 비용.
	public int tran; // 환승 체크.
	Edge(){
		pos=0; cost=-1; tran=0;
	}
	Edge(int pos, int cost){
		this.pos = pos;
		this.cost = cost;
		this.tran = 0;
	}
	Edge(int pos, int cost, int tran){
		this.pos = pos;
		this.cost = cost;
		this.tran = tran;
	}
	@Override
	public int compareTo(Edge o) { // 비교 가능하게 설정.
		// TODO Auto-generated method stub
		if (tran > o.tran) return 1;
		else if (tran < o.tran) return -1;
		
		if (cost==o.cost) return 0;
		else if (cost > o.cost) return 1;
		else return -1;
	}	
}