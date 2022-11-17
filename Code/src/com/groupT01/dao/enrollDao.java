package com.groupT01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.groupT01.view.LOGIN;

public class enrollDao {

	public ResultSet lookup(Connection con) throws SQLException {

		PreparedStatement ps = null;
		String sql = "select A.cid,A.sid,B.cname,B.csex,B.ctel,B.cage,B.vip from enroll A inner join client B on A.cid=B.cid";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);

		return rs;

	}

	public int num(Connection con) throws SQLException {
		PreparedStatement ps = null;
		String sql = "select * from enroll";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		int count = 0;// 总的记录数
		while (rs.next()) {
			count++;
		}
		return count;
	}

	public ResultSet query(Connection con, String sid) throws SQLException {

		PreparedStatement ps = null;
		String sql ="select A.cid,A.sid,B.cname,B.csex,B.ctel,B.cage,B.vip from enroll A inner join client B on A.cid=B.cid where A.sid in ( "+sid+ ") ";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		return rs;

	}

	public ResultSet look(Connection con, String sid) throws SQLException {

		StringBuffer sb = new StringBuffer(
				"select A.sid,A.gid,A.pid,B.gname,B.gsex,B.gtel,B.gage,B.grade,C.beginday,C.travelday,C.money,C.scenic1,C.scenic2,C.scenic3,C.scenic4,C.scenic5,C.scenic6 from scheme A inner join guide B on A.gid=B.gid inner join pscheme C on C.pid = A.pid where A.sid=?");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, sid);
		ResultSet rs = pstmt.executeQuery();

		return rs;

	}
	
	
	//Guide
	public ResultSet query_g(Connection con, String sid) throws SQLException {

		PreparedStatement ps = null;
		String sql = "select A.cid,A.sid,B.cname,B.csex,B.ctel,B.cage,B.vip from enroll A inner join client B on A.cid=B.cid where A.sid in ("+sid+") ";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);


		return rs;

	}
	
	public int num_g(Connection con,String sid) throws SQLException {
		PreparedStatement ps = null;
		String sql = "select * from enroll where sid in ("+sid+") ";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		int count = 0;// 总的记录数
		while (rs.next()) {
			count++;
		}
		return count;
	}
	
	public ResultSet lookup_scheme(Connection con, int gid) throws SQLException {

	
		StringBuffer sb = new StringBuffer("select sid from scheme where gid = ?");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setInt(1, gid);
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}
}
