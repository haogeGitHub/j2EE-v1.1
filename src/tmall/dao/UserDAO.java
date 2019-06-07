package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDAO {
	//��
	public void add(User bean){
		String sql="insert into user value(null,?,?)";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
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
		String sql="delete from User where id=?";
		
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
	public void update(User bean){
		String sql="update user set name=? , password=? where id=?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			//ִ��
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//��-ById
	public User get(int id){
		
		User bean=null;
		
		String sql="select * from user where id=?";
		
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
				bean=new User();
				bean.setId(id);
				String name=rs.getString("name");
				bean.setName(name);
				String password=rs.getString("password");
				bean.setPassword(password);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return bean;
	}
	
	//��-ByName(�ж�ĳ���û��Ƿ��Ѿ�����)
	public User get(String name){
		
		User bean=null;
		
		String sql="select * from user where name=?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setString(1,name);
			//ִ��
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				bean=new User();
				bean.setName(name);

				String password=rs.getString("password");
				bean.setPassword(password);
				
				int id=rs.getInt("id");
				bean.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return bean;
	}
	
	//��-ByNameAndPassword(�ж��˺������Ƿ���ȷ�ķ�ʽ,������һ�°����е��û���Ϣ����������ڴ��н��бȽ�)
	public User get(String name,String password){
		
		User bean=null;
		
		String sql="select * from user where name=? and password=?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setString(1,name);
			ps.setString(2,password);
			//ִ��
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				bean=new User();
				bean.setName(name);

				bean.setPassword(password);
				
				int id=rs.getInt("id");
				bean.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return bean;
	}
	
	//��-��ҳ
	public List<User> list(int start,int count){
		List<User> list=new ArrayList<User>();
		
		String sql="select * from user order by id desc limit ?,?";
		
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
				User bean=new User();
				int id = rs.getInt(1);
				bean.setId(id);
				
				String name=rs.getString("name");
				bean.setName(name);
				
				String password=rs.getString("password");
				bean.setPassword(password);
				
				list.add(bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return list;
	}
	//��-All
	public List<User> list(){
		return list(0,Short.MAX_VALUE);
	}
	//��-����
	public int getTotal(){
		int total=0;
		String sql="select count(*) from User";
		
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
	
	//��boolean��ʽ����ĳ���û����Ƿ��Ѿ�����
	public boolean isExist(String name) {
        User user = get(name);
        return user!=null;
    }
}
