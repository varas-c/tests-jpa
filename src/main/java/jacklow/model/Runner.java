package jacklow.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;




public class Runner {	
	
	public static void main(String [] args){
		
		
		EntityManager mg = JPAUtility.getEntityManager();
		
		
		mg.getTransaction().begin();
		
		
		Empresa unaEmpresa = new Empresa("Empresa de Carlos");
		unaEmpresa.addCuenta(new Cuenta("UnaCuenta", "2016", "unValor", unaEmpresa));
		unaEmpresa.addCuenta(new Cuenta("OtraCuenta","2019", "blablabla", unaEmpresa));
		
		
		mg.persist(unaEmpresa);
		mg.getTransaction().commit();
		
		
		Empresa otraEmpresa = (Empresa) mg.find(Empresa.class, 1L);
		otraEmpresa.getCuentas().forEach(x -> System.out.println(x.getNombre()));
		
		otraEmpresa.addCuenta(new Cuenta("unUpdate", "update", "update", otraEmpresa));
		
		
		
		mg.getTransaction().begin();
		mg.persist(otraEmpresa);
		mg.getTransaction().commit();
		
		otraEmpresa = (Empresa) mg.find(Empresa.class, 1L);
		otraEmpresa.getCuentas().forEach(x -> System.out.println(x.getNombre()));
		
		
		/*
		EntityManager entityManager = JPAUtility.getEntityManager();
		Repo repo = new Repo(entityManager);
		EntityTransaction tx = entityManager.getTransaction(); //Prueba de inheritance
		
		tx.begin();
		entityManager.persist(new Vehiculo("unaVTU","unaPatente"));
		entityManager.persist(new Robo());
		
		tx.commit();
		
		Evento evento = entityManager.find(Evento.class,new Long(1));
		
		System.out.println(evento.getClass());//Consulto por la superClase y se da cuenta por el DTYPE que clase es
	
	

		tx.begin();
		
		Vehiculo vehiculo1 = new Vehiculo("otraVtu","unaPatente");
		repo.agregar(vehiculo1);
		
		Vehiculo vehiculo = repo.findById(1);
	
		Robo robo = new Robo();
		robo.setVehiculo(vehiculo);
		
		entityManager.persist(robo);
		
		tx.commit();
		tx.begin();
		
		Vehiculo vehiculo1 = new vehiculo("otraVtu","unaPatente");
		repo.agregar(Vehiculo);
		tx.commit();
	
		*/
	}
}
