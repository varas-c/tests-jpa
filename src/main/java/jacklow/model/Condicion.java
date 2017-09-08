package jacklow.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Condicion {

	@Id
	@GeneratedValue
	private Long id;
	
	String name = "Una Condicion";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Indicador_id", nullable=false)
	Indicador indicador;
	
	public Condicion(Indicador indicador) {
		this.indicador = indicador;
	}
	
	public Condicion() {
		
	}
	
}
