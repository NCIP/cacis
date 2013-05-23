/**
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
package gov.nih.nci.cacis.cdw;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.nih.nci.cacis.common.exception.AuthzProvisioningException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.openrdf.model.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author <a href="mailto:vinodh.rc@semanticbits.com">Vinodh Chandrasekaran</a>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-mock-cdw-test.xml")
public class GraphAuthzMgrSystemTest {
    
    @Autowired
    private GraphAuthzMgr graphAuthzMgr;
    
    @Autowired
    private SimpleJdbcTemplate simpleJdbcTemplate;
    
    /**
     * Tests adding graph to graph groups with mock jdbc template
     * @throws AuthzProvisioningException - exception thrown, if any
     */
    @Test
    public void add() throws AuthzProvisioningException {
        assertNotNull(graphAuthzMgr);
        assertNotNull(simpleJdbcTemplate);
        
        final URI graph = mock(URI.class);
        final Set<URI> grphGrps = new HashSet<URI>();
        final URI graphGrp = mock(URI.class);
        grphGrps.add(graphGrp);
        
        graphAuthzMgr.add(graph, grphGrps);
        
        final ArgumentCaptor<String> ins_sql = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<List> ins_sql_args = ArgumentCaptor.forClass(List.class);
        verify(simpleJdbcTemplate, atLeastOnce()).batchUpdate(ins_sql.capture(), ins_sql_args.capture());
        
        assertNotNull(ins_sql.getValue());
        assertTrue(ins_sql.getValue().contains("DB.DBA.RDF_GRAPH_GROUP_INS"));
        
        final List ins_sql_args_value = ins_sql_args.getValue();        
        assertEquals(1, ins_sql_args_value.size());         
        assertTrue(ins_sql_args_value.get(0) instanceof Object[]);
        
        final Object[] objArr = (Object[]) ins_sql_args_value.get(0);   
        assertEquals(graph.toString(), objArr[1]);
        assertEquals(graphGrp.toString(), objArr[0]); 
        
        final ArgumentCaptor<String> user_perms_sql = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> graph_arg = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Integer> perm_arg = ArgumentCaptor.forClass(Integer.class);
        verify(simpleJdbcTemplate, atLeastOnce()).update(user_perms_sql.capture(), graph_arg.capture(), perm_arg.capture());
        
        assertNotNull(user_perms_sql.getValue());
        assertNotNull(graph_arg.getValue());
        assertNotNull(perm_arg.getValue());
        
        assertEquals(graph.toString(), graph_arg.getValue());
        assertEquals(Integer.valueOf(1), perm_arg.getValue());
    }
    
    /**
     * Tests removing graph from graph groups with mock jdbc template
     * @throws AuthzProvisioningException - exception thrown, if any
     */
    @Test
    public void remove() throws AuthzProvisioningException {
        assertNotNull(graphAuthzMgr);
        assertNotNull(simpleJdbcTemplate);
        
        final URI graph = mock(URI.class);
        final Set<URI> grphGrps = new HashSet<URI>();
        final URI graphGrp = mock(URI.class);
        grphGrps.add(graphGrp);
        
        graphAuthzMgr.remove(graph, grphGrps);
        
        final ArgumentCaptor<String> rem_sql = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<List> rem_sql_args = ArgumentCaptor.forClass(List.class);
        verify(simpleJdbcTemplate, atLeastOnce()).batchUpdate(rem_sql.capture(), rem_sql_args.capture());
        
        assertNotNull(rem_sql.getValue());
        assertTrue(rem_sql.getValue().contains("DB.DBA.RDF_GRAPH_GROUP_DEL"));
        
        final List rem_sql_args_value = rem_sql_args.getValue();        
        assertEquals(1, rem_sql_args_value.size());         
        assertTrue(rem_sql_args_value.get(0) instanceof Object[]);
        
        final Object[] objArr = (Object[]) rem_sql_args_value.get(0);   
        assertEquals(graph.toString(), objArr[1]);
        assertEquals(graphGrp.toString(), objArr[0]);
    }
    
    /**
     * Tests validating null graph
     * @throws AuthzProvisioningException - exception thrown, if any
     */
    @Test(expected=AuthzProvisioningException.class)
    public void validateNullGraph() throws AuthzProvisioningException {        
        graphAuthzMgr.add(null, null);
    }
    
    /**
     * Tests validating null graph groups
     * @throws AuthzProvisioningException - exception thrown, if any
     */
    @Test(expected=AuthzProvisioningException.class)
    public void validateNullGraphGroups() throws AuthzProvisioningException {       
        final URI graph = mock(URI.class);
        graphAuthzMgr.add(graph, null);
    }
    
    /**
     * Tests validating empty graph groups
     * @throws AuthzProvisioningException - exception thrown, if any
     */
    @Test(expected=AuthzProvisioningException.class)
    public void validateEmptyGraphGroups() throws AuthzProvisioningException {
        final URI graph = mock(URI.class);
        final Set<URI> grphGrps = new HashSet<URI>();
        graphAuthzMgr.add(graph, grphGrps);
    }
}
