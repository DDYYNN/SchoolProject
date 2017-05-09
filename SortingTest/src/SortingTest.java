import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.

//			int [] ans = (int[])value.clone();
//			ans = DoMergeSort(ans);
			
			
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}
				/*
				int same=1;
				for (int i=0;i<newvalue.length;i++){
					if (ans[i]!=newvalue[i]) same=0;
				}
				if (same==1) System.out.println("Yes");
				else System.out.println("No");
				*/
			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		int i,j;
		for (i=0;i<value.length;i++){
			for (j=0;j<value.length-1;j++){
				if (value[j] > value[j+1]){
					int temp;
					temp=value[j];
					value[j]=value[j+1];
					value[j+1]=temp;
				}
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		int i,j;
		for (i=1;i<value.length;i++){
			int temp;
			temp = value[i];
			for (j=i-1;j>=0;j--){
				if (value[j] <= temp) break;
				value[j+1] = value[j];
			}
			value[j+1]=temp;
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	private static int[] DoHeapSort(int[] value)
	{
		PriorityQueue<Integer> q=new PriorityQueue<Integer>();
		
		int[] heap = new int[value.length*4];
		int cnt=0;
		
		int i;
		for (i=0;i<value.length;i++){
			heap[++cnt]=value[i];
			int here=cnt;
			while(here!=1){
				int there=here>>1;
				if (heap[there] > heap[here]){
					int temp=heap[there];
					heap[there]=heap[here];
					heap[here]=temp;
				}
				else break;
				here=there;
			}
		}
		for (i=0;i<value.length;i++){
			value[i] = heap[1];
			heap[1] = heap[cnt--];
			
			int here=1;
			while(true){
				int there;
				if (2*here > cnt) break;
				else if (2*here == cnt || heap[2*here] < heap[2*here+1]) there=2*here;
				else there = 2*here+1;
				if (heap[there] < heap[here]){
					int temp=heap[there];
					heap[there]=heap[here];
					heap[here]=temp;
				}
				else break;
				here=there;
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		if (value.length==1) return value;
		
		int[] left = new int[value.length/2];
		int[] right = new int[value.length-value.length/2];
		int i,j;
		
		for (i=0;i<left.length;i++) left[i] = value[i];
		for (;i<value.length;i++) right[i-left.length] = value[i];
		left = DoMergeSort(left);
		right = DoMergeSort(right);
		i=0; j=0;
		while(i<left.length && j<right.length){
			if (left[i] > right[j]){
				value[i+j] = right[j];
				j++;
			}
			else{
				value[i+j] = left[i];
				i++;
			}
		}
		for (;i<left.length;i++) value[i+j] = left[i];
		for (;j<right.length;j++) value[i+j] = right[j];
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		if (value.length < 2) return value;
		
		int i,le,ri,pivot;
		
		pivot = value[0];
		le=0; ri=0;
		for (i=1;i<value.length;i++){
			if (value[i] < pivot) le++;
			else ri++;
		}
		int[] left = new int[le];
		int[] right = new int[ri];
		le=0; ri=0;
		for (i=1;i<value.length;i++){
			if (value[i] < pivot){
				left[le] = value[i];
				le++;
			}
			else{
				right[ri] = value[i];
				ri++;
			}
		}
		left = DoQuickSort(left);
		right = DoQuickSort(right);
		for (i=0;i<left.length;i++) value[i] = left[i];
		value[left.length] = pivot;
		for (i=left.length+1;i<value.length;i++) value[i] = right[i-left.length-1];
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		Vector<Integer>[] v = (Vector<Integer>[]) new Vector[20];
		int i,j;
		
		for (i=1;;i*=10){
			for(j = 0; j < v.length; j++){
				   v[j] = new Vector<Integer>();
			}
			for (j=0;j<value.length;j++){
				int temp=value[j]/i;
				if (temp<0) temp*=-1;
				if (value[j] < 0) v[9-temp%10].addElement(value[j]);
				else v[temp%10+10].addElement(value[j]);
			}
			int cnt=0;
			for (j=0;j<20;j++){
				for (int r=0;r<v[j].size();r++){
					value[cnt++] = v[j].elementAt(r);
				}
			}
			if (v[9].size()+v[10].size()==value.length) break;
		}
		return (value);
	}
}