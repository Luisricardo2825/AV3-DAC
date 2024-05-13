package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe responsavel pela criação de um EntityManager. Nota: o
 * EntityManagerFactory
 * é responsavel por criar uma conexão com o banco
 * de dados, via um gerenciador de entidades previamente criadas.
 */
public class JPAUtil {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityJogo");
	

	/**
	 * Metodo responsavel por criar um EntityManager
	 * 
	 * @return EntityManager
	 */
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
}
