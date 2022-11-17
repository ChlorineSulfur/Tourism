package com.groupT01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.groupT01.model.travelPlan;

public class travelPlanDao {
	public ResultSet lookup(Connection con) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "select A.sid,A.gid,A.pid,B.gname,B.gsex,B.gtel,C.beginday,C.travelday,C.money        from scheme A inner join guide B on A.gid=B.gid inner join pscheme C on C.pid = A.pid";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		return rs;

	}

	public int num(Connection con) throws SQLException {
		PreparedStatement ps = null;
		String sql = "select * from scheme";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		int count = 0;// 总的记录数
		while (rs.next()) {
			count++;
		}
		return count;
	}

	public int insert(travelPlan p, Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer("insert into scheme(gid,pid) values (?,?)");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setInt(1, p.getGid());
		pstmt.setInt(2, p.getPid());

		if (pstmt.executeUpdate() > 0)
			i = 50;
		return i;
	}

	public int delete(String id, Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer("delete from scheme where sid=?");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, id);

		if (pstmt.executeUpdate() > 0)
			i = 100;
		return i;
	}
	
	
	//特定导游
	
public ResultSet lookup_G(Connection con,String id) throws SQLException {
		
		PreparedStatement ps = null;
		String sql = "select A.sid,A.gid,A.pid,B.gname,B.gsex,B.gtel,C.beginday,C.travelday,C.money from scheme A inner join guide B on A.gid=B.gid inner join pscheme C on C.pid = A.pid where A.gid="+id+" ";
		
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery(sql);
		return rs;

	}

	public int num_G(Connection con,String id) throws SQLException {
		PreparedStatement ps = null;
		String sql = "select * from scheme where gid = "+id+" ";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		int count = 0;// 总的记录数
		while (rs.next()) {
			count++;
		}
		return count;
	}

	public int insert_G(travelPlan p, int gid,Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer("insert into scheme(gid,pid) values (?,?)");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setInt(1, gid);
		pstmt.setInt(2, p.getPid());

		if (pstmt.executeUpdate() > 0)
			i = 50;
		return i;
	}
}
