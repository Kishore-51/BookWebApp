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

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
	private static final String query="SELECT BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA WHERE ID=?";
	 private static final String jdbcurl = "jdbc:oracle:thin:@//Gipsy:1521/orcl";
	 private static final String db_username = "KishoreDB";
	 private static final String db_password = "kishore";
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		  
	    //getting printwriter obj
		PrintWriter out=res.getWriter();
		//setting content type
		res.setContentType("text/html");
		//getting the book information from EditBookServletpage to EditScreenServlet
		int id=Integer.parseInt(req.getParameter("id"));
		
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
			ps.setInt(1, id);
			ResultSet resultset=ps.executeQuery();
			resultset.next();
			out.println("<form action='edit?id="+id+" ' method='post'>");
			out.println("<table border='1' align='center'>");
			out.println("<tr>");
			out.println("<td>Book Name</td>");
			out.println("<td><input type='text' name ='bookname' value=' "+resultset.getString(1)+ " '></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Book Edition</td>");
			out.println("<td><input type='text' name ='bookedition' value=' "+resultset.getString(2)+ " '></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>Book Price</td>");
			out.println("<td><input type='text' name ='bookprice' value=' "+resultset.getFloat(3)+ " '></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td><input type='submit' value='Update'></td>");
			out.println("<td><input type='reset' value='Clear'></td>");
			out.println("</tr>");


			
			out.println("</table>");
			out.println("</form>");
			con.close();
		}
		catch(SQLException se)
		{
			se.printStackTrace();
			out.println("<h2>"+se.getMessage()+"</h2>");
		}
		out.println("<a href='Home.html'>HOME</a>");
		out.println("<br>");
		out.println("<a href='booklist'>Book List</a>");
		
		
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		doGet(req,res);
	}
}

