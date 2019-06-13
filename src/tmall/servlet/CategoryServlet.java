package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.util.FileUtil;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class CategoryServlet extends BaseBackServlet {
	
	//查询
	public String list(HttpServletRequest req, HttpServletResponse res,Page page){
    
	    //查询
	    List<Category> cs=categoryDAO.list(page.getStart(), page.getCount());
	    int total=categoryDAO.getTotal();
	    page.setTotal(total);
	    
	    req.setAttribute("categorys", cs);
	    req.setAttribute("page", page);
	    
	    return "admin/listCategory.jsp";
	}
	
	//添加
	public String add(HttpServletRequest req, HttpServletResponse res,Page page){
	    
		
		//System.out.println("hello");
		//获取提交的参数及文件流
		Map<String,String> params=new HashMap<>();
		InputStream is=FileUtil.parseUpload(req, params);
		
		//添加
		String name=params.get("name");//跟jsp中的对应
		Category c=new Category();
		c.setName(name);
		categoryDAO.add(c);
		//System.out.println("hello");
		
	    //保存图片
		//System.out.println(req.getContextPath());
		//System.out.println(req.getServletPath());
		//保存图片的路径
		File imageFolder = new File(req.getSession().getServletContext().getRealPath("img/category"));
	    //建立图片文件
		File file=new File(imageFolder,c.getId()+".jpg");
		
		FileUtil.wirte(file, is);
		//通过如下代码，把文件保存为jpg格式
        BufferedImage img = ImageUtil.change2jpg(file);
        try {
			ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	    return "@admin_category_list";
	}
	
	//删除
	public String delete(HttpServletRequest req, HttpServletResponse res,Page page){
		
		//获取参数
		String id=req.getParameter("id");
		categoryDAO.delete(Integer.parseInt(id));
		
		return "@admin_category_list";
	}
	
	//编辑
	public String edit(HttpServletRequest req, HttpServletResponse res,Page page){
		
		//获取参数
		int id=Integer.parseInt(req.getParameter("id"));
		
		//获取Category
		Category c=categoryDAO.get(id);
		//将Category放入request中
		req.setAttribute("c",c);
			
		return "admin/editCategory.jsp";
	}
	
	//修改
	//编辑
	public String update(HttpServletRequest req, HttpServletResponse res,Page page){
		
		//获取提交的参数及文件流
		Map<String,String> params=new HashMap<>();
		InputStream is=FileUtil.parseUpload(req, params);
		
		//获取参数
		int id=Integer.parseInt(params.get("id"));
		String name=params.get("name");
		
		Category c=new Category();
		c.setId(id);
		c.setName(name);
	
		categoryDAO.update(c);
		
		//保存图片的路径
		File imageFolder = new File(req.getSession().getServletContext().getRealPath("img/category"));
	    //建立图片文件
		File file=new File(imageFolder,c.getId()+".jpg");
		file.getParentFile().mkdirs();
		//mkdirs
		//如果父文件夹不存在并且最后一级子文件夹不存在，它就自动新建所有路经里写的文件夹；
		//如果父文件夹存在，它就直接在已经存在的父文件夹下新建子文件夹。
		
		FileUtil.wirte(file, is);
		//通过如下代码，把文件保存为jpg格式
        BufferedImage img = ImageUtil.change2jpg(file);
        try {
			ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	    return "@admin_category_list";
	}
	
}
