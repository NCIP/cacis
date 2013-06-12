/**
 * Copyright 5AM Solutions Inc
 * Copyright SemanticBits LLC
 * Copyright AgileX Technologies, Inc
 * Copyright Ekagra Software Technologies Ltd
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw;

import java.util.Set;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import org.openrdf.model.URI;

/**
 * Handles provisioning of access control at the graph group and graph levels.
 * 
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * @since Jul 15, 2011
 * 
 */

public interface GraphAuthzMgr {

    /**
     * Adds the given graph to the given graph groups and grants READ access on that graph to all users that have READ
     * access on the graph group.
     * 
     * @param graph the graph to add
     * @param graphGroups the set of graph groups to add the graph to
     * @throws AuthzProvisioningException on error
     */
    void add(URI graph, Set<URI> graphGroups) throws AuthzProvisioningException;

    /**
     * Removes the given graph from the given graph groups and removes READ access on that graph to all users that have
     * READ access on the graph group.
     * 
     * @param graph the graph to add
     * @param graphGroups the set of graph groups to add the graph to
     * @throws AuthzProvisioningException on error
     */
    void remove(URI graph, Set<URI> graphGroups) throws AuthzProvisioningException;

}
