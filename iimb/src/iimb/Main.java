package iimb;
import java.sql.Connection;
import java.sql.DriverManager;
public class Main
   {
       public static void main (String[] args)
       {
           Connection conn = null;

           try
           {
               String userName = "root";
               String password = "root123";
               String url = "jdbc:mysql://localhost:3306/iimb";
               Class.forName ("com.mysql.jdbc.Driver").newInstance ();
               conn = DriverManager.getConnection (url,userName,password);
               System.out.println ("Database connection established");
           }
           catch (Exception e)
           {
               System.err.println (e.getMessage());
           }
           finally
           {
               if (conn != null)
               {
                   try
                   {
                       conn.close ();
                       System.out.println ("Database connection terminated");
                   }
                   catch (Exception e) {  }
               }
           }
       }
   }