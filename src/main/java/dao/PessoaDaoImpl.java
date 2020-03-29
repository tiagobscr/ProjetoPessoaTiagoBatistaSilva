package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidade.Pessoa;

/**
 * 
 * @author Tiago Batista
 *
 *	Essa classe implementa a interface, todos os metodos mostrados na interface.
 *  Lembrando de uma coisa, a implementação ela recebe no construtor o entityManager,
 *  a conexão com o banco de dados, deixando assim essa classe totalemnte independente 
 *
 */

public class PessoaDaoImpl implements PessoaDao {
	
	private EntityManager ent;
	
	public PessoaDaoImpl(EntityManager ent) {
		this.ent = ent;
	}

	public boolean inserir(Pessoa pessoa) {
		// TODO Auto-generated method stub
		
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.persist(pessoa);
		tx.commit();
		return true;
	}

	public void alterar(Pessoa pessoa) {
		// TODO Auto-generated method stub
		
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.merge(pessoa);
		tx.commit();
	}

	public void remover(Pessoa pessoa) {
		// TODO Auto-generated method stub
		EntityTransaction tx = ent.getTransaction();
		tx.begin();

		ent.remove(pessoa);
		tx.commit();
		
	}

	public Pessoa pesquisar(String cpf) {
		// TODO Auto-generated method stub
		
        Pessoa pessoa = ent.find(Pessoa.class, cpf);
		
		return pessoa;
		
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> listarTodos() {
		// TODO Auto-generated method stub
		
        Query query = ent.createQuery("from Pessoa p order by nome asc");
		
		List<Pessoa> pessoas = query.getResultList();
		
		return pessoas;
		
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> listarNome(String nome) {
		// TODO Auto-generated method stub
		
		System.out.println(nome);
       Query query = ent.createQuery("From  Pessoa Where nome LIKE '%"+nome+"%' ORDER BY nome asc");
		
		List<Pessoa> pessoas = query.getResultList();
		
		return pessoas;
		
	}

}
