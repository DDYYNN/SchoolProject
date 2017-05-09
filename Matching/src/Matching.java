import java.io.*;
import java.util.ArrayList;

public class Matching
{	
	public static HashAVL db;
	private static BufferedReader read;
	
	public static void main(String args[])
	{
		db = new HashAVL();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			try
			{
				String input = br.readLine();
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
	
	private static void command(String input) throws IOException
	{
		if (input.isEmpty()) return;
		
		if (input.charAt(0) == '<'){
			
			File file = new File(input.substring(2, input.length()));
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
//			read = new BufferedReader(new FileReader(input.substring(2, input.length())));
			
			db = new HashAVL();
						
			String str;
			int cnt = 0;
			while((str = reader.readLine()) != null){
				int sum=0; cnt++;
				for (int i=0;i<str.length();i++){
					sum += (int)str.charAt(i);
					if (i > 5) sum += 200-(int)str.charAt(i-6);
					sum %= 100;
					if (i > 4) db.insert(sum, str.substring(i-5, i+1), new Pair<Integer, Integer>(cnt, i-4));
				}
			}
		}
		else if (input.charAt(0) == '@'){
			int temp = Integer.parseInt(input.substring(2, input.length()));
			if (0 <= temp &&  temp < 100){
				if (db.isEmpty(temp)) System.out.print("EMPTY");
				else db.print(temp);
			}
			System.out.println();
		}
		else if (input.charAt(0) == '?'){			
			ArrayList<Pair<Integer, Integer>> v = db.find(input.substring(2, input.length()));

			if (v.isEmpty()) v.add(new Pair<Integer, Integer>(0,0));
			System.out.print(v.get(0).toString());
			for (int i=1;i<v.size();i++)
				System.out.print(" "+v.get(i).toString());
			System.out.println();
		}
	}
}