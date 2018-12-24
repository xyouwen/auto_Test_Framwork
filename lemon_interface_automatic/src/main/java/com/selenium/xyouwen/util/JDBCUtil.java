package com.selenium.xyouwen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class JDBCUtil {
	
	/*设置全局变量*/
	
	// jdbc的连接字符串
	private static String url;
	// jdbc的连接用户名
	private static String user;
	// jdbc的连接密码
	private static String password;
	
	static {
		try {
			Properties properties = new Properties();
			properties.load(JDBCUtil.class.getResourceAsStream("/jdbc/jdbc.properties"));  
			Class.forName(properties.getProperty("jdbc.driver"));
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 把共有的代码抽取出来
	/**
	 * 功能：连接数据库
	 * @return
	 */
	private static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 功能：关闭、释放资源
	 * @param conn
	 * @param stmt
	 */
	private static void close(Connection conn, PreparedStatement stmt) {
		if(stmt!=null) {  // 为避免抛出空指针异常，这里可以加个判断
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 功能：关闭、释放资源
	 * @param conn
	 * @param stmt
	 * @param resultSet
	 */
	private static void close(Connection conn, PreparedStatement stmt, ResultSet resultSet) {
		if (resultSet!=null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close(conn, stmt);
	}
	
	/**
	 * 功能: 数据库操作之 -- 增、删、查
	 * @param sql：带占位符？的sql语句
	 * @param parameters：占位符？所指代的参数
	 */
	public static void exectue(String sql, Object... parameters) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < parameters.length; i++) {
				stmt.setObject(i+1, parameters[i]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {   // 为避免前面代码块抛出异常，资源不被释放，释放资源的操作一定要放在finally中，确保会被一定执行
			close(conn, stmt);
		}
	}
	
	/**
	 * 功能：数据库操作之 -- 查询，返回的结果是一个LinkedHashMap（有序的map列表），方便后期把查询的实际结果和预期结果进行比对
	 * @param sql：带占位符？的sql语句
	 * @param parameters：占位符？所指代的参数
	 * @return
	 */
	public static List<LinkedHashMap<String, String>> query(String sql, Object ... parameters){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		// 创建一个list容器来存储遍历出来的map
		List<LinkedHashMap<String, String>> allRecrodList = new ArrayList<LinkedHashMap<String,String>>();
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			for(int i=0; i<parameters.length; i++) {
				stmt.setObject(i+1, parameters[i]);
			}
			resultSet = stmt.executeQuery(sql);  // 执行后，得到一个结果集
			ResultSetMetaData metaData = resultSet.getMetaData();
			int count = metaData.getColumnCount();
			while(resultSet.next()) {
				// 结果集的每一行记录保存到一个map里面
				LinkedHashMap<String, String> oneRecord =  new LinkedHashMap<String, String>();
				// 遍历每一列
				for(int i=0; i<count; i++) {
					// 列的名称
//					String columnName = metaData.getColumnName(i+1);
					String aliasOfColumnName = metaData.getColumnLabel(i+1);  // 列的名称用别名来接收
					// 列的值
					String columnValue = resultSet.getString(i+1);
					// 往map里新增一条数据
					oneRecord.put(aliasOfColumnName, columnValue);
				}
				//往List列表里新增一条map
				allRecrodList.add(oneRecord);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(conn, stmt, resultSet);
		}
		return allRecrodList;
	}

	public static void main(String[] args) {
		String insertsql =  "insert into member(regname,mobilephone,pwd) values(?, ?, ?);";
		exectue(insertsql, "tom","13832582532","111111");
		
		String delsql = "delete from member where id = ?";
		exectue(delsql, 54);
		
		String querysqp = "select * from member";
		List<LinkedHashMap<String, String>> records = query(querysqp);
		for (Map<String, String> recordMap : records) {
			Set<String> colunmNames = recordMap.keySet();
			for (String columnName : colunmNames) {
				System.out.print(columnName+":"+recordMap.get(columnName)+"\t");
			}
			System.out.println();
		}
	}
}

