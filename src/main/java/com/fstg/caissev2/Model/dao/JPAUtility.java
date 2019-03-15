package com.fstg.caissev2.Model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract class JPAUtility<T> {


    private static final String PERSISTENCE_UNIT_NAME = "com.sir_caisse_jar_1.0-SNAPSHOTPU";

    private EntityManager em;

    protected EntityManager getEntityManager() {
        if (em == null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            em = entityManagerFactory.createEntityManager();
        }
        return em;
    }

    protected void save(T entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();
    }

    protected void update(T entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();
    }

    protected void delete(T entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(entity);
        getEntityManager().getTransaction().commit();
    }

    public List<T> getMultipleResult(String query) {
        return getEntityManager().createQuery(query).getResultList();
    }


    public T getSingleResult(String query) {
        List<T> resultat = getMultipleResult(query);
        if (resultat == null || resultat.isEmpty() || resultat.get(0) == null)
            return null;
        return resultat.get(0);
    }

}
