/*
 * Created on 2004-10-15
 *
 */
package dyna.framework.client.dfempdm;

//import java.util.HashMap;
//import java.util.ArrayList;
import javax.swing.JOptionPane;

import dyna.framework.service.DOS;
import dyna.framework.service.DSS;
//import dyna.framework.service.dos.DOSChangeable;
import dyna.framework.client.DynaMOAD;
//import dyna.framework.client.LogIn;
//import dyna.framework.client.UIGeneration;

import java.sql.*;
//import javax.sql.*;
//import oracle.jdbc.*;
//import oracle.jdbc.pool.OracleDataSource;

//
/**
 * @author 李渊
 *
 */
public final class anitest {
	public static void perform(Object inputObject, Object returnObject) throws Exception {
		DOS dos = DynaMOAD.dos;
		DSS dss = DynaMOAD.dss;
		
		try {
			//1. 快速查询结果工具栏按钮事件响应测试
//			ArrayList selectedList = (ArrayList) ((HashMap) inputObject)
//					.get("list");
//			if (selectedList == null)
//				return;
//
//			for (int i = 0; i < selectedList.size(); i++)
//				System.out.println(selectedList.get(i));

			//2. 快速查询结果右键菜单事件响应测试
//			ArrayList selectedList = (ArrayList) inputObject;
//			if (selectedList == null)
//				return;
//			
//			for (int i = 0; i < selectedList.size(); i++)
//				System.out.println(selectedList.get(i));

			//3. 获得当前登录用户
//			System.out.println(LogIn.userID);
//			System.out.println(LogIn.userName);
//			System.out.println(LogIn.isAdmin);
			
			//4. 添加实例
//			String classOUID = "86055efd"; // product class ouid, got from 'Object Modeler'
//			DOSChangeable dosObject = new DOSChangeable();
//
//			dosObject.setClassOuid(classOUID);
//			dosObject.put("aniPrep", "test..."); // required field
//			dosObject.put("md$description", "ani' test product"); // optional field
//			dosObject.put("remarks", "no remark"); // optional field
//
//			dos.add(dosObject);

			//n. ...
			//...
			
			//n+1. connect Oracle database and execute SQL without dyna service
			Connection conn = null;
			Statement stmt = null;
			ResultSet rset = null;
			
			try {
				// Force loading of the Oracle JDBC driver
				Class.forName ("oracle.jdbc.driver.OracleDriver");
				
				// retrieve connection
				String url = "jdbc:oracle:thin:@127.0.0.1:1521:LEARN";
				String user = "dynasys";
				String pwd = "1";
				conn = DriverManager.getConnection(url, user, pwd);
				
				/*// Another way to access database via Oracle JDBC driver, 
				// "import oracle.jdbc.pool.OracleDataSource" directive is required 
				// and it need Oracle listener
			    // Create a OracleDataSource instance explicitly
			    OracleDataSource ods = new OracleDataSource();
			    
			    // Set the user name, password, driver type and network protocol
			    ods.setUser("dynasys");
			    ods.setPassword("1");
			    ods.setDriverType("oci8");
			    ods.setNetworkProtocol("ipc");
			    
			    // Retrieve a connection
			    conn = ods.getConnection();
			    //*/
				
			    // Execute some directive
			    // Create a Statement
			    stmt = conn.createStatement ();

			    // Select the USER column from the dual table
			    rset = stmt.executeQuery(
			    		"SELECT * " + 
							"FROM DYNASYS.DOSACT " + 
							"WHERE ( OUID = '2248553786' )");

			    // Iterate through the result and print the USER
			    while (rset.next ())
			      System.out.println("Script Name: " + rset.getString("name")
							+ "\nDiscription: " + rset.getString("des"));

			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "@_@! Something goes wrong!\n"
						+ sqle.toString());
				
			} finally {
			    // clean up ResultSet, Statement and Connection
				if (rset != null) { try { rset.close(); rset = null; } catch (Exception e) {} }
				if (stmt != null) { try { stmt.close(); stmt = null; } catch (Exception e) {} }
				if (conn != null) { try { conn.close(); conn = null; } catch (Exception e) {} }
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "@_@! Something goes wrong!\n"
					+ e.toString());
			
			throw e;
		}
	}
}
