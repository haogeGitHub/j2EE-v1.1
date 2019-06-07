package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static String ip="127.0.0.1";
	private static int port=3306;
	private static String database="tmall";
	private static String encoding="UTF-8";
	private static String user="root";
	private static String password="363205";
	private static String driver="com.mysql.jdbc.Driver";
	
	
	//加载驱动器
	static{
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//获取连接
	public static Connection getConnection() throws SQLException{
		String url=String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s",ip,port,database,encoding);
		return DriverManager.getConnection(url, user, password);
	}
	
//	public static void main(String[] args) throws SQLException {
//		// TODO Auto-generated method stub
//		System.out.println(getConnection());
//	}

}
