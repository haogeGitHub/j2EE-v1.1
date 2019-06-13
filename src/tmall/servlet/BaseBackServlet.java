package tmall.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import tmall.dao.CategoryDAO;
import tmall.util.Page;

public class BaseBackServlet extends HttpServlet {
	
	
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
				res.sendRedirect(dir.substring(1));
			}else if(dir.startsWith("%")){
				System.out.println(dir.substring(1));
			}else{
				req.getRequestDispatcher(dir).forward(req, res);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
