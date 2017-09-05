package db;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

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
	
	
	@Test
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
	
	@Test
	public void buscarCuentaEnDb() {
		Empresa otraEmpresa = this.buscarEmpresaId(1L);
		assertEquals(otraEmpresa.getCuentas().size(), 3);
		assertEquals(otraEmpresa.getCuentas().get(0).getNombre(), "UnaCuenta");
		assertEquals(otraEmpresa.getCuentas().get(1).getNombre(), "OtraCuenta");
		assertEquals(otraEmpresa.getCuentas().get(2).getNombre(), "Ebitda");
	}
	
	@Test
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
	
	@Test
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
	
	

}
