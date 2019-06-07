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
	
	//��
	public void add(Category bean){
		String sql="insert into category value(null,?)";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setString(1, bean.getName());
			//ִ��
			ps.execute();
			
			//��ѯ����ȡid
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
	
	//ɾ
	public void delete(int id){
		String sql="delete from category where id=?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1, id);
			//ִ��
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//��
	public void update(Category bean){
		String sql="update category set name=? where id=?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getId());
			//ִ��
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//��-ById
	public Category get(int id){
		
		Category bean=null;
		
		String sql="select * from category where id=?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1,id);
			//ִ��
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
	//��-��ҳ
	public List<Category> list(int start,int count){
		List<Category> list=new ArrayList<Category>();
		
		String sql="select * from category order by id desc limit ?,?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1,start);
			ps.setInt(2,count);
			//ִ��
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
	//��-All
	public List<Category> list(){
		return list(0,Short.MAX_VALUE);
	}
	//��-����
	public int getTotal(){
		int total=0;
		String sql="select count(*) from category";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			

			//ִ��
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
