package jacklow.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtility {

	private static final EntityManagerFactory empFactory;
		static {
			empFactory = Persistence.createEntityManagerFactory("testing");
		}
	
	public static EntityManager getEntityManager(){
		return empFactory.createEntityManager();
	}
	
	public static void close(){
		empFactory.close();
	}
}
