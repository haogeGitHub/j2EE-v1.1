package tmall.util;

public class Page {
	private int start;//开始位置
	private int count;//每页显示的数量
	private int total;//总共有多少条数据
	
	public Page(int start,int count){
		this.start=start;
		this.count=count;
	}
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
