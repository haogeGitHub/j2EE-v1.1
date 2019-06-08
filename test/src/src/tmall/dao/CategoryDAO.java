package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.util.DBUtil;

public class CategoryDAO {
	
	//增
	public void add(Category bean){
		String sql="insert into category value(null,?)";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setString(1, bean.getName());
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
		String sql="delete from category where id=?";
		
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
	public void update(Category bean){
		String sql="update category set name=? where id=?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getId());
			//执行
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//查-ById
	public Category get(int id){
		
		Category bean=null;
		
		String sql="select * from category where id=?";
		
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
				bean=new Category();
				bean.setId(id);
				String name=rs.getString(2);
				bean.setName(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return bean;
	}
	//查-分页
	public List<Category> list(int start,int count){
		List<Category> list=new ArrayList<Category>();
		
		String sql="select * from category order by id desc limit ?,?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setInt(1,start);
			ps.setInt(2,count);
			//执行
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Category bean=new Category();
				int id = rs.getInt(1);
				bean.setId(id);
				String name=rs.getString(2);
				bean.setName(name);
				list.add(bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return list;
	}
	//查-All
	public List<Category> list(){
		return list(0,Short.MAX_VALUE);
	}
	//查-总数
	public int getTotal(){
		int total=0;
		String sql="select count(*) from category";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			

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
