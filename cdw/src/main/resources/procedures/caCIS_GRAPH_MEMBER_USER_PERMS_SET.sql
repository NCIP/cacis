drop procedure caCIS_GRAPH_MEMBER_USER_PERMS_SET
/
--Every user, who has access to the group in which the newly added graph is a member
--will be granted read or read/write or noacess
--permission 0 is for no access
--permission 1 is for read only
--permission 3 is read/write acess  
create procedure caCIS_GRAPH_MEMBER_USER_PERMS_SET (in graph_member_uri varchar, in permission integer) {
  
  if (graph_member_uri is null or graph_member_uri='')
    return 0;
   
  for 
    select RGGM_GROUP_IID from RDF_GRAPH_GROUP_MEMBER where RGGM_MEMBER_IID = iri_to_id(graph_member_uri) DO 
    {
--dbg_printf('%S', id_to_iri(RGGM_GROUP_IID));

  for
    select sy.U_NAME as u_name from SYS_USERS sy, RDF_GRAPH_USER rgu where sy.U_ID = rgu.RGU_USER_ID and rgu.RGU_GRAPH_IID = RGGM_GROUP_IID DO 
    {
--dbg_printf('%S', u_name);
    DB.DBA.RDF_GRAPH_USER_PERMS_SET (graph_member_uri, u_name, permission);
    }

}

return 0;
}
/