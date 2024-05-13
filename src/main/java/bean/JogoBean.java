package bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import dao.JogoDAO;
import entidades.Jogo;

@ManagedBean
@ViewScoped
public class JogoBean {

	private Jogo jogoSelecionado;

	private Date dataAtual;

	private Jogo jogo = new Jogo();

	private List<Jogo> jogos;

	/**
	 * Metodo usado para buscar um jogo pelo ID de um Jogo
	 * 
	 * @param jogo
	 * @return Jogo
	 */
	public Jogo getById(int id) {
		for (Jogo jogo : jogos) {
			if (jogo.getId() == id) {
				return jogo;
			}
		}
		return null;
	}

	/**
	 * Metodo usado para buscar um jogo pelo ID tendo um objeto "Jogo" como
	 * parametro
	 * 
	 * @param jogo
	 * @return Jogo
	 */
	public Jogo get(Jogo jogo) {
		for (Jogo j : jogos) {
			if (j.getId() == jogo.getId()) {
				return j;
			}
		}
		return null;
	}

	@PostConstruct
	public void initValues() {
		jogo = new Jogo();
		dataAtual = new Date();
		jogos = JogoDAO.getAll();

	}

	/**
	 * Metodo usado para iniciar a edição de um item. Nota: Ele não possui nenhum
	 * código pois só é necessário para informar ao primefaces que é necessario
	 * atualizar a variavel "jogoSelecionado"
	 *
	 * @param jogo
	 * @return Void
	 */
	public void initEdit(Jogo jogo) {
		if (jogo != null)
			jogoSelecionado = jogo;
	}

	/**
	 * Metodo usado para salvar um jogo no banco de dados
	 *
	 * @param jogo
	 * @return Void
	 */
	public void save() {

		try {

			if (!jogo.checkValues())
				throw new Exception("Os valores precisam estar entre 1 a 10, verifique os dados e tente novamente");
			// Salva novo jogo
			JogoDAO.save(jogo);
			// Adiciona o jogo na lista de jogos
			jogos.add(jogo);

			addMessage("Sucesso", "Salvo com sucesso! Id:" + jogo.getId());

			// Instancia novo jogo sem valores preenchidos(todos estão como "null")
			jogo = new Jogo();

		} catch (Exception erroAoSalvar) {
			erroAoSalvar.printStackTrace();
			String mensagemDeErro = erroAoSalvar.getLocalizedMessage();
			addMessage("Erro ao salvar", mensagemDeErro);
		}

	}

	/**
	 * Metodo usado para editar um jogo cadastrado no banco de dados
	 *
	 * @param jogo
	 * @return Void
	 */
	public void edit(Integer id) {
		if (id != null)
			jogoSelecionado = getById(id);

		try {
			if (!jogoSelecionado.checkValues()) {
				jogos = JogoDAO.getAll();
				throw new Exception("Os valores precisam estar entre 1 a 10, verifique os dados e tente novamente");
			}

			// Salva o jogo no banco de dados e adiciona a lista de jogos
			Jogo novoJogo = JogoDAO.update(jogoSelecionado);

			addMessage("Sucesso", "Editado com sucesso!" + novoJogo.getValues());

			// Instancia novo jogo sem valores preenchidos(todos estão como "null")
			jogo = new Jogo();

			jogos.add(novoJogo);

			jogos.remove(jogoSelecionado);
		} catch (Exception erroAoEditar) {
			erroAoEditar.printStackTrace();
			String mensagemDeErro = erroAoEditar.getLocalizedMessage();
			addMessage("Erro ao editar", mensagemDeErro);
		}

	}

	/**
	 * Metodo usado para deletar um jogo cadastrado no banco de dados
	 *
	 * @param jogo
	 * @return Void
	 */
	public void delete(Integer id) {
		if (id != null)
			jogoSelecionado = getById(id);

		// Deleta o jogo do banco de dados
		JogoDAO.delete(jogoSelecionado);

		// Remove o jogo da lista de jogos
		jogos.remove(jogoSelecionado);
		jogoSelecionado = null;

		addMessage("Deletado", "Deletado com sucesso!");

	}

	/**
	 * Metodo usado para contar a quantidae de jogos cadastrados no banco de dados
	 * 
	 * @return Void
	 */
	public void sorteado(Integer id) {
		if (id != null)
			jogoSelecionado = getById(id);

		// Retorna a quantidade de jogos cadastrados no banco de dados
		int numeroSorteado = jogoSelecionado.getNumeroSorteado();

		if (jogoSelecionado.getSorteadoIsInValues()) {

			addMessage("Parabens!", "Você acertou o numero sorteado: " + numeroSorteado);
			PrimeFaces.current().executeScript("explodeConfeti()");
		} else {
			addMessage("Informativo", "Você não acertou o numero sorteado :( ");
		}

	}

	public void maiorEntreOsValores(Integer id) {
		if (id != null)
			jogoSelecionado = getById(id);

		Integer greater = jogoSelecionado.getGreaterBetweenValues();

		addMessage("Informativo", "O maior numero escolhido foi: " + greater);

	}

	public void maiorSorteadoDaTabela() {

		Integer greater = JogoDAO.getMax();

		addMessage("Informativo", "O maior numero sorteado da tabela foi: " + greater);

	}

	// ---- GETTERS E SETTERS -----

	public void onDateTimeSelect(SelectEvent<LocalDateTime> event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", event.getObject().format(formatter)));
	}

	public Date getDataAtual() {
		return dataAtual;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public List<Jogo> getJogos() {
		// Ordena os jogos pelo ID
		jogos.sort((jogo1, jogo2) -> jogo1.getId() - jogo2.getId());

		return jogos;
	}

	public Jogo getJogoSelecionado() {
		return jogoSelecionado;
	}

	public void setJogoSelecionado(Jogo jogoSelecionado) {
		this.jogoSelecionado = jogoSelecionado;
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
