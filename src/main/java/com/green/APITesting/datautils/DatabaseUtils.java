package com.green.APITesting.datautils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database driver
 * 
 * @author Dieson Zuo
 */
public class DatabaseUtils {
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	private PreparedStatement pstmt = null;

	/**
	 * Initialization connection
	 * 
	 * @param dbUrl
	 *            database connection information dbUrl=HOST:POST:SID
	 * @param userName
	 *            user name
	 * @param password
	 *            password
	 */
	public DatabaseUtils(String url, String userName, String password) {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			// create database connection
			conn = DriverManager.getConnection(url, userName, password);
			// Query using Statement
			st = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Select
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet querySQL(String sql) {
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Update（delete，update，insert）
	 * 
	 * @param sql
	 */
	public void updateSQL(String sql) {
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Disconnect database
	 */
	public void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (st != null) {
				st.close();
				st = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * programmability
	 * @param programmability 
	 * @param parameters		
	 * @return
	 */
	public ResultSet executeSprocInParams(String programmability, String...parameters) {
		String size = "";
		for (int i = 0; i < parameters.length; i++) {
			size = size + "?,"; 
		}
		size = size.substring(0, size.length()-1);
		
		try {
			pstmt = conn.prepareStatement("{call " + programmability + "(" + size + ")}");
			for(int i = 0; i < parameters.length; i++){
				pstmt.setString(i+1, parameters[i]);
			}
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

}
