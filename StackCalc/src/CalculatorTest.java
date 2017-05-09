import java.io.*;
import java.util.Stack;
import java.util.Vector;

public class CalculatorTest
{
	public static Vector<Object> save;
	public static Stack<Long> ans;
	static int[] level = new int[300];
	
	
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true)   
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;
				
				command(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}
	public static int cal_ans(char there){ // 후위표기식 계산, 계산과정에서 에러이면 0을 반환
		if (there=='~'){ // unary, 하나의 operand가 필요
			if (ans.size() < 1) return 0;
			long a;
			a = ans.pop();
			ans.push(-a);
		}
		else{ // 두개의 operand가 필요
			if (ans.size() < 2) return 0;
			long a,b;
			a = ans.pop(); b = ans.pop();
			if (there=='+') ans.push(a+b);
			else if (there=='-') ans.push(b-a);
			else if (there=='*') ans.push(b*a);
			else if (there=='/'){
				if (a==0) return 0;
				ans.push(b/a);
			}
			else if (there=='%'){
				if (a==0) return 0;
				ans.push(b%a);
			}
			else if (there=='^'){
				if (a<0) return 0;
				long temp = (long) Math.pow(b, a);
				ans.push(temp);
			}
			else return 0;
		}
		return 1;
	}
	public static int make_stack(String input, int start, int end){ // input의 start에서 end까지 수식 해결, 잘못된 수식일 경우 0을 반환
		Stack<Character> op = new Stack<Character>(); // 연산자 스택
		int i;
		boolean pre = false; // pre : 앞에 operand가 있는지 여부.
		
		for (i=start;i<end;i++){
			char here = input.charAt(i);
			if (here==' ' || here =='\t') continue; // 사이 공백 무시
			
			else if (!pre){ // 앞에 operand가 없는 경우
				if (here=='('){
					i++;
					int st=i,cnt=1;
					for (;i<end;i++){ // 매칭되는 괄호 찾기
						if (input.charAt(i)==')'){
							cnt--;
							if (cnt==0) break;
						}
						else if (input.charAt(i)=='('){
							cnt++;
						}
					}
					if (i==end) return 0; // ')' 없는 경우.
					if (make_stack(input,st,i)==0) return 0; // 괄호 안의 수식을 새로운 함수로 넘김
					
					pre=true;
				}
				else if ('0' <= here && here <= '9'){
					long sum=0;
					for (;i<end;i++){ // 숫자 인식
						char there = input.charAt(i);
						if ('0' <= there && there <= '9'){
							sum*=10;
							sum+=there-'0';
						}
						else break;
					}
					i--;
					save.addElement(sum);
					ans.push(sum);
					pre = true;
				}
				else if (here=='-'){ // unary
					here = '~';
					while(!op.empty()){
						char there = op.pop();
						// 자신보다 우선순위가 높은 연산자 추출
						if (level[(int)there] > level[(int)here] || (level[(int)there]==level[(int)here] && level[(int)here]<3)){
							if (cal_ans(there)==0) return 0;
							save.addElement(there);
						}
						else{
							op.push(there);
							break;
						}
					}					
					op.push(here);
					pre = false;
				}
				else return 0;
			}
			else{
				if (level[(int)here]>0){ // 알고있는 기호만 인식.
					while(!op.empty()){
						char there = op.pop();
						// 자신보다 우선순위가 높은 연산자 추출
						if (level[(int)there] > level[(int)here] || (level[(int)there]==level[(int)here] && level[(int)here]<3)){
							if (cal_ans(there)==0) return 0;
							save.addElement(there);
						}
						else{
							op.push(there);
							break;
						}
					}
					op.push(here);
				}
				else{
					return 0;
				}
				pre = false;
			}
		}
		//남은 연산자 추출
		while(!op.empty()){
			char there = op.pop();
			if (cal_ans(there)==0) return 0;
			save.addElement(there);
		}
		if (!pre) return 0; // 끝날때 연산자로 끝날 경우 에러
		return 1;
	}
	private static void command(String input)
	{
		if (input.isEmpty()) return;
		//연산자 우선순위 레벨
		level[(int)'+']=1; level[(int)'-']=1;
		level[(int)'*']=2; level[(int)'/']=2; level[(int)'%']=2;
		level[(int)'~']=3;
		level[(int)'^']=4;
		
		save = new Vector<>(); // 후위표기식
		ans = new Stack<>(); // 답
		
		if (make_stack(input,0,input.length())==0){ // 0은 에러를 의미함.
			System.out.println("ERROR");
			return;
		}
		for (int i=0;i<save.size();i++){ // 후위표기식
			System.out.print(save.elementAt(i)+" ");
		}
		System.out.println();
		System.out.println(ans.pop());
	}
}