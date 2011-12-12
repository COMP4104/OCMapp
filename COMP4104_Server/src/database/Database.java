package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database
{
	static Connection conn;
	static Statement st;

	public static List<Object[]> runQuery(String tableName, String conditions)
	{
		ResultSet set = null;
		List<Object[]> results = new ArrayList<Object[]>();
		try
		{
			set = st.executeQuery("select * from " + tableName + " "
					+ conditions + ";");
			ResultSetMetaData metadata = set.getMetaData();
			for (int i = 0; i < metadata.getColumnCount(); i++)
			{
				System.out.print("\t" + metadata.getColumnLabel(i + 1));
			}
			System.out.println("\n----------------------------------");

			while (set.next())
			{
				Object[] temp = new Object[10];
				for (int i = 0; i < 10; i++)
				{
					try
					{
						temp[i] = set.getObject(i + 1);
					}
					catch (Exception e)
					{
						break;
					}
				}
				results.add(temp);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;
	}

	public static void connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			conn = DriverManager.getConnection(
					"jdbc:mysql://99.240.156.41:3306/ocmapp", "root", "custin8");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			st = conn.createStatement();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void disconnect()
	{
		try
		{
			st.close();
			conn.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
