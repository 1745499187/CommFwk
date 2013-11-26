package com.chinawiserv.service.alertserver.auth;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinawiserv.service.alertserver.util.SecurityUtil;
import com.chinawiserv.service.alertserver.util.StringUtil;

//import org.springframework.jdbc.core.JdbcTemplate;

public class AuthUser {
	private static final Logger logger = LoggerFactory.getLogger(AuthUser.class);

	// private JdbcTemplate jdbcTemplate;

	private DataSource dataSource;

	private String fieldUser;

	private String fieldPassword;

	private String tableUsers;

	private String sql;

	private boolean encode = false;

	private String isAvailable = "";
	private String isAvailableValue = "";
	private String jndiName = "";

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void setIsAvailableValue(String isAvailableValue) {
		this.isAvailableValue = isAvailableValue;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public void setEncode(boolean encode) {
		this.encode = encode;
	}

	public boolean auth(String username, String password) {
		return true;
		//return authenticateUsernamePassword(username, password);
	}

	public final boolean authenticateUsernamePassword(String username, String password) {
		String md5Password = getMD5(password);

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			if (this.sql == null || sql.length() == 0) {
				this.sql = "SELECT " + fieldPassword + " from "
						+ this.tableUsers + " Where " + this.fieldUser
						+ " = ? ";
				if (isAvailable != null && isAvailable.length() > 0) {
					sql = sql + " AND " + isAvailable + " in ( "
							+ isAvailableValue + ")";
				}
			}
			String dbPassword = "";
			logger.info(sql);
//			 dbPassword = getJdbcTemplate().queryForObject(this.sql, String.class, username);

			if (encode && dbPassword.equals(password)) {
				return true;
			}
			if (dbPassword.equalsIgnoreCase(md5Password)) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			rs = null;
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			stmt = null;
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			conn = null;
		}

		return false;
	}

	public String getMD5(String s) {
		String md5sum = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] hash = md5.digest(s.getBytes());

			md5sum = dumpBytes(hash);
		} catch (Exception e) {
		}

		return md5sum;

	} // end getMD5()

	private String dumpBytes(byte[] bytes) {
		int size = bytes.length;
		StringBuffer sb = new StringBuffer(size * 2);
		String s;

		for (int i = 0; i < size; i++) {

			s = Integer.toHexString(bytes[i]);

			if (s.length() == 8) // -128 <= bytes[i] < 0
				sb.append(s.substring(6));
			else if (s.length() == 2) // 16 <= bytes[i] < 128
				sb.append(s);
			else
				sb.append("0" + s); // 0 <= bytes[i] < 16
		}

		return sb.toString();
	}

	/**
	 * @param fieldPassword
	 *            The fieldPassword to set.
	 */
	public final void setFieldPassword(final String fieldPassword) {
		this.fieldPassword = fieldPassword;
	}

	/**
	 * @param fieldUser
	 *            The fieldUser to set.
	 */
	public final void setFieldUser(final String fieldUser) {
		this.fieldUser = fieldUser;
	}

	/**
	 * @param tableUsers
	 *            The tableUsers to set.
	 */
	public final void setTableUsers(final String tableUsers) {
		this.tableUsers = tableUsers;
	}

	/**
	 * Method to set the datasource and generate a JdbcTemplate.
	 * 
	 * @param dataSource
	 *            the datasource to use.
	 */
	// public final void setDataSource(final DataSource dataSource) {
	// this.jdbcTemplate = new JdbcTemplate(dataSource);
	// this.dataSource = dataSource;
	// }

	/**
	 * Method to return the jdbcTemplate
	 * 
	 * @return a fully created JdbcTemplate.
	 */
	// protected final JdbcTemplate getJdbcTemplate() {
	// return this.jdbcTemplate;
	// }

	protected final DataSource getDataSource() {
		return this.dataSource;
	}

	public static void main(String args[]) {
		// org.jasig.cas.authentication.handler.Md5PasswordEncoder e = new
		// org.jasig.cas.authentication.handler.Md5PasswordEncoder();
		// AuthUser authUser = new AuthUser();
		// System.out.println(authUser.getMD5("111111"));
	}
}
