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
		
		//获取分页信息
		int start=0;
		int count=5;//每页默认显示5条数据
		
		try{
			start=Integer.parseInt(req.getParameter("page.start"));
		}catch(Exception e){
			//出现异常，什么都不做，start值便不会被改变
		}
		try{
			count=Integer.parseInt(req.getParameter("page.count"));
		}catch(Exception e){
			//出现异常，什么都不做，start值便不会被改变
		}
		
		Page page=new Page(start,count);
		/*借助反射，调用对应的方法*/
		String method=(String) req.getAttribute("method");
		
		try {
			//System.out.println(this);//这里的this指的是调用该方法的对象，而非父类
			Method m=this.getClass().getMethod(method,javax.servlet.http.HttpServletRequest.class, javax.servlet.http.HttpServletResponse.class,Page.class);
			String dir=m.invoke(this,req,res,page).toString();
			
			if(dir.startsWith("@")){//客户端跳转
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
