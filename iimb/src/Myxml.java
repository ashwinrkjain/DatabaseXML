import java.io.*;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;

import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Myxml
{
    public static void main(String args[])
    {
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        String querry;
         Connection con=null;
         Statement stmt=null;
         int iterate=1,cols=1;

        try
        {


            System.out.println("Enter the querry");
            querry=br.readLine();

            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/iimb?"+"user=root&password=root123");
            stmt=con.createStatement();
             ResultSet rs=null;

             if(querry.startsWith("select"))
            {
                rs=stmt.executeQuery(querry);
            }
             else
             {
                 int retrn=stmt.executeUpdate(querry);
                 if(retrn==1)
                 {
                     System.out.println("Querry OK,successful !");
                     System.exit(0);
                 }
                 else
                 {
                     System.out.println("Querry not OK,unsuccessful !");
                      System.exit(0);
                 }
             }

            DocumentBuilderFactory docFact= DocumentBuilderFactory.newInstance();
            DocumentBuilder docB=docFact.newDocumentBuilder();

            Document doc = docB.newDocument();
            Element rootE=doc.createElement("COMPANY");
            doc.appendChild(rootE);

             cols=rs.getMetaData().getColumnCount();
           // String res= rs.getMetaData().toString();
            //  System.out.println(cols);
            while(rs.next())
            {
                 Element emp=doc.createElement("EMPLOYEE");
                 rootE.appendChild(emp);

                 while(iterate<=cols)
                    {
                      // System.out.print(rs.getMetaData().getColumnLabel(iterate)+":");
                     //  System.out.println("\t"+rs.getString(iterate));
                       Element child=doc.createElement(rs.getMetaData().getColumnLabel(iterate));
                        child.appendChild(doc.createTextNode(rs.getString(iterate)));
                        emp.appendChild(child);
                      // System.out.println("\n");
                       iterate++;
                    }

                iterate=1;
            }
            TransformerFactory tfact=TransformerFactory.newInstance();
            Transformer tformer=tfact.newTransformer();
            DOMSource src=new DOMSource(doc);
            StreamResult result=new StreamResult(new File("c:/xml1.xml"));
            tformer.transform(src,result);

        }

        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            e.toString();
        }
        catch(ClassNotFoundException e)
        {
           // System.out.println("Invalid Querry !!");
            e.printStackTrace();
        }
       catch(ParserConfigurationException e)
        {
            System.err.println(e.getMessage());
        }
        catch(TransformerConfigurationException e)
        {
            System.err.println(e.getMessage());
        }
        catch(TransformerException e)
        {
            System.out.println(e.getMessage());
        }
         finally
        {
             try
             {
                con.close();
                stmt.close();
             }
             catch(SQLException e)
             {
                 System.out.println(e);
             }
         }
    }
}



