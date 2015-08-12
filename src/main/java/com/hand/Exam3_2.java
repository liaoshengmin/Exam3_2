package com.hand;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Exam3_2 {

	public static void main(String[] args) {
		System.out.print("请输入Customer ID：");
		Scanner scaner = new Scanner(System.in);
		String customerId = scaner.next();
		
		Connection conn = new ConnectionMySql().makeConnectionMySql();
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String sql1="select first_name,last_name from customer where customer_id =?";
		String sql2 = "select f.film_id,f.title,r.rental_date from rental r,inventory i,film_text f "
				+ "where r.inventory_id = i.inventory_id and i.film_id = f.film_id and r.customer_id=? order by rental_date desc;";
		
		try {
			
			PreparedStatement ps1 = conn.prepareCall(sql1);
			ps1.setString(1, customerId);
			rs1 = ps1.executeQuery();
			while(rs1.next()){
				System.out.println(rs1.getString("first_name")+"."+rs1.getString("last_name")+"租用的Film:");
			}
			
			
			
			
			PreparedStatement ps2 = conn.prepareCall(sql2);
			ps2.setString(1, customerId);
			rs2 = ps2.executeQuery();
//			System.out.println("CountryID"+customerId+"所属的城市：");
			System.out.println("Film ID|Film名称|租用时间");
			while(rs2.next()){
				System.out.println(rs2.getInt("film_id")+"|"+rs2.getString("title")+"|"+rs2.getString("rental_date"));
				
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}

}


class ConnectionMySql {

	public Connection makeConnectionMySql(){

		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila","root","");
		
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
}
