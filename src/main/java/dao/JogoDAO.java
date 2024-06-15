package dao;

import java.util.List;

import javax.persistence.Query;

import entidades.Jogo;

public class JogoDAO {

	static GenericDAO<Jogo> genDAO = new GenericDAO<Jogo>(Jogo.class);

	/**
	 * Metodo para salvar um jogo no banco de dados
	 */
	public static void save(Jogo jogo) {
		genDAO.save(jogo);
	}

	/**
	 * Metodo para atualizar um jogo no banco de dados
	 * 
	 * @param jogo
	 * @return
	 */
	public static Jogo update(Jogo jogo) {
		return genDAO.update(jogo);
	}

	/**
	 * Metodo para deletar um jogo no banco de dados
	 *
	 * @param jogo
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void delete(Jogo jogo) {
		try {
			genDAO.delete(jogo);
		} catch (Exception e) {
			System.err.println("Erro ao deletar JOGO:" + e);
		}
	}

	/**
	 * Metodo para buscar um jogo no banco de dados
	 *
	 * @param id
	 * @return
	 */
	public static Jogo getById(Integer id) {
		return genDAO.getById(id);
	}

	/**
	 * Metodo para buscar todos os jogos no banco de dados
	 *
	 * @return
	 */
	public static List<Jogo> getAll() {
		return genDAO.getAll();
	}

	public static Integer getMax() {
		return genDAO.customRule((em)->{
			Query consultaBuscaTodos = em.createNamedQuery("maxNumeroSorteado");
			Integer resultadoMax = (Integer) consultaBuscaTodos.getSingleResult();
			return resultadoMax;
		});
	}

}
