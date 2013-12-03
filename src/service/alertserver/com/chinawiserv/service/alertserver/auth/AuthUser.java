package com.chinawiserv.service.alertserver.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.fwk.util.MD5;
import com.chinawiserv.service.alertserver.ASConfig;

public class AuthUser {
	private static final Logger logger = LoggerFactory.getLogger(AuthUser.class);

	public boolean auth(String username, String password) {
		String dbPassword = password;
		if(ASConfig.getInstance().getBoolValue("DB_AUTH_PWD_IS_DIGEST_BY_MD5")) {
			dbPassword = MD5.digest(password);
		}
		
//		String sql = "select count(1) as cnt from User_ where screenName=? and password_=? and active_ in(1)";
		String sql = ASConfig.getInstance().getStringValue("DB_AUTH_VERIFY_SQL");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(ASConfig.getInstance().getStringValue("DB_DRIVER"));
			conn = DriverManager.getConnection(ASConfig.getInstance().getStringValue("DB_URL"), 
					ASConfig.getInstance().getStringValue("DB_USER"), 
					ASConfig.getInstance().getStringValue("DB_PASSWORD"));
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, dbPassword);
			rs = ps.executeQuery();
			if(rs.next()) {
				int count = rs.getInt(1);
				if(count == 1) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("Error when query database, please check db configurations", e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(ps != null) {
					ps.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

	public static void main(String args[]) {
		ASConfig.init("alertServer.properties");
		AuthUser authUser = new AuthUser();
		System.out.println(authUser.auth("admin", "admin"));
		System.out.println(authUser.auth("guest", "admin"));
	}
}
