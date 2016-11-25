package com.classement.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classement.business_object.Concurrent;
import com.classement.data_access_object.ClassementConcurrentDAO;

/**
 * Servlet implementation class TestCnx
 */
@WebServlet("/TestCnx")
public class TestCnx extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestCnx() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	super.service(arg0, arg1);
    	try {
			DBConnexion inst = new DBConnexion();
			Connection cnx = inst.getmConnexion();
//			System.out.println(cnx.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Concurrent c = new Concurrent();
    	c.setNom("Azzaz");
    	c.setPrenom("Julien");
    	c.setTemps(10.25f);
    	ClassementConcurrentDAO.creeConcurrent(c);
    	Concurrent conc = ClassementConcurrentDAO.trouveConcurrentParNo(2);
    	System.out.println(conc.getNom());
    	
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
