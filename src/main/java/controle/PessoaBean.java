package controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import dao.PessoaDao;
import dao.PessoaDaoImpl;
import entidade.Endereco;
import entidade.Pessoa;
import util.BuscaCEP;
import util.JpaUtil;

/**
 * 
 * @author Tiago Batista
 *
 *         Classe responsavel por controlar as telas de consultar e manter
 *
 */

@ManagedBean(name = "PessoaBean")
@ViewScoped
public class PessoaBean {

	private Pessoa pessoa;
	private Endereco endereco;
	private List<Pessoa> pessoas;
	private PessoaDao pessoaDao;
	private String pessoaSelecionada;
	private String confirmaSenha;
	private String nomePesquisa;
	private String cpfPesquisa;
	private BuscaCEP buscaCep;
	private String CEP;

	private static final String PESQUISAR = "pesquisarPessoa.xhtml";

	private static final String Manter = "manterPessoa.xhtml";

	private static final String Login = "index.xhtml";

	public PessoaBean() {
		this.pessoa = new Pessoa();
		this.endereco = new Endereco();
		this.pessoas = new ArrayList<Pessoa>();
		this.pessoaDao = new PessoaDaoImpl(JpaUtil.getEntityManager());

		this.pessoas = this.pessoaDao.listarTodos();
		this.pessoa = new Pessoa();
		this.pessoa.setEnderecos(new ArrayList<Endereco>());

	}

	public void pesquisar() {
		this.pessoas = this.pessoaDao.listarTodos();
		System.out.println("Teste");

	}
	
	public void pesquisarNome() {
		System.out.println(this.nomePesquisa);
		this.pessoas = this.pessoaDao.listarNome(this.nomePesquisa);
	}
	
	public void pesquisarCPF() {
		if(this.pessoaDao.pesquisar(this.cpfPesquisa)!=null) {
			Pessoa pessoaAtual = this.pessoaDao.pesquisar(this.cpfPesquisa);
			this.pessoas.clear();
			this.pessoas.add(pessoaAtual);
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					" CPF inválido " ));
		}
	}

	public void cadastrar() {
		System.out.println(pessoa);
		pessoaDao.inserir(pessoa);

		System.out.println("Cadastrado");
	}

	public void editar() {
		Pessoa pessoaAtual = this.pessoaDao.pesquisar(this.pessoa.getCpf());
		this.pessoa = pessoaAtual;
		pessoaDao.alterar(pessoaAtual);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "", pessoaAtual + " alterado com sucesso."));

		System.out.println("alterado");
	}

	public void remover() {
		System.out.println(pessoa);
		Pessoa pessoaAtual = this.pessoaDao.pesquisar(this.pessoa.getCpf());
		this.pessoa = pessoaAtual;

		pessoaDao.remover(pessoaAtual);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "", pessoaAtual + " Excluido com sucesso."));
		System.out.println("removido");
		pesquisar();
	}

	public void adicionarEndereco() {
		Endereco enderecoNovo = new Endereco();
		enderecoNovo.setRua(this.endereco.getRua());
		enderecoNovo.setNumero(this.endereco.getNumero());
		enderecoNovo.setComplemento(this.endereco.getComplemento());
		enderecoNovo.setPessoa(this.pessoa);

		if (!this.existeEndereco(enderecoNovo)) {
			this.pessoa.getEnderecos().add(enderecoNovo);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					" Endereço já cadastrado para " + this.pessoa.getNome()));
			
		}

		this.endereco = new Endereco();

	}

	private boolean existeEndereco(Endereco endereco) {
		boolean resultado = false;

		for (Endereco enderecoAtual : this.pessoa.getEnderecos()) {
			if (enderecoAtual.getRua().equals(endereco.getRua()) && enderecoAtual.getNumero() == endereco.getNumero()) {
				resultado = true;
			}
		}

		return resultado;
	}

	public void removerEndereco() {

		int i = 0;
		while (i < this.pessoa.getEnderecos().size()) {

			if (this.pessoa.getEnderecos().get(i).getId() == this.endereco.getId()) {
				this.pessoa.getEnderecos().remove(i);
			}
			i = i + 1;
		}

		System.out.println(this.pessoa.getEnderecos());

	}

	public void salvar() throws IOException {
		Pessoa pessoaAtual = this.pessoaDao.pesquisar(this.pessoa.getCpf());

		if (pessoaAtual != null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.pessoa.getCpf() + " já cadastrado"));
		} else {
			if (pessoaDao.inserir(this.pessoa)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "", this.pessoa + " salvo com sucesso."));
				System.out.println("=======salvou ======");
				this.pessoa = new Pessoa();
				this.endereco = new Endereco();
				abrirPesquisarPessoa();

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao inserir."));
			}
		}

	}

	public void abrirPesquisarPessoa() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(PESQUISAR);

	}

	public void abrirManterPessoa() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext().redirect(Manter);
	}

	public void abrirLogin() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(Login);

	}
	
	public void cepBusca() {
		String array[] = new String[30];
		
		this.buscaCep=new BuscaCEP();
		
		if (this.CEP.length()<9) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Digite um CEP válido."));
			
		}else {
		System.out.println(this.CEP);	
		System.out.println(this.CEP.length());
		
		if(this.CEP.length()==9)array = this.buscaCep.buscaCEP(this.CEP);
		
		if(array[1].equals("erro")) {
			System.out.println("Digite1 um CEP Valido");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Digite um CEP válido"));
		} else {
		if(array!=null)this.endereco.setRua(array[7]);
		
		if(array!=null)this.endereco.setComplemento(array[15]+" "+array[19]+" "+array[23]+" CEP: "+this.CEP);
		System.out.println(this.endereco);
		}
		}
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public PessoaDao getPessoaDao() {
		return pessoaDao;
	}

	public void setPessoaDao(PessoaDao pessoaDao) {
		this.pessoaDao = pessoaDao;
	}

	public String getPessoaSelecionada() {
		return pessoaSelecionada;
	}

	public void setPessoaSelecionada(String pessoaSelecionada) {
		this.pessoaSelecionada = pessoaSelecionada;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getNomePesquisa() {
		return nomePesquisa;
	}

	public void setNomePesquisa(String nomePesquisa) {
		this.nomePesquisa = nomePesquisa;
	}

	public String getCpfPesquisa() {
		return cpfPesquisa;
	}

	public void setCpfPesquisa(String cpfPesquisa) {
		this.cpfPesquisa = cpfPesquisa;
	}

	public BuscaCEP getBuscaCep() {
		return buscaCep;
	}

	public void setBuscaCep(BuscaCEP buscaCep) {
		this.buscaCep = buscaCep;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}
	
	

}
