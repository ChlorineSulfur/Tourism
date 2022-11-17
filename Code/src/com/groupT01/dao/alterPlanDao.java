package com.groupT01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.groupT01.model.alterPlan;

public class alterPlanDao {

	static Date date;

	public ResultSet lookup(Connection con) throws SQLException {

		StringBuffer sb = new StringBuffer("select * from pscheme");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	public int num(Connection con) throws SQLException {
		PreparedStatement ps = null;
		String sql = "select * from pscheme";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		int count = 0;// 总的记录数
		while (rs.next()) {
			count++;
		}
		return count;
	}

	public int insert(alterPlan p, Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer(
				"insert into pscheme(beginday,travelday,money,scenic1,scenic2,scenic3,scenic4,scenic5,scenic6) values (?,?,?,?,?,?,?,?,?)");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String x = p.getStartdate();

		try {
			date = sdf.parse(x);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		pstmt.setDate(1, sqlDate);
		pstmt.setInt(2, p.getDaynum());
		pstmt.setDouble(3, p.getMoney());
		pstmt.setString(4, p.getScenic1());
		if (p.getScenic2().equals("-"))
			pstmt.setNull(5, Types.CHAR);
		else
			pstmt.setString(5, p.getScenic2());

		if (p.getScenic3().equals("-"))
			pstmt.setNull(6, Types.CHAR);
		else
			pstmt.setString(6, p.getScenic3());

		if (p.getScenic3().equals("-"))
			pstmt.setNull(7, Types.CHAR);
		else
			pstmt.setString(7, p.getScenic3());

		if (p.getScenic4().equals("-"))
			pstmt.setNull(8, Types.CHAR);
		else
			pstmt.setString(8, p.getScenic4());

		if (p.getScenic5().equals("-"))
			pstmt.setNull(9, Types.CHAR);
		else
			pstmt.setString(9, p.getScenic5());

		if (pstmt.executeUpdate() > 0)
			i = 50;
		return i;
	}

	public int delete(String pid, Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer("delete from pscheme where pid=?");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, pid);

		if (pstmt.executeUpdate() > 0)
			i = 100;
		return i;
	}

}
