package com.bookdatasevlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/deleteurl")
public class DeletScreenServlet extends HttpServlet {
	private static final String query="DELETE FROM BOOKDATA WHERE ID=?";
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
			int cnt=ps.executeUpdate();
			if(cnt==1)
			{
				out.println("<h2>Record is Successfully Deleted</h2>");
			}
			else
			{
				out.println("<h2>Record is Not Successfully Deleted</h2>");
			}
			con.close();		}
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


