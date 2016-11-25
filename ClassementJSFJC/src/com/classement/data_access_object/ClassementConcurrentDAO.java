package com.classement.data_access_object;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList; 

import com.classement.business_object.Concurrent; 
import com.classement.exception.UnknownConcurrentException;
import com.classement.utils.DBConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ClassementConcurrentDAO {
	private static List<Concurrent> concurrents = new ArrayList<Concurrent>();//Static pour "simuler" la BdD

	//	private static int noDossard = 1;//Static car commun à toutes les instances de la liste/BdD
	private static DBConnexion inst = new DBConnexion();

	/*
	 * Requêtes
	 */
	private final static String requeteAdd = "INSERT INTO concurrent (nom, prenom, temps) VALUES (?, ?, ?)";
	private final static String requeteGetAll = "SELECT * FROM concurrent";
	private final static String requeteGetId = "SELECT * FROM concurrent WHERE noDossard=?";
	private final static String requeteUpdate = "UPDATE concurrent SET nom = ?, prenom=?, temps=? WHERE noDossard=?";
	private final static String requeteDelete = "DELETE FROM concurrent WHERE noDossard=?";

	/*
	 * Variable de contrôle pour la MàJ
	 */
	private static Boolean faireMaj = false;

	private ClassementConcurrentDAO() { }
	/**
	 * Génére un numéro de concurrent et le stocke
	 * @param concurrent
	 */
	public static void creeConcurrent(Concurrent concurrent) {
		try {
			PreparedStatement stmt = inst.getmConnexion().prepareStatement(requeteAdd, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, concurrent.getNom());
			stmt.setString(2, concurrent.getPrenom());
			stmt.setFloat(3, concurrent.getTemps());
			stmt.executeUpdate();
			ResultSet keys = stmt.getGeneratedKeys();
			while(keys.next()){
				concurrent.setNoDossard(keys.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		updateConcurrents();
		faireMaj = true;
	}
	/**
	 * Met a jour un concurrent
	 * @throws UnknownConcurrentException quand le concurrent n'existe pas
	 * @param concurrent
	 */
	public static void MAJConcurrent(Concurrent concurrent) {
		try {
			PreparedStatement stmt = inst.getmConnexion().prepareStatement(requeteUpdate);
			stmt.setString(1, concurrent.getNom());
			stmt.setString(2, concurrent.getPrenom());
			stmt.setFloat(3, concurrent.getTemps());
			stmt.setInt(4, concurrent.getNoDossard());
			stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		updateConcurrents();
		faireMaj = true;

		//		int index = trouveIndexDuConcurrentParNo(concurrent.getNoDossard());
		//		if(index > -1) {
		//			concurrents.set(index, concurrent);
		//		} else {
		//			throw new UnknownConcurrentException(concurrent.getNoDossard());
		//		}


	}

	public static void updateConcurrents(){
		concurrents = new ArrayList<Concurrent>();
		try {
			PreparedStatement stmt = inst.getmConnexion().prepareStatement(requeteGetAll);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				concurrents.add(sqlToConcurrent(rs));
			}
		} catch (SQLException e) {
			System.out.println("WARNING: Problème dans la MàJ de la liste !");
		}
		faireMaj = false;
	}

	/**
	 * Retourne un concurrent selon son numéro de dossard
	 * @throws UnknownConcurrentException quand aucun concurrent en mémoire a ce numéro
	 * @param no le numéro de le concurrent
	 * @return un concurrent selon son numéro
	 */
	public static Concurrent trouveConcurrentParNo(int no) {
		Concurrent c = null;
		try {
			PreparedStatement stmt = inst.getmConnexion().prepareStatement(requeteGetId);
			stmt.setInt(1, no);
			stmt.executeQuery();
			ResultSet rs = stmt.getResultSet();
			while(rs.next()){
				c = sqlToConcurrent(rs);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;

		//		int index = trouveIndexDuConcurrentParNo(no);
		//		if(index > -1) {
		//			Concurrent concurrent = concurrents.get(index);
		//			return concurrent;
		//		}
		//		throw new UnknownConcurrentException(no);
	}

	/**
	 * @return la liste de concurrents stockée en mémoire (inchangeable)
	 */
	public static List<Concurrent> getTousLesConcurrents() {
		if( faireMaj ){
			updateConcurrents();
		}
		//		try {
		//			PreparedStatement stmt = inst.getmConnexion().prepareStatement(requeteGetAll);
		//			ResultSet rs = stmt.executeQuery();
		//			while(rs.next()){
		//				concurrents.add(sqlToConcurrent(rs));
		//			}
		//		} catch (SQLException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}


		return concurrents;
	}

	/**
	 * Détruit un concurrent
	 * @param le concurrent à supprimer
	 * @throws UnknownConcurrentException si le concurrent n'existe pas en mémoire
	 */
	public static void supprimeConcurrent(Concurrent concurrent) {
		supprimeConcurrent(concurrent.getNoDossard());
	}

	/**
	 * Détruit un concurrent
	 * @param le numéro de dossard du concurrent à supprimer
	 * @throws UnknownConcurrentException si le concurrent n'existe pas en mémoire
	 */
	public static void supprimeConcurrent(int no) {
		try {
			PreparedStatement stmt = inst.getmConnexion().prepareStatement(requeteDelete);
			stmt.setInt(1, no);
			stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		updateConcurrents();
		faireMaj = true;

		//		int index = trouveIndexDuConcurrentParNo(no);
		//		if(index > -1) {
		//			concurrents.remove(index);
		//		} else {
		//			throw new UnknownConcurrentException(no);
		//		}
	}

	//	private static int trouveIndexDuConcurrentParNo(int no) {
	//		for (int i = 0; i < concurrents.size(); i++) {
	//			Concurrent concurrent = concurrents.get(i);
	//			if(concurrent.getNoDossard()== no) {
	//				return i;
	//			}
	//		}
	//		return -1;
	//	}

	//Méthode d'externalisation de la création de Concurrent via un RS.
	private static Concurrent sqlToConcurrent(ResultSet rs) throws SQLException {
		Concurrent c = new Concurrent();
		c.setNoDossard(rs.getInt("noDossard"));
		c.setNom(rs.getString("nom"));
		c.setPrenom(rs.getString("prenom"));
		c.setTemps(rs.getFloat("temps"));
		return c;
	}


}
