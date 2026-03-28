package br.com.kaio.cadswingfiel.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EmFactory {

	private static EntityManager em;

	public static em getEm() {

		if (em == null) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("fielPersistenceUnit");
			emf.createEntityManager();
		}

	} 

	
	return em
}
