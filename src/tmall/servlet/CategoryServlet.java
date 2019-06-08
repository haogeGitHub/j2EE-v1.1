package tmall.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.util.Page;

public class CategoryServlet extends BaseBackServlet {
	
	public String list(HttpServletRequest req, HttpServletResponse res,Page page){
//	    PrintWriter pw = null;
//		try {
//			pw = res.getWriter();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    pw.println("haoge");
	    
	    //≤È—Ø
	    List<Category> cs=categoryDAO.list(page.getStart(), page.getCount());
	    int total=categoryDAO.getTotal();
	    page.setTotal(total);
	    
	    req.setAttribute("categorys", cs);
	    req.setAttribute("page", page);
	    
	    return "admin/listCategory.jsp";
	}

}
