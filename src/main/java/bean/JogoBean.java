package bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;

import dao.GenericDAO;
import dao.JogoDAO;
import entidades.Campeonato;
import entidades.Jogo;

@ManagedBean
@ViewScoped
public class JogoBean {

	private Jogo jogoSelecionado;

	private Date dataAtual;

	private Jogo jogo = new Jogo();

	private List<Jogo> jogos;

	private List<SelectItem> times = new ArrayList<SelectItem>();
	private List<SelectItem> campeonatos = null;

	public List<SelectItem> getCampeonatos() {
		return campeonatos;
	}

	public void setCampeonatos(List<SelectItem> campeonatos) {
		this.campeonatos = campeonatos;
	}

	public List<SelectItem> getTimes() {
		return times;
	}

	public void setTimes(List<SelectItem> times) {
		this.times = times;
	}

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
		times = Jogo.Times.A.getTimes().stream().map(time -> new SelectItem(time, time)).toList();
		List<SelectItem> camps = new GenericDAO<Campeonato>(Campeonato.class).getAll().stream()
				.map(campeonato -> new SelectItem(campeonato.getId(), campeonato.getNome())).toList();
		campeonatos = camps.size() > 0 ? camps : null;

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
			if (jogo.getTime1().equals(jogo.getTime2())) {
				throw new Exception("Os times não podem ser iguais!");
			}
			if (jogo.getIdCampeonato() == null) {
				throw new Exception("Selecione um campeonato!");
			}
			Campeonato camp = new GenericDAO<Campeonato>(Campeonato.class).getById(jogo.getIdCampeonato());
			jogo.setCampeonato(camp);
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
			addMessage("Erro ao salvar", mensagemDeErro, true);
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
			if (jogoSelecionado.getTime1().equals(jogoSelecionado.getTime2())) {
				throw new Exception("Os times não podem ser iguais!");
			}

			if (jogoSelecionado.getCampeonato() == null) {
				throw new Exception("Selecione um campeonato!");
			}
			// Atualiza o jogo no banco de dados e adiciona a lista de jogos
			Jogo novoJogo = JogoDAO.update(jogoSelecionado);

			addMessage("Sucesso", "Editado com sucesso!" + novoJogo.getId());

			// Instancia novo jogo sem valores preenchidos(todos estão como "null")
			jogo = new Jogo();

			jogos.add(novoJogo);

			jogos.remove(jogoSelecionado);
		} catch (Exception erroAoEditar) {
			erroAoEditar.printStackTrace();
			String mensagemDeErro = erroAoEditar.getLocalizedMessage();
			addMessage("Erro ao editar", mensagemDeErro, true);
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
	// PrimeFaces.current().executeScript("explodeConfeti()");

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

	public void addMessage(String summary, String detail, boolean error) {
		FacesMessage message = new FacesMessage(error ? FacesMessage.SEVERITY_FATAL : FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
