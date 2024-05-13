package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Jogo;
import util.JPAUtil;

public class JogoDAO {

    /**
     * Metodo para salvar um jogo no banco de dados
     */
    public static void save(Jogo jogo) {
        EntityManager em = JPAUtil.createEntityManager();
        em.getTransaction().begin();
        em.persist(jogo);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Metodo para atualizar um jogo no banco de dados
     * 
     * @param jogo
     * @return
     */
    public static Jogo update(Jogo jogo) {
        EntityManager em = JPAUtil.createEntityManager();
        Jogo jogoAtualizado = jogo;
        em.getTransaction().begin();
        jogoAtualizado = em.merge(jogo);
        em.getTransaction().commit();
        em.close();
        return jogoAtualizado;
    }

    /**
     * Metodo para deletar um jogo no banco de dados
     *
     * @param jogo
     */
    public static void delete(Jogo jogo) {
        EntityManager em = JPAUtil.createEntityManager();
        em.getTransaction().begin();
        jogo = em.find(Jogo.class, jogo.getId());
        em.remove(jogo);
        em.getTransaction().commit();
        em.close();

    }

    /**
     * Metodo para buscar um jogo no banco de dados
     *
     * @param id
     * @return
     */
    public static Jogo getById(Integer id) {
        EntityManager em = JPAUtil.createEntityManager();
        Jogo jogo = em.find(Jogo.class, id);
        em.close();
        return jogo;
    }

    /**
     * Metodo para buscar todos os jogos no banco de dados
     *
     * @return
     */
    public static List<Jogo> getAll() {
        EntityManager em = JPAUtil.createEntityManager();
        Query consultaBuscaTodos = em.createQuery("SELECT a FROM Jogo a");
        @SuppressWarnings("unchecked")
        List<Jogo> resultadoDaBuscaDeTodos = consultaBuscaTodos.getResultList();
        return resultadoDaBuscaDeTodos;
    }
    
    
    public static Integer getMax() {
    	 EntityManager em = JPAUtil.createEntityManager();
         Query consultaBuscaTodos = em.createNamedQuery("maxNumeroSorteado");
         Integer resultadoMax = (Integer) consultaBuscaTodos.getSingleResult();
         return resultadoMax;
    }

}
