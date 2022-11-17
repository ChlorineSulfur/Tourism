package com.groupT01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.groupT01.model.City;

public class CityDao {

	public ResultSet lookup(Connection con) throws SQLException {

		StringBuffer sb = new StringBuffer("select * from City");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		return rs;
	}

	public int num(Connection con) throws SQLException {
		PreparedStatement ps = null;
		String sql = "select * from City";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		int count = 0;// 总的记录数
		while (rs.next()) {
			count++;
		}
		return count;
	}

	public int insert(City c, Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer("insert into city(cityname,scenicname) values (?,?)");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, c.getCity());
		pstmt.setString(2, c.getSpot());

		if (pstmt.executeUpdate() > 0)
			i = 50;
		return i;
	}

	public int delete(String id, Connection con) throws SQLException {
		int i = 0;
		StringBuffer sb = new StringBuffer("delete from city where scenicname=?");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, id);

		if (pstmt.executeUpdate() > 0)
			i = 100;
		return i;
	}

}
