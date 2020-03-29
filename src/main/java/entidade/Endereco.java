package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Tiago Batista
 * 
 * Classe endereco utilizada como referencia  pessoa, onde faz um para muitos.
 * Nesta classe tem uma coisa em particular, ela tem um ID, onde o mesmo usa a estratégia de
 * autoincremento do JPA.
 *
 */

@Entity
@Table(name = "Endereco")
public class Endereco {

	@Id
	@Column(name = "id_endereco")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "rua")
	private String rua;
	@Column(name = "numero")
	private int numero;
	@Column(name = "complemento")
	private String complemento;
	@ManyToOne
	@JoinColumn(name = "cpf_pessoa", referencedColumnName = "cpf", nullable = false)
	private Pessoa pessoa;

	public Endereco() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return rua + ",  " + numero + " " +complemento;
	}

}
