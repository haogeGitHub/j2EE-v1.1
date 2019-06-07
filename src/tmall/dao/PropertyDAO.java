package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Property;
import tmall.bean.Category;
import tmall.util.DBUtil;
import tmall.dao.CategoryDAO;

public class PropertyDAO {
	//增
	public void add(Property bean){
		String sql="insert into Property value(null,?,?)";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			//执行
			ps.execute();
			
			//查询，获取id
			ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//删
	public void delete(int id){
		String sql="delete from Property where id=?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setInt(1, id);
			//执行
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//改
	public void update(Property bean){
		String sql="update Property set cid=? , name=?  where id=?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getId());
			//执行
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//查-ById
	public Property get(int id){
		
		Property bean=null;
		
		String sql="select * from Property where id=?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setInt(1,id);
			//执行
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				bean = new Property();
				
				String name = rs.getString("name");
                int cid = rs.getInt("cid");
                bean.setName(name);
                Category category = new CategoryDAO().get(cid);
                //调用一次PropertyDAO.get()就需要new一个CategoryDAO，所以CategoryDAO可以采用单例模式
                bean.setCategory(category);
                bean.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return bean;
	}
	
	//查-分页(查询某个分类下的的属性对象)
	public List<Property> list(int cid, int start, int count) {
        List<Property> beans = new ArrayList<Property>();
  
        String sql = "select * from Property where cid = ? order by id desc limit ?,? ";
  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
  
            ps.setInt(1, cid);
            ps.setInt(2, start);
            ps.setInt(3, count);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                Property bean = new Property();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                bean.setName(name);
                Category category = new CategoryDAO().get(cid);
                bean.setCategory(category);
                bean.setId(id);
                 
                beans.add(bean);
            }
        } catch (SQLException e) {
  
            e.printStackTrace();
        }
        return beans;
    }
	//查-All(查询某个分类下的的属性对象)
	public List<Property> list(int cid){
		return list(cid,0,Short.MAX_VALUE);
	}
	//查-总数(获取某种分类下的属性总数，在分页显示的时候会用到)
	public int getTotal(int cid){
		int total=0;
		String sql="select count(*) from Property where cid =?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setInt(1,cid);
			//执行
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				total=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return total;
	}
}
