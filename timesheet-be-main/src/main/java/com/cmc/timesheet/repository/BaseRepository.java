package com.cmc.timesheet.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Component
public class BaseRepository {
    /**
     * The Entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;
    /**
     * The Application context.
     */
    private ApplicationContext applicationContext;
    /**
     * Gets entity manager.
     *
     * @return the entity manager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
    /**
     * The Model mapper.
     */
    private ModelMapper modelMapper;
    /**
     * Init query params.
     *
     * @param query  the query
     * @param params the params
     */
    public void initQueryParams(final Query query,
                                final Map<String, Object> params) {
        if (!ObjectUtils.isEmpty(params)) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }
    /**
     * Init paging.
     *
     * @param query    the query
     * @param pageNo   the page no
     * @param pageSize the page size
     */
    public void initPaging(final Query query, int pageNo, int pageSize) {
        query.setFirstResult((pageNo) * pageSize).setMaxResults(pageSize);
    }
    /**
     * Create query typed query.
     *
     * @param <T>    the type parameter
     * @param sql    the sql
     * @param params the params
     * @param clazz  the clazz
     * @return the typed query
     */
    public <T> TypedQuery<T> createQuery(final String sql,
                                         final Map<String, Object> params,
                                         final Class<T> clazz) {
        TypedQuery<T> query =
                this.getEntityManager().createQuery(sql, clazz);
        this.initQueryParams(query, params);
        return query;
    }
    /**
     * Gets model mapper.
     *
     * @return the model mapper
     */
    public ModelMapper getModelMapper() {
        return modelMapper;
    }
    /**
     * Sets model mapper.
     *
     * @param pModelMapper the p model mapper
     */
    @Autowired
    public void setModelMapper(final ModelMapper pModelMapper) {
        modelMapper = pModelMapper;
    }
    /**
     * Gets application context.
     *
     * @return the application context
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    /**
     * Sets application context.
     *
     * @param pApplicationContext the p application context
     */
    @Autowired
    public void setApplicationContext(
            final ApplicationContext pApplicationContext) {
        applicationContext = pApplicationContext;
    }
}
