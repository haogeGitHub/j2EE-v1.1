package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

public class PropertyValueDAO {
	
	//增
	public void add(PropertyValue bean){
		String sql="insert into PropertyValue value(null,?,?,?)";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setInt(1, bean.getProduct().getId());
            ps.setInt(2, bean.getProperty().getId());
            ps.setString(3, bean.getValue());
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
		String sql="delete from PropertyValue where id=?";
		
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
	public void update(PropertyValue bean){
		String sql="update PropertyValue set pid= ?, ptid=?, value=? where id=?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setInt(1, bean.getProduct().getId());
            ps.setInt(2, bean.getProperty().getId());
            ps.setString(3, bean.getValue());
            ps.setInt(4, bean.getId());
			//执行
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//查-根据属性id和产品id，获取一个PropertyValue对象
	public PropertyValue get(int ptid, int pid){
		
		PropertyValue bean=null;
		
		String sql="select * from PropertyValue where ptid=? and pid=?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setInt(1,ptid);
			ps.setInt(2,pid);
			//执行
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				bean=new PropertyValue();
				int id = rs.getInt("id");
				 
                String value = rs.getString("value");
                 
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return bean;
	}
	//查-分页
	public List<PropertyValue> list(int pid){
		List<PropertyValue> list=new ArrayList<PropertyValue>();
		
		String sql="select * from PropertyValue where pid=?";
		
		try {
			//获取连接
			Connection con=DBUtil.getConnection();
			//预编译
			PreparedStatement ps=con.prepareStatement(sql);			
			//配置参数
			ps.setInt(1,pid);
			//执行
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				PropertyValue bean=new PropertyValue();
				int id = rs.getInt("id");
				int ptid=rs.getInt("ptid");
                String value = rs.getString("value");
                 
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                
                list.add(bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return list;
	}
	
	/*
	 * 初始化某个产品对应的属性值，初始化逻辑：
	 *	1. 根据分类获取所有的属性 
	 *	2. 遍历每一个属性
	 *	  2.1 根据属性和产品，获取属性值 
	 *	  2.2 如果属性值不存在，就创建一个属性值对象
	 * */
    public void init(Product p) {
        List<Property> pts= new PropertyDAO().list(p.getCategory().getId());
         
        for (Property pt: pts) {
            PropertyValue pv = get(pt.getId(),p.getId());
            if(null==pv){
                pv = new PropertyValue();
                pv.setProduct(p);
                pv.setProperty(pt);
                this.add(pv);
            }
        }
    }
	
}
