
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
    
    public int num[],reverse[];
    public int sign,len;
 
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("\\s*(\\D?)\\s*(\\d+)\\s*(\\D)\\s*(\\D?)\\s*(\\d+)*\\s*");
 
    public BigInteger(){
    	num = new int[210];
    	reverse = new int[210];
    	sign = +1;
    	len = 0;
    }
    public BigInteger(int i)
    {
    }
    public BigInteger(int[] num1)
    {
    	
    }
    
    public int max(int a,int b){
    	return a>b?a:b;
    }
    
    public BigInteger(String s,String sign)
    {
    	num = new int[210];
    	reverse = new int[210];
    	
    	for (int i=0;i<s.length();i++) {
    		num[i] = s.charAt(i) - '0' ;
    		reverse[s.length() - i - 1] = s.charAt(i) - '0';
    	}
    	this.len = s.length();
    	this.sign =  ("-".equals(sign))?-1:1;
    }
    public int comp(BigInteger a,BigInteger b){
    	if (a.len > b.len) return 1;
    	else if (a.len < b.len) return -1;
    	else{
    		for (int i=0;i<a.len;i++){
    			if (a.num[i] > b.num[i]) return 1;
    			else if (a.num[i] < b.num[i]) return -1;
    		}
    		return 0;
    	}
    }
    public void alpha(){
    	while(len > 1 && reverse[len-1]==0) len--;
    	for (int i=0;i<len;i++) num[i] = reverse[len-1-i];
    	if (len==1 && num[0]==0) sign=1;
    }
    public BigInteger add(BigInteger big)
    {
        BigInteger num3 = new BigInteger();
        
        if (this.sign*big.sign==1){
        	num3.sign = this.sign;
        	int i;
        	for (i=0;i<max(this.len,big.len);i++){
        		num3.reverse[i] += this.reverse[i]+big.reverse[i];
        		num3.reverse[i+1] += num3.reverse[i]/10;
        		num3.reverse[i]%=10;
        	}
        	num3.len = i;
        	if (num3.reverse[num3.len] > 0) num3.len++;        	
        	for (i=0;i<num3.len;i++) num3.num[i]=num3.reverse[num3.len-1-i];
        }
        else{
        	int cp=comp(this,big);
        	if (cp==1){
        		int temp = this.sign;
        		this.sign = 1; big.sign = 1;
        		num3 = this.subtract(big);
        		num3.sign = temp;
        	}
        	else if (cp==-1) num3=big.add(this);
        	else{
        		num3.len=1;
        		num3.num[0]=0;
        		num3.reverse[0]=0;
        		num3.sign=1;
        	}
        }
        num3.alpha();
        return num3;
    }
 
    public BigInteger subtract(BigInteger big)
    {
        BigInteger num3 = new BigInteger();

    	if (this.sign * big.sign==1){
    		int cp=comp(this,big);
    		if (cp==1){
    			num3.sign=this.sign;
    			int i;
    			for (i=0;i<max(this.len,big.len);i++){
    				num3.reverse[i]+=this.reverse[i]-big.reverse[i];
    				while(num3.reverse[i] < 0){
    					num3.reverse[i]+=10;
    					this.reverse[i+1]--;
    				}
    			}
    			if (num3.reverse[i-1]==0) num3.len = i-1;
    			else num3.len = i;
    			for (i=0;i<num3.len;i++) num3.num[i]=num3.reverse[num3.len-1-i];
    		}
    		else if (cp==-1){
    			num3=big.subtract(this);
    			num3.sign*=-1;
    		}
    		else{
    			num3.len=1;
    			num3.num[0]=0;
    			num3.reverse[0]=0;
    			num3.sign=1;
    		}
    	}
    	else{
    		big.sign*=-1;
    		num3 = this.add(big);
    	}
    	num3.alpha();
    	return num3;
    }
 
    public BigInteger multiply(BigInteger big)
    {
        BigInteger num3 = new BigInteger();
        num3.sign=this.sign*big.sign;
        int i;
        for (i=0;i<this.len;i++){
        	for (int j=0;j<big.len;j++){
        		num3.reverse[i+j]+=this.reverse[i]*big.reverse[j];
        	}
        }
        for (i=0;i<this.len+big.len-1 || num3.reverse[i]>0;i++){
        	num3.reverse[i+1] += num3.reverse[i]/10;
        	num3.reverse[i]%=10;
        }
        num3.len=i;        
        num3.alpha();
        return num3;
    }
 
    @Override
    public String toString()
    {
    	String ret="";
    	if (this.sign==-1) ret+="-";
    	for (int i=0;i<len;i++){
    		ret+=String.valueOf(num[i]);
    	}
    	return ret;
    }
    public static void error(){
    	System.out.println("Error");
    }
    
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // implement here
        // parse input
        // using regex is allowed
        // One possible implementation

    	Matcher matcher = EXPRESSION_PATTERN.matcher(input);
    	if (!matcher.matches()) error(); 
         BigInteger num1 = new BigInteger(matcher.group(2),matcher.group(1));
         BigInteger num2 = new BigInteger(matcher.group(5),matcher.group(4));
         
         
         
         if ("+".equals(matcher.group(3))) return num1.add(num2);
         else if ("-".equals(matcher.group(3))) return num1.subtract(num2);
         else if ("*".equals(matcher.group(3))) return num1.multiply(num2);
         else return num1;
         
    }
 
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
					input = input.replaceAll("\\s+", "");
 
                    try
                    {
                        done = processInput(input);
                        
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
 
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
 
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
            
            return false;
        }
    }
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}