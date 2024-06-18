package bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import dao.GenericDAO;
import entidades.Campeonato;

@ManagedBean
public class CampeonatoBean {

    private GenericDAO<Campeonato> CampeonatoDAO = new GenericDAO<>(Campeonato.class);
    private Campeonato campeonatoSelecionado;

    private Date dataAtual;

    private Campeonato campeonato = new Campeonato();

    private List<Campeonato> campeonatos;

    /**
     * Metodo usado para buscar um campeonato pelo ID de um Campeonato
     * 
     * @param campeonato
     * @return Campeonato
     */
    public Campeonato getById(int id) {
        for (Campeonato campeonato : campeonatos) {
            if (campeonato.getId() == id) {
                return campeonato;
            }
        }
        return null;
    }

    /**
     * Metodo usado para buscar um campeonato pelo ID tendo um objeto "Campeonato"
     * como
     * parametro
     * 
     * @param campeonato
     * @return Campeonato
     */
    public Campeonato get(Campeonato campeonato) {
        for (Campeonato j : campeonatos) {
            if (j.getId() == campeonato.getId()) {
                return j;
            }
        }
        return null;
    }

    @PostConstruct
    public void initValues() {
        campeonato = new Campeonato();
        dataAtual = new Date();
        campeonatos = CampeonatoDAO.getAll();

    }

    /**
     * Metodo usado para iniciar a edição de um item. Nota: Ele não possui nenhum
     * código pois só é necessário para informar ao primefaces que é necessario
     * atualizar a variavel "campeonatoSelecionado"
     *
     * @param campeonato
     * @return Void
     */
    public void initEdit(Campeonato campeonato) {
        if (campeonato != null)
            campeonatoSelecionado = campeonato;
    }

    /**
     * Metodo usado para salvar um campeonato no banco de dados
     *
     * @param campeonato
     * @return Void
     */
    public void save() {

        try {
            if (campeonato.getNome().isEmpty()) {
                throw new Exception("O nome do campeonato é obrigatório!");
            }

            // Salva novo campeonato
            CampeonatoDAO.save(campeonato);
            // Adiciona o campeonato na lista de campeonatos
            campeonatos.add(campeonato);

            addMessage("Sucesso", "Salvo com sucesso! Id:" + campeonato.getId());

            // Instancia novo campeonato sem valores preenchidos(todos estão como "null")
            campeonato = new Campeonato();

        } catch (Exception erroAoSalvar) {
            erroAoSalvar.printStackTrace();
            String mensagemDeErro = erroAoSalvar.getLocalizedMessage();
            addMessage("Erro ao salvar", mensagemDeErro, true);
        }

    }

    /**
     * Metodo usado para editar um campeonato cadastrado no banco de dados
     *
     * @param campeonato
     * @return Void
     */
    public void edit(Integer id) {
        if (id != null)
            campeonatoSelecionado = getById(id);

        try {
            if (campeonatoSelecionado.getNome().isEmpty()) {
                throw new Exception("O nome do campeonato é obrigatório!");
            }
            // Atualiza o campeonato no banco de dados e adiciona a lista de campeonatos
            Campeonato novoCampeonato = CampeonatoDAO.update(campeonatoSelecionado);

            addMessage("Sucesso", "Editado com sucesso!" + novoCampeonato.getId());

            // Instancia novo campeonato sem valores preenchidos(todos estão como "null")
            campeonato = new Campeonato();

            campeonatos.add(novoCampeonato);

            campeonatos.remove(campeonatoSelecionado);
        } catch (Exception erroAoEditar) {
            erroAoEditar.printStackTrace();
            String mensagemDeErro = erroAoEditar.getLocalizedMessage();
            addMessage("Erro ao editar", mensagemDeErro);
        }

    }

    /**
     * Metodo usado para deletar um campeonato cadastrado no banco de dados
     *
     * @param campeonato
     * @return Void
     */
    public void delete(Integer id) {
        if (id != null)
            campeonatoSelecionado = getById(id);

        try {
            // Deleta o campeonato do banco de dados
            CampeonatoDAO.delete(campeonatoSelecionado);

            // Remove o campeonato da lista de campeonatos
            campeonatos.remove(campeonatoSelecionado);
            campeonatoSelecionado = null;

            addMessage("Deletado", "Deletado com sucesso!");
        } catch (Exception erroAoDeletar) {
            erroAoDeletar.printStackTrace();
            String mensagemDeErro = erroAoDeletar.getLocalizedMessage();
            addMessage("Erro ao deletar", mensagemDeErro, true);
        }

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

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public List<Campeonato> getCampeonatos() {
        // Ordena os campeonatos pelo ID
        campeonatos.sort((campeonato1, campeonato2) -> campeonato1.getId() - campeonato2.getId());

        return campeonatos;
    }

    public Campeonato getCampeonatoSelecionado() {
        return campeonatoSelecionado;
    }

    public void setCampeonatoSelecionado(Campeonato campeonatoSelecionado) {
        this.campeonatoSelecionado = campeonatoSelecionado;
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addMessage(String summary, String detail, boolean error) {
        FacesMessage message = new FacesMessage(error ? FacesMessage.SEVERITY_FATAL : FacesMessage.SEVERITY_INFO,
                summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
