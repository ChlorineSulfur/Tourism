package com.groupT01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.groupT01.model.Forum;

public class ForumDao {
	static Date date;

	public ResultSet lookup(Connection con) throws SQLException {

		StringBuffer sb = new StringBuffer("select * from Forum");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();

		return rs;
	}

	public int num(Connection con) throws SQLException {
		PreparedStatement ps = null;
		String sql = "select * from Forum";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		int count = 0;// 总的记录数
		while (rs.next()) {
			count++;
		}
		return count;
	}

	public int insert(Forum f, Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer("insert into forum(fname,fiden,forumday,comm) values (?,?,?,?)");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, f.getFname());
		pstmt.setString(2, f.getIdentity());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String x = f.getForumdate();

		try {
			date = sdf.parse(x);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		pstmt.setDate(3, sqlDate);
		pstmt.setString(4, f.getComment());

		if (pstmt.executeUpdate() > 0)
			i = 50;
		return i;
	}

	public int delete(String id, Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer("delete from forum where fid=?");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, id);

		if (pstmt.executeUpdate() > 0)
			i = 100;
		return i;
	}

}
