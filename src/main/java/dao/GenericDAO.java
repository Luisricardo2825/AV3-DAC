package dao;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Id;
import javax.persistence.Entity;

import util.JPAUtil;

public class GenericDAO<T> {
    private Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Retorna o valor da PK se existente(Busca pela Annotation "@Id")
     * 
     * @param entity
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws SecurityException
     */
    private Object getIdField(T entity)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        for (Field declaredField : entity.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (isPk(declaredField)) {
                Object value = declaredField.get(entity);
                return value;
            }
        }
        throw new NoSuchFieldException("PK Não encontrada");
    }

    private boolean isPk(Field field) {
        Id annotation = field.getAnnotation(Id.class);
        return annotation != null;
    }

    /**
     * Metodo para salvar um registros no banco de dados
     */
    public void save(T registro) {
    	EntityManager em = JPAUtil.createEntityManager();
        em.getTransaction().begin();
        em.persist(registro);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Metodo para atualizar um registro no banco de dados
     * 
     * @param registro
     * @return
     */
    public T update(T registro) {
    	EntityManager em = JPAUtil.createEntityManager();
        T registroAtualizado = registro;
        em.getTransaction().begin();
        registroAtualizado = em.merge(registro);
        em.getTransaction().commit();
        em.close();
        return registroAtualizado;
    }

    /**
     * Metodo para deletar um registro no banco de dados
     *
     * @param registro
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public void delete(T registro)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
    	EntityManager em = JPAUtil.createEntityManager();
        em.getTransaction().begin();

        // Retorna o id
        Object idField = this.getIdField(registro);

        registro = (T) em.find(clazz, idField);
        em.remove(registro);
        em.getTransaction().commit();
        em.close();

    }

    /**
     * Metodo para buscar um registro no banco de dados
     *
     * @param id
     * @return
     */
    public T getById(Integer id) {
    	EntityManager em = JPAUtil.createEntityManager();
        T registro = em.find(clazz, id);
        em.close();
        return registro;
    }

    /**
     * Metodo para buscar todos os registros no banco de dados
     *
     * @return
     */
    public List<T> getAll() {
    	EntityManager em = JPAUtil.createEntityManager();
    	String query = String.format("SELECT a FROM %s a", this.getEntityName());
        Query consultaBuscaTodos = em.createQuery(query);
        @SuppressWarnings("unchecked")
        List<T> resultadoDaBuscaDeTodos = consultaBuscaTodos.getResultList();
        return resultadoDaBuscaDeTodos;
    }

    /*
     * Retorna o nome da entidade
     * 
     * @return String - nome da entidade
     */
    private String getEntityName() {
        String entityName = clazz.getSimpleName();
        Entity entityAnnotation = clazz.getDeclaredAnnotation(Entity.class);
        if (entityAnnotation != null) {
            String entityAnnotationName = entityAnnotation.name();
            if (!entityAnnotationName.isEmpty()) {
                entityName = entityAnnotationName;
            }
        }
        return entityName;
    }

    public <R> R customRule(Function<EntityManager, R> function) {
    	EntityManager em = JPAUtil.createEntityManager();
        R apply = function.apply(em);
        if (em.isOpen())
            em.close();
        return apply;
    }

}
