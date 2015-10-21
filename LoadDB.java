import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoadDB {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
		    conn =
		       DriverManager.getConnection("jdbc:mysql://localhost/ManTech_Events?" +
		                                   "user=root&password=1Qazxsw2_alaa");

		    // Do something with the Connection


		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

}
