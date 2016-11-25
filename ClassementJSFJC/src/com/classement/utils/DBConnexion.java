package com.classement.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnexion {
	//private static DBConnexion instance;
	private Properties props;
	private Connection cnx;

	public DBConnexion() {
		//on va chercher les données dans le fichier properties créé dans le package default.
		props = new Properties();
		try {
			props.load(getClass().getResourceAsStream("connexion.properties"));
			//	            props.load(getClass().getClassLoader().getResourceAsStream(arg0));
		} catch (IOException ex) {
			Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			Class.forName(com.mysql.jdbc.Driver.class.getName());
			// ou bien Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException ex) {
			System.out.println("Impossible de charger le pilote jdbc");
		}

		//	        cnx = DriverManager.getConnection(this.props.getProperty("url"),
		//	                this.props.getProperty("user"),
		//	                this.props.getProperty("pass"));
		try {
			cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/classementdb",
					"root",
					"1234");
		} catch (SQLException e) {
			System.out.println("PROBLEME A LA CONNECTION !");
		}

	}

	public Connection getmConnexion() throws SQLException{
		//	        if(instance == null){
		//	            //On lance une instance en interne !!
		//	            instance = new DBConnexion();
		//	        }
		//	            
		//	            
		//	        return DriverManager.getConnection(instance.props.getProperty("url"),
		//	                instance.props.getProperty("user"),
		//	                instance.props.getProperty("pass"));
		return cnx;
	}


}
