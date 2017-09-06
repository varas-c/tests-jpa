package db;

import static org.junit.Assert.*;

import java.util.stream.Collector;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jacklow.model.Cuenta;
import jacklow.model.Empresa;
import jacklow.model.JPAUtility;

public class TestPersistencia {
	EntityManager manager = JPAUtility.getEntityManager();
	

	
	public Empresa crearEmpresaConTresCuentas() {
		Empresa unaEmpresa = new Empresa("Empresa de Carlos");
		unaEmpresa.addCuenta(new Cuenta("UnaCuenta", "2016", "19524.32", unaEmpresa));
		unaEmpresa.addCuenta(new Cuenta("OtraCuenta","2019", "15", unaEmpresa));
		unaEmpresa.addCuenta(new Cuenta("Ebitda","2015", "98.75", unaEmpresa));
		return unaEmpresa;
	}
	
	
	//@Test
	public void insertarUnaEmpresaConTresCuentas() {
		Empresa empresa = this.crearEmpresaConTresCuentas();
		
		manager.getTransaction().begin();
		manager.persist(empresa);
		manager.getTransaction().commit();
		assertTrue(manager.contains(empresa));
	}
	
	
	private Empresa buscarEmpresaId(Object id) {
		return (Empresa) manager.find(Empresa.class, id);
	}
	
	//@Test
	public void buscarCuentaEnDb() {
		Empresa otraEmpresa = this.buscarEmpresaId(1L);
		assertEquals(otraEmpresa.getCuentas().size(), 3);
		assertEquals(otraEmpresa.getCuentas().get(0).getNombre(), "UnaCuenta");
		assertEquals(otraEmpresa.getCuentas().get(1).getNombre(), "OtraCuenta");
		assertEquals(otraEmpresa.getCuentas().get(2).getNombre(), "Ebitda");
	}
	
	//@Test
	public void borrarUnaCuentaYAhoraTiene2() {
		Empresa empresa = this.buscarEmpresaId(1L);
		assertEquals(empresa.getCuentas().size(),3);
		empresa.getCuentas().remove(2);
		manager.getTransaction().begin();
		manager.persist(empresa);
		manager.getTransaction().commit();
		
		empresa = null;
		empresa = this.buscarEmpresaId(1L);
		assertEquals(empresa.getCuentas().size(),2);
	}
	
	//@Test
	public void prueba() {
		Empresa empresa = this.buscarEmpresaId(1L);
		assertEquals(empresa.getCuentas().size(),2);
	}
	
	//@Test
	public void agregarCuentasYAhoraTiene5() {
		Empresa empresa = this.buscarEmpresaId(1L);
		
		empresa.addCuenta(new Cuenta("Update1", "blabla", "pipipi,", empresa));
		empresa.addCuenta(new Cuenta("Update2", "Probando un update", "pipipi,", empresa));
		empresa.addCuenta(new Cuenta("Update3", "tatatata", "pipipi,", empresa));
		
		manager.getTransaction().begin();
		manager.persist(empresa);
		manager.getTransaction().commit();
		
		empresa = null;
		
		empresa = this.buscarEmpresaId(1L);
		assertEquals(empresa.getCuentas().size(),5);
	}
	
	//@Test 
	public void verificoQueTenga5() {
		Empresa empresa = this.buscarEmpresaId(1L);
		assertEquals(empresa.getCuentas().size(),5);
	}
	
	//@Test
	public void modificoCuentaEbitda() {
		Empresa empresa = this.buscarEmpresaId(1L);
		empresa.getCuentas().get(2).setNombre("Hackeada");
		
		manager.getTransaction().begin();
		manager.persist(empresa);
		manager.getTransaction().commit();
	}
	
	//@Test
	public void verificoQueTengaCuentaHackeada() {
		Empresa empresa = this.buscarEmpresaId(1L);
		assertEquals(empresa.getCuentas().get(2).getNombre(), "Hackeada");
	}
	
	//@Test
	public void creamosUnaNuevaEmpresa() {
		Empresa empresa = new Empresa("Una nueva empresa");
		empresa.addCuenta(new Cuenta("1","1","1",empresa));
		
		manager.getTransaction().begin();
		manager.persist(empresa);
		manager.getTransaction().commit();
	}
	
	public Empresa buscarPorNombre(String nombre) {
		return (Empresa) manager.createNamedQuery("Empresa.findByName").setParameter("nombre", nombre).getSingleResult();
		
	}
	
	//@Test
	public void buscamosEmpresaNuevaPorNombre() {
		Empresa empresa = null;
		empresa = this.buscarPorNombre("Una nueva empresa");
		assertEquals(empresa.getNombre(),"Una nueva empresa");
		assertEquals(empresa.getCuentas().size(),1);
	}
	
	//@Test
	public void sacamosLaCuentaHackeadaDeLaPrimerEmpresaYSeLaDamosAlaSegunda() {
		Empresa uno = this.buscarEmpresaId(1L);
		Empresa dos = this.buscarPorNombre("Una nueva empresa");
		
		//uno.getCuentas().forEach(x -> System.out.println(x.getNombre()));
		
		Cuenta unaCuentaHackeada = uno.getCuentas().stream().filter(x -> x.getNombre().equals("Hackeada")).findFirst().orElse(null);
		assertEquals(unaCuentaHackeada.getNombre(), "Hackeada");
		
		Boolean a = uno.getCuentas().removeIf(x -> x.getNombre().equals("Hackeada"));
		
		assertTrue(a);
		dos.addCuenta(unaCuentaHackeada);
		unaCuentaHackeada.setEmpresa(dos);
		
		manager.getTransaction().begin();
		manager.persist(uno);
		manager.persist(dos);
		manager.getTransaction().commit();
	}
	
	//@Test
	public void laEmpresaDosTieneDosCuentas() {
		Empresa empresa = this.buscarPorNombre("Una nueva empresa");
		assertEquals(empresa.getCuentas().size(),2);
	}
	
	//@Test
	public void borrarEmpresaDos() {
		Empresa empresa = this.buscarPorNombre("Una nueva empresa");
		
		manager.getTransaction().begin();
		manager.remove(empresa);
		manager.getTransaction().commit();
	}
	
	

}
