package tmall.util;

public class Page {
	private int start;//��ʼλ��
	private int count;//ÿҳ��ʾ������
	private int total;//�ܹ��ж���������
	
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
