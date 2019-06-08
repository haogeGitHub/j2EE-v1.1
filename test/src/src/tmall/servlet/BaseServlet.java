package tmall.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.dao.CategoryDAO;
import tmall.util.Page;

public class BaseServlet extends HttpServlet {
	
	
	protected CategoryDAO categoryDAO=new CategoryDAO();
	
	
	
	
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//��ȡ��ҳ��Ϣ
		int start=0;
		int count=5;//ÿҳĬ����ʾ5������
		
		try{
			start=Integer.parseInt(req.getParameter("page.start"));
		}catch(Exception e){
			//�����쳣��ʲô��������startֵ�㲻�ᱻ�ı�
		}
		try{
			count=Integer.parseInt(req.getParameter("page.count"));
		}catch(Exception e){
			//�����쳣��ʲô��������startֵ�㲻�ᱻ�ı�
		}
		
		Page page=new Page(start,count);
		/*�������䣬���ö�Ӧ�ķ���*/
		String method=(String) req.getAttribute("method");
		
		try {
			//System.out.println(this);//�����thisָ���ǵ��ø÷����Ķ��󣬶��Ǹ���
			Method m=this.getClass().getMethod(method,javax.servlet.http.HttpServletRequest.class, javax.servlet.http.HttpServletResponse.class,Page.class);
			String dir=m.invoke(this,req,res,page).toString();
			
			if(dir.startsWith("@")){//�ͻ�����ת
				res.sendRedirect(dir);
			}else if(dir.startsWith("%")){
				System.out.println(dir);
			}else{
				req.getRequestDispatcher(dir).forward(req, res);
				//req.getRequestDispatcher(dir).forward(req, res);
				//res.sendRedirect(dir);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
