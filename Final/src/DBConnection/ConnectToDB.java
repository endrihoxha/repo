package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectToDB {
	
	

	public Connection conn = null;
	
	
	public Connection connect()
	{
		try{
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/fingerprint?useSSL=false","root","test");
			
			return conn;
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage());
			return null;
		}
	}

	public void disconnect()
	{
		conn = null;
		System.out.println("DB Disconnected !!!");
	}

}
