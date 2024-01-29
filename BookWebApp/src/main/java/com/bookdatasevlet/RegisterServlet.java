package com.bookdatasevlet;





import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	//creating query
	private static final String query="INSERT INTO BOOKDATA (BOOKNAME,BOOKEDITION,BOOKPRICE) VALUES(?,?,?)";
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
		String bookname=req.getParameter("bookname");
		String bookedition=req.getParameter("bookedition");
		Float bookprice=Float.parseFloat(req.getParameter("bookprice"));
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
			//inserting values into database from webpage
			ps.setString(1,bookname);
			ps.setString(2, bookedition);
			ps.setFloat(3, bookprice);
			int cnt=ps.executeUpdate();
			if(cnt==1)
			{
				out.println("<h2>Recorded sucessfully</h2> ");
			}
			else
			{
				out.println("<h2>Not Recorded sucessfully</h2> ");

			}
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

