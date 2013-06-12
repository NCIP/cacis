/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.common.dao;

import gov.nih.nci.cacis.common.exception.ApplicationRuntimeException;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

/**
 * @author kherm manav.kher@semanticbits.com
 */
public class InMemoryDaoTest {
    private final String STRING1 = "some string";
    private final String STRING2 = "some other string";

    private InMemoryDao<String> dao;

    @Before
    public void before() {
        dao = new InMemoryDao<String>();
    }

    @Test
    public void saveStrings() {
        final Long id = dao.save(STRING1);
        assertNotNull(id);

        final Long id2 = dao.save(STRING2);
        assertNotSame(id, id2);

        assertEquals(2, dao.getAll().size());

        dao.replace(id, dao.getById(id2));
        assertEquals(dao.getById(id), dao.getById(id2));
    }

    @Test
    public void saveNullEntity() {
        assertNull(dao.save(null));
    }

    @Test
    public void replaceNullId() {
        dao.save(STRING1);

        dao.replace(null, null);

        // Nothing should be replaced
        assertEquals(1, dao.getAll().size());
    }

    @Test (expected = ApplicationRuntimeException.class)
    public void replaceIncorrectId() {
        Long id = dao.save(STRING1);

        dao.replace(id + 1, STRING2);
    }

    @Test
    public void delete() {
        Long id = dao.save(STRING1);

        dao.delete(id);

        // Value should be removed
        assertEquals(0, dao.getAll().size());
    }

    @Test
    public void deleteAll() {
        dao.save(STRING1);
        dao.save(STRING2);

        dao.deleteAll();

        // Value should be removed
        assertEquals(0, dao.getAll().size());
    }
}
