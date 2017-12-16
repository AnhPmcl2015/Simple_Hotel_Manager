package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbconnection.DBConnection;
import dto.CustomerType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class CustomerTypeDAO {
	public static ObservableList<CustomerType> listCustomerType;
	
	public static ObservableList<String> listCustomerTypeName;
	
	public static void getCustomerType() throws SQLException {
		
		if(listCustomerType == null) {
			listCustomerType = FXCollections.observableArrayList();
		}else {
			listCustomerType.clear();
		}
		
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		Statement statement = con.createStatement();
		String sql = "select * from CustomerType";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			int customerTypeId = rs.getInt(1);
			String customerTypeName = rs.getString(2);
			float rate = rs.getFloat(3);
			listCustomerType.add(new CustomerType(customerTypeId, customerTypeName, rate));
		}
		con.close();
		
		if(listCustomerTypeName == null) {
			listCustomerTypeName = FXCollections.observableArrayList();
		}else {
			listCustomerTypeName.clear();
		}
		
		for(CustomerType i : listCustomerType) {
			listCustomerTypeName.add(i.getCustomerTypeName().get());
		}
	}
	
	public static void changeRate(float newValue) throws SQLException {
		Connection con = DBConnection.getConnection("DESKTOP-7G62O4Q", "HotelManager");
		
		String sql = "update CustomerType set customerTypeRate = ? where customerTypeId = 2";
		
		
		PreparedStatement pr = con.prepareStatement(sql);
		
		pr.setFloat(1, newValue);
		
		pr.executeUpdate();
		con.close();
		
		getCustomerType();
		
	}
	
}
