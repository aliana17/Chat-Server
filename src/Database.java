import java.sql.*;
public class Database {
	Connection conn;
	Statement stmt;
	ResultSet rst;
	int cnt;
	Database(){
		runOtherQuery("create table if not exists users(user varchar(20) primary key,pass varchar(20))");
	}
	void connectDatabase(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","root","");
			stmt=conn.createStatement();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	ResultSet runSelectQuery(String query){
		try{
			connectDatabase();
			rst=stmt.executeQuery(query);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return rst;
	}
	int runOtherQuery(String query){
		try{
			connectDatabase();
			cnt=stmt.executeUpdate(query);
			closeDatabase();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return cnt;		
	}
	void closeDatabase(){
		try{
			stmt.close();
			conn.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
}
