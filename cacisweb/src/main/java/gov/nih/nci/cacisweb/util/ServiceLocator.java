/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacisweb.util;

import gov.nih.nci.cacisweb.CaCISWebConstants;
import gov.nih.nci.cacisweb.exception.ServiceLocatorException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * @author Ajay Nalamala
 */
public class ServiceLocator {

    private static ServiceLocator me;
    InitialContext context = null;
    String connectionResource = System.getProperty(CaCISWebConstants.COM_PRIMARY_VIRTUOSO_DATASOURCE_NAME);

    /**
     * Commons Logging instance.
     */
    static Logger log = Logger.getLogger(ServiceLocator.class);

    /**
     * Constructor for ServiceLocator.
     */
    public ServiceLocator() throws ServiceLocatorException {
        try {
            context = new InitialContext();
        } catch (NamingException ne) {
            log.error(ne.getMessage(), ne);
            throw new ServiceLocatorException();
        }
    }

    /**
     * Method getInstance.
     * 
     * @return ServiceLocator
     * @throws ServiceLocatorException
     */
    public static ServiceLocator getInstance() throws ServiceLocatorException {
        if (me == null) {
            me = new ServiceLocator();
        }
        return me;
    }

    /**
     * Method getDataSource.
     * 
     * @return DataSource
     * @throws ServiceLocatorException
     */
    public DataSource getDataSourceByName(String dataSourceName) throws ServiceLocatorException {
        DataSource dataSource = null;
        try {
            if (connectionResource != null) {
                dataSource = (DataSource) context.lookup(connectionResource);
            } else {
                dataSource = (DataSource) context.lookup(dataSourceName);
            }
            return dataSource;
        } catch (NamingException ne) {
            log.error(ne.getMessage(), ne);
            throw new ServiceLocatorException(ne.getMessage());
        }
    }

}
