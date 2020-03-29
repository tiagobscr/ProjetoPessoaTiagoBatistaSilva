package dao;

import java.util.List;

import entidade.Pessoa;

/**
 * 
 * @author Tiago Batista
 *
 *  PessoaDao � uma interface, onde compartilha a chamada dos metodos, mas n�o os implementam.
 */

public interface PessoaDao {
	
    public boolean inserir(Pessoa pessoa);
	
	public void alterar(Pessoa pessoa);
	
	public void remover(Pessoa pessoa);
	
	public Pessoa pesquisar(String cpf);
	
	public List<Pessoa> listarTodos();
	
	public List<Pessoa> listarNome(String nome);

}
