package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

public class ProductImageDAO {
	
    public static final String type_single = "type_single";
    public static final String type_detail = "type_detail";
	
	//��
	public void add(ProductImage bean){
		String sql="insert into ProductImage value(null,?,?)";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setString(1, bean.getType());
			ps.setInt(2, bean.getProduct().getId());
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
		String sql="delete from ProductImage where id=?";
		
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
	public void update(ProductImage bean){
		
	}
	
	//��-ById
	public ProductImage get(int id){
		
		ProductImage bean=null;
		
		String sql="select * from ProductImage where id=?";
		
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
				bean=new ProductImage();
				bean.setId(id);
				
				String type=rs.getString("type");
				bean.setType(type);
				
				int pid=rs.getInt("pid");
				Product product=new ProductDAO().get(pid);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return bean;
	}
	//��-��ҳ
	public List<ProductImage> list(Product p, String type,int start,int count){
		List<ProductImage> list=new ArrayList<ProductImage>();
		
		String sql="select * from ProductImage where pid =? and type =? order by id desc limit ?,?";
		
		try {
			//��ȡ����
			Connection con=DBUtil.getConnection();
			//Ԥ����
			PreparedStatement ps=con.prepareStatement(sql);			
			//���ò���
			ps.setInt(1,p.getId());
			ps.setString(2,type);
			
			ps.setInt(3,start);
			ps.setInt(4,count);
			//ִ��
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ProductImage bean=new ProductImage();
				int id = rs.getInt(1);
				
				bean.setId(id);
				bean.setType(type);
				bean.setProduct(p);
				
				list.add(bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return list;
	}
	//��-All
	public List<ProductImage> list(Product p, String type){
		return list(p,type,0,Short.MAX_VALUE);
	}
	//��-����
	public int getTotal(){
		int total=0;
		String sql="select count(*) from ProductImage";
		
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
