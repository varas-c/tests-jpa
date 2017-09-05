package jacklow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class Cuenta {


	private Long id;
	
	private String nombre;
	private String periodo;
	private String valor;
	
	

	private Empresa empresa;
	
	@Id
	@GeneratedValue
	@Column(name="CUENTA_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPRESA_ID", nullable=false)
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Cuenta(String nombre, String periodo, String valor, Empresa empresa) {
		this.nombre = nombre;
		this.periodo = periodo;
		this.valor = valor;
		this.empresa = empresa;
	}
	
	public Cuenta() {};
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	
	
	
	
	
}
