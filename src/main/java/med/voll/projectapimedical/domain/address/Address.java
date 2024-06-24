package med.voll.projectapimedical.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String logradouro;
    private String neighborhood;
    private String cep;
    private String number;
    private String complement;
    private String city;
    private String uf;

    public Address(AddressData addressData) {
    	this.logradouro = addressData.logradouro();
    	this.neighborhood = addressData.neighborhood();
    	this.cep = addressData.cep();
    	this.number = addressData.number();
    	this.complement = addressData.complement();
    	this.city = addressData.city();
    	this.uf = addressData.uf();
    }

	public void updateInfo(AddressData data) {
		
		if(data.logradouro()!=null) {
			this.logradouro = data.logradouro();
		}
		if(data.neighborhood()!=null) {
			this.neighborhood = data.neighborhood();
		}
		if(data.cep()!=null) {
			this.cep = data.cep();
		}
		if(data.number()!=null) {
			this.number = data.number();
		}
		if(data.complement()!=null) {
			this.complement = data.complement();
		}
		if(data.city()!=null) {
			this.city = data.city();
		}
		if(data.uf()!=null) {
			this.uf = data.uf();
		}
	}
}
