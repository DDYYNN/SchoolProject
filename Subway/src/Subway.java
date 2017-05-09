import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Subway {
	
	static Hashtable<String, Integer> hash; // 고유번호 numbering.
	static int N,N_station; // 들어오는 vertex 수, 역 수.
	static Node[] db; // hash에 의해 numbering된 고유번호로 설정된 vertex들의 배열.
	static int[] dist,chk,path,transfer; // 다익스트라에 쓰이는 배열.

	static Hashtable<String, Integer> station; // 역이름 numbering.
	static ArrayList<ArrayList<Integer>> group; // 같은 역들의 집합.

	static int MAX_Q = 1000000; // 최대치.
	static int MAX_ST = 100000; // 최대치.
	public static void main(String args[]) throws IOException
	{
		hash = new Hashtable<String, Integer>();
		db = new Node[MAX_Q];
		dist = new int[MAX_Q]; chk = new int[MAX_Q]; path = new int[MAX_Q]; transfer = new int[MAX_Q];
		
		station = new Hashtable<String, Integer>();
		group = new ArrayList<ArrayList<Integer>>();
		
		String input; boolean flag=true;
//		BufferedReader read = new BufferedReader(new FileReader(args[0]));
		BufferedReader read = new BufferedReader(new FileReader("seoul"));
		
		N = 0; N_station = 0;

		for (int i=0;i<=MAX_ST;i++) group.add(new ArrayList<Integer>());
		

		while((input = read.readLine()) != null){ // read data.
			if (input.isEmpty()){
				flag=false;
//				System.out.println("good");
				continue;
			}
			String[] in = new String[3];
			in = input.split(" ");
			if (flag){
				hash.put(in[0], ++N); // 고유번호 numbering.
				db[N] = new Node(in[0],in[1],in[2]); // 새로운 노드 설정.
				
				if (!station.containsKey(in[1])) station.put(in[1],++N_station); // 같은 역 그룹 세팅.
				group.get(station.get(in[1])).add(N);
			}
			else{
				db[hash.get(in[0])].edge.add(new Edge(hash.get(in[1]),Integer.parseInt(in[2]))); // 간선 세팅.
			}
		}
		
		for (int i=1;i<=N;i++){ // 같은 역끼리 간선 설정.
			for (int j : group.get(station.get(db[i].name))){
				if (i!=j){
					db[i].edge.add(new Edge(j,5)); // 환승비용 5분.
				}
			}
		}
		/*
		for (int i : group.get(station.get("미남"))){
			System.out.println(db[i].color);
		}
		*/
		/*
		for (int i=1;i<=N;i++){
			System.out.println("현재 위치 : "+db[i].name+"역 "+db[i].color+"호");
			for (Edge j : db[i].edge){
				System.out.println("\t"+db[j.pos].name+"역 "+j.cost);
			}
		}
		*/

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			try
			{
				input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;
				command(input);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}		
		
	}
	static void command(String input){ // 다익스트라.
		if (input.isEmpty()) return;
		
		String[] in = input.split(" ");
		ArrayList<String> ans = new ArrayList<String>();
		
		for (int i=1;i<=N;i++){ // 초기화.
			dist[i]=1000000009; chk[i]=0; path[i]=0; transfer[i]=MAX_ST+1;
		}
		Heap<Edge> q = new Heap<Edge>(); // Edge 클래스 기반 heap 구성.

		if (in.length==3){ // 최소 환승 설정.
//			System.out.println("good");
			for (int i=1;i<=N;i++){ // 환승 간선 세팅.
				for (Edge j : db[i].edge){
					if (db[i].name.compareTo(db[j.pos].name)==0){
						j.tran=1; // 환승 표시.
					}
				}
			}
		}


		for (int i : group.get(station.get(in[0]))){ // 시작점 여러개 세팅. 다른 노드라도 같은 역일 경우.
			q.insert(new Edge(i,0));
			path[i] = i;
			dist[i] = 0;
			transfer[i] = 0;
		}
				
		while(!q.isEmpty()){ // 다익스트라 O(E+VlogE)
			Edge now = q.peek(); q.pop();
//			System.out.println(db[now.pos].name);
			if (chk[now.pos]==1) continue;
			chk[now.pos]=1;

			
			if (db[now.pos].name.compareTo(in[1])==0){ // 도착점 찾음.
//				System.out.println("success");
				int here = now.pos;
				while(db[here].name.compareTo(in[0])!=0){ // paht 기록.
					ans.add(db[here].name);
					here = path[here];
				}
				ans.add(db[here].name);
				System.out.print(ans.get(ans.size()-1));
				for (int i=ans.size()-2;i>=0;i--){ // 거꾸로 출력. 연속 같은 역이 나오면 환승역이라는 표시.
					if (i>0){
						if (ans.get(i).compareTo(ans.get(i-1))==0) continue;
					}
					if (ans.get(i).compareTo(ans.get(i+1))==0) System.out.print(" ["+ans.get(i)+"]");
					else System.out.print(" "+ans.get(i));
				}
				System.out.println();
				System.out.println(now.cost);
				break;
			}
			
			for (Edge i : db[now.pos].edge){ // tran이 cost보다 우선순위가 더 높음. 최소 환승이 아닐 경우, tran은 모두 0이므로 알아서 cost만 고려함.
				if (chk[i.pos] == 0 && (transfer[i.pos] > now.tran+i.tran || (transfer[i.pos]==now.tran+i.tran && dist[i.pos] > now.cost+i.cost))){
					// update.
					dist[i.pos] = now.cost+i.cost;
					q.insert(new Edge(i.pos, now.cost+i.cost, now.tran+i.tran));
					path[i.pos] = now.pos;
					transfer[i.pos] = now.tran+i.tran;					
				}
			}
		}
//		if (q.isEmpty()) System.out.println("finish");
		if (in.length==3){ // 다시 초기화. 이후에는 최소 환승을 묻는 쿼리가 아닐 수도 있으므로.
			for (int i=1;i<=N;i++){
				for (Edge j : db[i].edge){
					if (db[i].name.compareTo(db[j.pos].name)==0) j.tran=0;
				}
			}
		}

	}
}
