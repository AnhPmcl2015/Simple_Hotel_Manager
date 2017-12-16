package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.DBConnection;
import dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class CustomerDAO {
	public static ObservableList<Customer> listCustomer;
	
	public static void getCustomer() throws SQLException {
		
		if(listCustomer == null) {
			listCustomer = FXCollections.observableArrayList();
		}else {
			listCustomer.clear();
		}
		
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from Customer";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			String customerId = rs.getString(1);
			String customerName = rs.getString(2);
			String customerAddress = rs.getString(3);
			String governmentId = rs.getString(4);
			int customerTypeId = rs.getInt(5);
			
			listCustomer.add(new Customer(customerName, customerAddress, governmentId, customerId, customerTypeId));
		}
		con.close();
	}
	
	public static void addNewCustomer(Customer customer) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "insert into customer values(?,?,?,?,?)";
		PreparedStatement pr = con.prepareStatement(sql);

		pr.setString(1, customer.getCustomerId().get());
		pr.setString(2, customer.getPeopleName().get());
		pr.setString(3, customer.getPeopleAddress().get());
		pr.setString(4, customer.getGovernmentId().get());
		pr.setInt(5, customer.getCustomerTypeId().get());
		
		pr.executeUpdate();
		con.close();
		
		getCustomer();
	}
}
