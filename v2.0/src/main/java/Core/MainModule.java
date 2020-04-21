package Core;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MainModule {

	public static List<String> dp(String Username) throws Exception {

		List<String> data = new ArrayList<String>();

		//Get Properties from Object Repository
		FileInputStream fis = new FileInputStream("C:\\Users\\suresh.b\\git\\v2.0\\v2.0\\src\\main\\java\\Core\\9020.properties");
		Properties p = new Properties();
		p.load(fis);
		/*
		 * Server Connection Creation
		 * Conurl contains Server Name, Database Name
		 */
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String Conurl = "jdbc:sqlserver://" + p.getProperty("ServerName") + "databaseName="+ p.getProperty("databaseName");
		Connection con = DriverManager.getConnection(Conurl, p.getProperty("dbUsername"), p.getProperty("dbPassword"));

		Statement stmt = con.createStatement();
		ResultSet rs;

		
		String query2 = p.getProperty("uq1") + Username +  p.getProperty("uq2");
		
		stmt.execute(query2);

//		String query3 = "Select Title from #MainModule_List Where URL IS NOT NULL and Parent = 0 and Title In ('HR') Drop Table #MainModule_List";
		String query3 = "Select Title from #MainModule_List Where Parent=0 and Url is not null Drop Table #MainModule_List";
		
		stmt.execute(query3);
		
		rs = stmt.getResultSet();
		while (rs.next()) {
//   						System.out.println(rs.getString(1));
			data.add(rs.getString(1));
		}

		return data;
	}
	
	
	
	public static void main(String[] args) throws Exception 
	{
		List<String> Module = MainModule.dp("admin");
		for (String M : Module) 
		{
//			System.out.println(M);
		}

}
}