package jacklow.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("N")

public class NoTaxativa extends Condicion{
	String descripcion = "No Taxativa";
	String otroCampoAlPedo = "Al pedo :D";
	
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
