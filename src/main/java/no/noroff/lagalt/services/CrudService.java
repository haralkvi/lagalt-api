package no.noroff.lagalt.services;

import java.util.Collection;

    public interface CrudService <T,ID> {

        T findById(ID id);

        Collection<T> findAll();

        T add (T entity);

        T update (T entity);

        void deleteById(ID id);

        void delete(T entity);

        boolean existsById(ID id);
    }

