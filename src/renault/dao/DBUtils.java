package renault.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 数据库工具类
 *  * @author renault
 *
 */
public class DBUtils {
	
	private static Connection connection = null;
	private static PreparedStatement preparedStatement =null;
	private static ResultSet resultSet = null;
	
	//初始化
	static {
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 获取数据库连接
	 * @return
	 */
	private static  Connection getConnection(){
		try{
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/photodb?useUnicode=true&characterEncoding=UTF-8","root","admin");
		}catch(SQLException e){
			e.printStackTrace();			
		}
		return connection;
	}
	
	/**
	 * 关闭数据库连接
	 * @param connection
	 * @param preparedStatement
	 * @param resultSet
	 */
	private static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet ){
			try {
					if(resultSet == null){
						resultSet.close();
						resultSet = null;
					}
					if(preparedStatement == null){
						preparedStatement.close();
						preparedStatement = null;
					}
					if(connection == null){
						connection.close();
						connection = null;
					}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * 查询数据库
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public static ArrayList<Object[]> query(String sql, String[] parameters){
		ArrayList<Object[]> list =new ArrayList<Object[]>();
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			//设置查询参数
			for(int i=0; i<parameters.length; i++){
				preparedStatement.setString(i+1, parameters[i]);
			}
			resultSet = preparedStatement.executeQuery();
			int columnCount = resultSet.getMetaData().getColumnCount();
			while(resultSet.next()){
				Object[] objects = new Object[columnCount];
				for(int i=0;i<columnCount;i++){
					objects[i] = resultSet.getObject(i+1);
				}
				list.add(objects);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(connection,preparedStatement,resultSet);
		}
		return list;
	}
	/**
	 * 更新数据库
	 * @param sqls
	 * @param parameters
	 */
	public static void updates(String[] sqls, String[][] parameters){
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			for (int i = 0; i < sqls.length; i++) {
                preparedStatement = connection.prepareStatement(sqls[i]);
                for (int j = 0; j < parameters[i].length; j++) {
                    preparedStatement.setString(j + 1, parameters[i][j]);
                }
                preparedStatement.executeUpdate();
            }
            connection.commit();
		} catch (SQLException e) {
			try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
		}finally{
			close(connection, preparedStatement, resultSet);
		}
	}
	

}
