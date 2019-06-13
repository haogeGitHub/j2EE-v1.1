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
	
	//��ѯ
	public String list(HttpServletRequest req, HttpServletResponse res,Page page){
    
	    //��ѯ
	    List<Category> cs=categoryDAO.list(page.getStart(), page.getCount());
	    int total=categoryDAO.getTotal();
	    page.setTotal(total);
	    
	    req.setAttribute("categorys", cs);
	    req.setAttribute("page", page);
	    
	    return "admin/listCategory.jsp";
	}
	
	//���
	public String add(HttpServletRequest req, HttpServletResponse res,Page page){
	    
		
		//System.out.println("hello");
		//��ȡ�ύ�Ĳ������ļ���
		Map<String,String> params=new HashMap<>();
		InputStream is=FileUtil.parseUpload(req, params);
		
		//���
		String name=params.get("name");//��jsp�еĶ�Ӧ
		Category c=new Category();
		c.setName(name);
		categoryDAO.add(c);
		//System.out.println("hello");
		
	    //����ͼƬ
		//System.out.println(req.getContextPath());
		//System.out.println(req.getServletPath());
		//����ͼƬ��·��
		File imageFolder = new File(req.getSession().getServletContext().getRealPath("img/category"));
	    //����ͼƬ�ļ�
		File file=new File(imageFolder,c.getId()+".jpg");
		
		FileUtil.wirte(file, is);
		//ͨ�����´��룬���ļ�����Ϊjpg��ʽ
        BufferedImage img = ImageUtil.change2jpg(file);
        try {
			ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	    return "@admin_category_list";
	}
	
	//ɾ��
	public String delete(HttpServletRequest req, HttpServletResponse res,Page page){
		
		//��ȡ����
		String id=req.getParameter("id");
		categoryDAO.delete(Integer.parseInt(id));
		
		return "@admin_category_list";
	}
	
	//�༭
	public String edit(HttpServletRequest req, HttpServletResponse res,Page page){
		
		//��ȡ����
		int id=Integer.parseInt(req.getParameter("id"));
		
		//��ȡCategory
		Category c=categoryDAO.get(id);
		//��Category����request��
		req.setAttribute("c",c);
			
		return "admin/editCategory.jsp";
	}
	
	//�޸�
	//�༭
	public String update(HttpServletRequest req, HttpServletResponse res,Page page){
		
		//��ȡ�ύ�Ĳ������ļ���
		Map<String,String> params=new HashMap<>();
		InputStream is=FileUtil.parseUpload(req, params);
		
		//��ȡ����
		int id=Integer.parseInt(params.get("id"));
		String name=params.get("name");
		
		Category c=new Category();
		c.setId(id);
		c.setName(name);
	
		categoryDAO.update(c);
		
		//����ͼƬ��·��
		File imageFolder = new File(req.getSession().getServletContext().getRealPath("img/category"));
	    //����ͼƬ�ļ�
		File file=new File(imageFolder,c.getId()+".jpg");
		file.getParentFile().mkdirs();
		//mkdirs
		//������ļ��в����ڲ������һ�����ļ��в����ڣ������Զ��½�����·����д���ļ��У�
		//������ļ��д��ڣ�����ֱ�����Ѿ����ڵĸ��ļ������½����ļ��С�
		
		FileUtil.wirte(file, is);
		//ͨ�����´��룬���ļ�����Ϊjpg��ʽ
        BufferedImage img = ImageUtil.change2jpg(file);
        try {
			ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	    return "@admin_category_list";
	}
	
}
