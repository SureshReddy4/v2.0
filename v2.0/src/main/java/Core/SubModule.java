package Core;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SubModule {

	static String ORpath = "C:\\Users\\suresh.b\\git\\v2.0\\v2.0\\src\\main\\java\\Core\\9020.properties";

	public static List<String> getSubModule(String Username,String Module) throws Exception
	{
		
		FileInputStream fis = new FileInputStream(ORpath);
		Properties p = new Properties();
		p.load(fis);
		
		List<String> data = new ArrayList<String>();
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String Conurl = "jdbc:sqlserver://" + p.getProperty("ServerName") + "databaseName="+ p.getProperty("databaseName");
		Connection con = DriverManager.getConnection(Conurl, p.getProperty("dbUsername"), p.getProperty("dbPassword"));		
		Statement stmt = con.createStatement();
		ResultSet rs;
		
		String s1 = p.getProperty("sb1")+Username+p.getProperty("sb2")+Module+p.getProperty("sb3");
		stmt.execute(s1);
		
		String query3 = "Select Title from #MainModule_List  Drop Table #MainModule_List";
				
		stmt.execute(query3);
		rs = stmt.getResultSet();
	   				while(rs.next())
	   					{
	   					//	System.out.println(rs.getString(1));
	   						data.add(rs.getString(1));
	   					}
	   				return data;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		List<String> Subs = SubModule.getSubModule("admin", "HR");
		for(String Sub : Subs)
		{
//			System.out.println(Sub);
		}
	}

}
