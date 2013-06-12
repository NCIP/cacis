/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.dao.impl;

import gov.nih.nci.cacis.common.dao.Dao;

import java.util.List;

/**
 * @param <T> Type parameter
 * @author kherm manav.kher@semanticbits.com
 */
public class DefaultDaoImpl<T> implements Dao<T> {

    /**
     * Retrieve all entities of the entity type served by this dao.
     *
     * @return list of the entities, empty list if none.
     */
    @Override
    public List<T> getAll() {
        return null;
    }

    /**
     * Save the given entity in the persistent store.
     *
     * @param entity entity to save. It must not be yet persistent
     * @return II id of the saved entity within repository (II.extention is table id in case of database, ordinal number
     *         in case of in-memory structure)
     */
    @Override
    public Long save(T entity) {
        return null;
    }

    /**
     * Retrieve an entity by a given internal id.
     *
     * @param id the internal identifier of the entity
     * @return the entity
     */
    @Override
    public T getById(Long id) {
        return null;
    }

    /**
     * Remove the entity from persistent store
     *
     * @param id - the external identifier of the entity
     */
    @Override
    public void delete(Long id) {
        //Implement me
    }

    /**
     * Replace the entity in the data store
     *
     * @param id     identifier of the entity
     * @param entity the new entity
     */
    @Override
    public void replace(Long id, T entity) {
        //Implement me
    }

    /**
     * Delete all entitites from the data store
     */
    @Override
    public void deleteAll() {
        //Implement me
    }
}
