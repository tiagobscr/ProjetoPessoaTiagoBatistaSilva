package controle;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import dao.PessoaDao;
import dao.PessoaDaoImpl;
import entidade.Pessoa;
import util.JpaUtil;

/**
 * 
 * @author Tiago Batista
 *
 *	LoginBean, classe responsavel pela logica de logar no sistema
 *
 */

@ManagedBean(name = "LoginBean")
@RequestScoped
public class LoginBean {
    
	//Essas variaveis s„o responsaveis para o acesso geral, o admin
	private String usuarioTXT;
	private String senhaTXT;
	PessoaDao pessoaDao;

	private static final String PESQUISAR = "pesquisarPessoa.xhtml";

	public LoginBean() {

		this.pessoaDao = new PessoaDaoImpl(JpaUtil.getEntityManager());

	}
	
	/**
	 * MEtodo entrar, vai verificar se È o admin ou um usuario j· existente
	 * @throws IOException 
	 */

	public void entrar() throws IOException {

		if (this.usuarioTXT.equals("admin") && this.senhaTXT.equals("admin")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect(PESQUISAR);
		} else {
			Pessoa pessoaPesquisa = this.pessoaDao.pesquisar(this.usuarioTXT);
			if (pessoaPesquisa != null) {
				if (pessoaPesquisa.getSenha().equals(this.senhaTXT)) {
					FacesContext.getCurrentInstance().getExternalContext().redirect(PESQUISAR);
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
							" Usu√°rio ou senha incorretos " ));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
						" Usu√°rio n√£o  cadastrado  " ));
			}

		}
	}

	public String getUsuarioTXT() {
		return usuarioTXT;
	}

	public void setUsuarioTXT(String usuarioTXT) {
		this.usuarioTXT = usuarioTXT;
	}

	public String getSenhaTXT() {
		return senhaTXT;
	}

	public void setSenhaTXT(String senhaTXT) {
		this.senhaTXT = senhaTXT;
	}

	public PessoaDao getPessoaDao() {
		return pessoaDao;
	}

	public void setPessoaDao(PessoaDao pessoaDao) {
		this.pessoaDao = pessoaDao;
	}

}
