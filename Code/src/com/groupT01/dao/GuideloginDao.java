package com.groupT01.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.groupT01.model.*;

public class GuideloginDao {
	public GuideLoginUser find(Connection con, String ID, String pwd) throws SQLException {
		GuideLoginUser u = new GuideLoginUser();

		StringBuffer sb = new StringBuffer("select * from Guide where GIDEN =?");
		PreparedStatement pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, ID);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			if (!rs.getString("GPWD").equals(pwd)) {
				return null;
			} else {
				u.setUserID(rs.getString("GIDEN"));
				u.setUserName(rs.getString("Gusername"));
				u.setName(rs.getString("gname"));
				u.setGID(rs.getInt("GID"));

			}
		}
		return u;
	}
}
