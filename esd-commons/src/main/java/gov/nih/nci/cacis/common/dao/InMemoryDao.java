/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.dao;

import gov.nih.nci.cacis.common.dao.impl.DefaultDaoImpl;
import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In memory persistence
 *
 * @param <T> Type parameter
 * @author kherm manav.kher@semanticbits.com
 */
public class InMemoryDao<T> extends DefaultDaoImpl<T> implements Dao<T> {

    private static final Log LOG = LogFactory.getLog(InMemoryDao.class);


    private final Map<Long, T> entityMap = Collections.synchronizedMap(new LinkedHashMap<Long, T>());

    private long sequence = 0;


    /**
     * {@inheritDoc}
     */
    @Override
    public T getById(final Long id) {
        return entityMap.get(id);
    }


    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return new ArrayList<T>(entityMap.values());
    }


    /**
     * {@inheritDoc}
     */
    public Long save(final T entity) {
        if (entity == null) {
            return null;
        }

        final Long id = nextSequence();
        entityMap.put(id, entity);
        LOG.debug("Saved entity with id" + id);
        return id;
    }

    private synchronized long nextSequence() {
        return ++sequence;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void replace(Long id, final T entity) {
        if (id == null) {
            return;
        }

        if (entityMap.get(id) == null) {
            throw new ApplicationRuntimeException(this.getClass().getSimpleName() + " entity not found for id"
                    + id);
        }
        entityMap.put(id, entity);
        LOG.debug("Replaced entity with id" + id);
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Long id) {
        entityMap.remove(id);
        LOG.debug("Deleted entity with id" + id);
    }


    /**
     * Method for unit testing purposes. Not part of DAO contract.
     */
    public void deleteAll() {
        entityMap.clear();
    }


}
