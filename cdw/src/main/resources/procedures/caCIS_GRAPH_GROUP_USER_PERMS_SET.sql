--
-- Copyright Ekagra Software Technologies Ltd.
-- Copyright SAIC, Inc
-- Copyright 5AM Solutions
-- Copyright SemanticBits Technologies
--
-- Distributed under the OSI-approved BSD 3-Clause License.
-- See http://ncip.github.com/cacis/LICENSE.txt for details.
--

drop procedure caCIS_GRAPH_GROUP_USER_PERMS_SET
/

--This procedure grants a user permissions to read or read/write or noacess to members of a graph group 
--permission 0 is for no access
--permission 1 is for read only
--permission 3 is read/write acess 
create procedure caCIS_GRAPH_GROUP_USER_PERMS_SET (in user_name varchar, in graph_group_uri varchar, in permission integer) {
  declare user_id, graph_group_id integer;
  whenever not found goto nf;
  
  if (user_name is null or user_name='') 
  	return 0;
  
  if (graph_group_uri is null or graph_group_uri='')
    return 0;
  
  select RGG_IID into graph_group_id from RDF_GRAPH_GROUP where RGG_IRI = graph_group_uri;
  if (graph_group_id = 0)
  	return 1;
  
  DB.DBA.RDF_DEFAULT_USER_PERMS_SET (user_name, 0);
  
  if(permission = 0)
  	  DB.DBA.RDF_GRAPH_USER_PERMS_SET (graph_group_uri, user_name, 0);
  else
  	  DB.DBA.RDF_GRAPH_USER_PERMS_SET (graph_group_uri, user_name, 8);	
  
  declare cr_graph_group_members cursor for 	
	select RGGM_MEMBER_IID from RDF_GRAPH_GROUP_MEMBER where RGGM_GROUP_IID = graph_group_id;
	declare graph_group_member_id integer;
	declare named_graph_iri varchar;
	whenever not found goto nf;
	open cr_graph_group_members;
	while (1) {
		fetch cr_graph_group_members into graph_group_member_id;
		named_graph_iri := id_to_iri(graph_group_member_id);
		DB.DBA.RDF_GRAPH_USER_PERMS_SET (named_graph_iri,user_name, permission);
	}
nf:
  return 0;
}
/