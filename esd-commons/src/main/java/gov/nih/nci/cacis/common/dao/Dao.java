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

import java.util.List;

/**
 * @param <T> Type parameter
 */
public interface Dao<T> {

    /**
     * Retrieve all entities of the entity type served by this dao.
     *
     * @return list of the entities, empty list if none.
     */
    List<T> getAll();

    /**
     * Save the given entity in the persistent store.
     *
     * @param entity entity to save. It must not be yet persistent
     * @return II id of the saved entity within repository (II.extention is table id in case of database, ordinal number
     *         in case of in-memory structure)
     */
    Long save(T entity);

    /**
     * Retrieve an entity by a given internal id.
     *
     * @param id the internal identifier of the entity
     * @return the entity
     */
    T getById(Long id);

    /**
     * Remove the entity from persistent store
     *
     * @param id - the external identifier of the entity
     */
    void delete(Long id);

    /**
     * Replace the entity in the data store
     *
     * @param id     identifier of the entity
     * @param entity the new entity
     */
    void replace(Long id, T entity);

    /**
     * Delete all entitites from the data store
     */
    void deleteAll();
}

