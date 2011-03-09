/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package iimb;

import java.sql.*;


public class main2{

public static void main(String args[]){
String dbtime;
String dbUrl = "jdbc:mysql://localhost:3306/iimb";
String dbClass = "com.mysql.jdbc.Driver";
String query = "Select * FROM employee";

try {

Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection (dbUrl);
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);

while (rs.next()) {
dbtime = rs.getString(1);
System.out.println(dbtime);
} //end while

con.close();
} //end try

catch(ClassNotFoundException e) {
e.printStackTrace();
}

catch(SQLException e) {
e.printStackTrace();
}

}  //end main

}  //end class