package drizzt.sf.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnection {
	private static Connection db_conn;

	public static DBConnection getInstance() {
		DBConnection cf = new DBConnection();
		return cf;

	}

	public static Connection getConnection() {
		ReadProperty rp = new ReadProperty();
		String forName = rp.getParameter("forName");
		try {
			Class.forName(forName);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		String url = rp.getParameter("url");
		String user = rp.getParameter("user");
		String psw = rp.getParameter("psw");
		try {
			db_conn = DriverManager.getConnection(url, user, psw);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return db_conn;
	}

	public static Connection getJndiConnection() {
		ReadProperty rp = new ReadProperty();
		String jndiName = rp.getParameter("jndiName");

		try {
			Context context=new InitialContext();
            DataSource  ds=(DataSource)context.lookup("java:/comp/env/"+jndiName);
            db_conn=ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return db_conn;
	}

	public static void close(Connection conn, ResultSet rest, Statement st) {
		try {
			if (rest != null) {
				rest.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("DBUtils: Cannot close connection.");
		}

	}

	public static void close(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			System.out.println("DBUtils: Cannot close statement.");
		}

	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			System.out.println("DBUtils: Cannot close resultset.");
		}
	}

}
