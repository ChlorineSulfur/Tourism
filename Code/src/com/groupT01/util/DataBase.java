package com.groupT01.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库工具类
 * 
 * @author ShenCl
 *
 */
public class DataBase {

	private static String dbUrl = "jdbc:oracle:thin:@localhost:1521:SULFUR";
	/* ?useUnicode=true&characterEncoding=UTF-8 */
	private static String dbUsername = "manager";
	private static String dbPassword = "JLU000000";
	private static String jdbcName = "oracle.jdbc.driver.OracleDriver";

	public Connection getCon() throws Exception {
		Class.forName(jdbcName);
		Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		con.setAutoCommit(true);
		return con;
	}

	public void closeCon(Connection con) throws SQLException {
		if (con != null) {
			con.close();
		}
	}

}
