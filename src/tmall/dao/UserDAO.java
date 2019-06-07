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
	//增
	public void add(User bean){
		String sql="insert into user value(null,?,?)";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
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
		String sql="delete from User where id=?";
		
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
	public void update(User bean){
		String sql="update user set name=? , password=? where id=?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			ps.setInt(3, bean.getId());
			//执行
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//查-ById
	public User get(int id){
		
		User bean=null;
		
		String sql="select * from user where id=?";
		
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
	
	//查-ByName(判断某个用户是否已经存在)
	public User get(String name){
		
		User bean=null;
		
		String sql="select * from user where name=?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setString(1,name);
			//执行
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
	
	//查-ByNameAndPassword(判断账号密码是否正确的方式,而不是一下把所有的用户信息查出来，在内存中进行比较)
	public User get(String name,String password){
		
		User bean=null;
		
		String sql="select * from user where name=? and password=?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setString(1,name);
			ps.setString(2,password);
			//执行
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
	
	//查-分页
	public List<User> list(int start,int count){
		List<User> list=new ArrayList<User>();
		
		String sql="select * from user order by id desc limit ?,?";
		
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
	//查-All
	public List<User> list(){
		return list(0,Short.MAX_VALUE);
	}
	//查-总数
	public int getTotal(){
		int total=0;
		String sql="select count(*) from User";
		
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
	
	//以boolean形式返回某个用户名是否已经存在
	public boolean isExist(String name) {
        User user = get(name);
        return user!=null;
    }
}
