package entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Tiago Batista
 *	
 * Pessoa é a classe principal desse projeto onde tem a tela de pesquisa e a tela de manter
 * Usuario tem uma lista de telefones e tem como chave primaria o CPF, levando em consideração
 * que muitos sistemas tem como login o CPF.
 *
 */

@Entity
@Table(name = "PESSOA")
public class Pessoa {

	@Id
	@Column(name = "cpf")
	private String cpf;
	@Column(name = "nome",nullable = false)
	private String nome;
	@Column(name = "idade")
	private int idade;
	@Column(name="senha",nullable = false)
	private String senha;
	@Column(name = "sexo")
	private String sexo;
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Endereco> enderecos;

	public Pessoa() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public String toString() {
		return nome;
	}

}
