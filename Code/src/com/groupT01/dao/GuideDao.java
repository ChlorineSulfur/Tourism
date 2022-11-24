package com.groupT01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.groupT01.model.Guide;

public class GuideDao {

	static Date date;

	public ResultSet lookup(Connection con) throws SQLException {

		StringBuffer sb = new StringBuffer("select * from Guide");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();

		return rs;
	}

	public ResultSet find(Connection con, String gid) throws SQLException {

		StringBuffer sb = new StringBuffer("select * from Guide where GID=?");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, gid);
		ResultSet rs = pstmt.executeQuery();

		return rs;
	}

	public int num(Connection con) throws SQLException {
		PreparedStatement ps = null;
		String sql = "select * from Guide";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		int count = 0;// 总的记录数
		while (rs.next()) {
			count++;
		}
		return count;
	}

	public int insert(Guide g, Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer(
				"insert into Guide(GNAME,GSEX,GIDEN,GTEL,GBIRTH,GAGE,GUSERNAME,GPWD,GRADE) values (?,?,?,?,?,?,?,?,?)");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, g.getName());
		pstmt.setString(2, g.getSex());
		pstmt.setString(3, g.getIden());
		pstmt.setString(4, g.getTel());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String x = g.GetBirth();

		try {
			date = sdf.parse(x);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		pstmt.setDate(5, sqlDate);

		pstmt.setInt(6, g.getAge());
		pstmt.setString(7, g.getUser());
		pstmt.setString(8, g.getPWD());
		pstmt.setString(9, g.getGrade());

		if (pstmt.executeUpdate() > 0)
			i = 50;
		return i;
	}

	public int delete(String id, Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer("delete from Guide where GIDEN=?");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, id);

		if (pstmt.executeUpdate() > 0)
			i = 100;
		return i;
	}
	
	public int update(String ziduan, String value, String id, Connection con) throws SQLException {
		int i = 0;
		int rs=0;
		Statement stmt = con.createStatement();  
		if(ziduan.equals("GBIRTH")) {
			rs = stmt.executeUpdate("update Guide set "+ziduan+" =  to_date ( '"+value+"' , 'YYYY-MM-DD' ) where giden = "+ id+ " ");
		}else {
			rs = stmt.executeUpdate("update Guide set "+ziduan+" = '" +value+ "' where giden = "+id+" ");  
		}
		if(rs>0) {
			i=25;
		}
		return i;
	}
}
