package jacklow.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries(value = 
{
	@NamedQuery(name="Empresa.findByName", query="SELECT e FROM Empresa e WHERE e.nombre = :nombre")
})

public class Empresa {
	

	private Long id;
	private String nombre;
	private List<Cuenta> cuentas;
	
	
	@Id
	@GeneratedValue
	@Column(name="EMPRESA_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(
	        mappedBy = "empresa", 
	        cascade = CascadeType.ALL,
	        orphanRemoval=true
	)
	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Empresa() {};
	
	public String getNombre() {
		return nombre;
	}
	
	public Empresa(String nombre) {
		this.nombre = nombre;
		cuentas = new ArrayList<Cuenta>();
	}
	
	
	public void addCuenta(Cuenta unaCuenta) {
		this.cuentas.add(unaCuenta);
	}
	

	
}
