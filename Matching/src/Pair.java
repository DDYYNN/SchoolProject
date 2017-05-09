public class Pair<L,R> {
    public L l;
    public R r;
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    public boolean isEqual(Pair<L,R> item){
    	return (l==item.l) && (r==item.r);
    }
    public String toString(){
    	return "("+l+", "+r+")";
    }
}