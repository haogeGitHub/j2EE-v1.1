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
	
	//��
	public void add(PropertyValue bean){
		String sql="insert into PropertyValue value(null,?,?,?)";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1, bean.getProduct().getId());
            ps.setInt(2, bean.getProperty().getId());
            ps.setString(3, bean.getValue());
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
		String sql="delete from PropertyValue where id=?";
		
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
	public void update(PropertyValue bean){
		String sql="update PropertyValue set pid= ?, ptid=?, value=? where id=?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1, bean.getProduct().getId());
            ps.setInt(2, bean.getProperty().getId());
            ps.setString(3, bean.getValue());
            ps.setInt(4, bean.getId());
			//ִ��
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//��-��������id�Ͳ�Ʒid����ȡһ��PropertyValue����
	public PropertyValue get(int ptid, int pid){
		
		PropertyValue bean=null;
		
		String sql="select * from PropertyValue where ptid=? and pid=?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1,ptid);
			ps.setInt(2,pid);
			//ִ��
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
	//��-��ҳ
	public List<PropertyValue> list(int pid){
		List<PropertyValue> list=new ArrayList<PropertyValue>();
		
		String sql="select * from PropertyValue where pid=?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1,pid);
			//ִ��
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
	 * ��ʼ��ĳ����Ʒ��Ӧ������ֵ����ʼ���߼���
	 *	1. ���ݷ����ȡ���е����� 
	 *	2. ����ÿһ������
	 *	  2.1 �������ԺͲ�Ʒ����ȡ����ֵ 
	 *	  2.2 �������ֵ�����ڣ��ʹ���һ������ֵ����
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
