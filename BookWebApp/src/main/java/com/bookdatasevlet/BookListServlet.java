package com.bookdatasevlet;




import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/booklist")
public class BookListServlet extends HttpServlet {
	//creating query
		private static final String query="SELECT ID,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA";
		 private static final String jdbcurl = "jdbc:oracle:thin:@//Gipsy:1521/orcl";
		 private static final String db_username = "KishoreDB";
		 private static final String db_password = "kishore";
		
		public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException 
		{
			  
		    //getting printwriter obj
			PrintWriter out=res.getWriter();
			//setting content type
			res.setContentType("text/html");
			//getting the book information from homepage to servlet
			
			//loading jdbc drivers
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			catch(ClassNotFoundException x)
			{
				x.printStackTrace();
				out.println("<h2>"+x.getMessage()+"</h2>");
			}
			//generating connection
			try(Connection con=DriverManager.getConnection(jdbcurl,db_username,db_password);
					PreparedStatement ps=con.prepareStatement(query);)
			{
				ResultSet resultset=ps.executeQuery();
				out.println("<table border='1' align='center'>");
				out.println("<tr>");
				out.println("<th>Book ID</th>");
				out.println("<th>Book Name</th>");
				out.println("<th>Book Edition</th>");
				out.println("<th>Book Price</th>");
				out.println("<th>Edit</th>");
				out.println("<th>Delete</th>");
				out.println("</tr>");


				while(resultset.next())
				{
					out.println("<tr>");
					out.println("<td>"+resultset.getInt(1)+"</td>");
					out.println("<td>"+resultset.getString(2)+"</td>");
					out.println("<td>"+resultset.getString(3)+"</td>");
					out.println("<td>"+resultset.getFloat(4)+"</td>");
					out.println("<td><a href='editScreen?id="+resultset.getInt(1)+"'>Edit</a></td>");
					out.println("<td><a href='deleteurl?id="+resultset.getInt(1)+"'>Delete</a></td>");		
					out.println("</tr>");
				}
				out.println("</table>");
				con.close();
			}
			catch(SQLException se)
			{
				se.printStackTrace();
				out.println("<h2>"+se.getMessage()+"</h2>");
			}
			out.println("<a href='Home.html'>HOME</a>");
			out.println("<br>");
			//out.println("<a href='editbookdata'>EDIT</a>");
			
			
			
		}
		public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
		{
			doGet(req,res);
		}

}

