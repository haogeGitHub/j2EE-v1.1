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
	//��
	public void add(Property bean){
		String sql="insert into Property value(null,?,?)";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
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
		String sql="delete from Property where id=?";
		
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
	public void update(Property bean){
		String sql="update Property set cid=? , name=?  where id=?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1, bean.getCategory().getId());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getId());
			//ִ��
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//��-ById
	public Property get(int id){
		
		Property bean=null;
		
		String sql="select * from Property where id=?";
		
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
				bean = new Property();
				
				String name = rs.getString("name");
                int cid = rs.getInt("cid");
                bean.setName(name);
                Category category = new CategoryDAO().get(cid);
                //����һ��PropertyDAO.get()����Ҫnewһ��CategoryDAO������CategoryDAO���Բ��õ���ģʽ
                bean.setCategory(category);
                bean.setId(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return bean;
	}
	
	//��-��ҳ(��ѯĳ�������µĵ����Զ���)
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
	//��-All(��ѯĳ�������µĵ����Զ���)
	public List<Property> list(int cid){
		return list(cid,0,Short.MAX_VALUE);
	}
	//��-����(��ȡĳ�ַ����µ������������ڷ�ҳ��ʾ��ʱ����õ�)
	public int getTotal(int cid){
		int total=0;
		String sql="select count(*) from Property where cid =?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1,cid);
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
