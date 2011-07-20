Setting up Virtuoso with sample data.
---------------------------------------

1:	Procedure to load RDF with a Named Graph IRI

	a:	Go to the RDF > RDF Store Upload tab. 
	b:	Select the File radio button and click the Choose File button to select <sample>.rdf 
	c:	Then, in the Named Graph IRI text box, enter <named graph iri>. 
	d:	Then, click Upload. 
	e:	The page will indicate “Upload finished”.

	Upload sample-rdf.xml with named graph uri as http://university.edu/science_dept/microprocessor_course/student1
	Upload sample-rdf.xml with named graph uri as http://university.edu/science_dept/microprocessor_course/student2
	Upload sample-rdf.xml with named graph uri as http://university.edu/science_dept/operatingsystems_course/student3
	Upload sample-rdf.xml with named graph uri as http://university.edu/science_dept/operatingsystems_course/student4

The below commands should be executed in the Interactive SQL (ISQL) window. 
	a:	login to http://localhost:8890/conductor as dba/dba
	b:	Click on the Interactive SQL (ISQL). This will pop up a different window.  

2: User creation 
	DB.DBA.USER_CREATE ('anna', 'anna');
	grant SPARQL_UPDATE to "anna";

3:	-- At least some data are supposed to be confidential, thus the whole storage becomes confidential.
	DB.DBA.RDF_DEFAULT_USER_PERMS_SET ('nobody', 0);
	
	-- No one of created users have access to all graphs (even for reading).
	DB.DBA.RDF_DEFAULT_USER_PERMS_SET ('anna', 0);
	
4:	-- Graph groups have its own security.		

	DB.DBA.RDF_GRAPH_GROUP_CREATE('http://university.edu/science_dept/operatingsystems_course', 1);
	DB.DBA.RDF_GRAPH_GROUP_INS('http://university.edu/science_dept/operatingsystems_course', 'http://university.edu/science_dept/operatingsystems_course/student3');
	DB.DBA.RDF_GRAPH_GROUP_INS('http://university.edu/science_dept/operatingsystems_course', 'http://university.edu/science_dept/operatingsystems_course/student4');

	DB.DBA.RDF_GRAPH_GROUP_CREATE('http://university.edu/science_dept/microprocessor_course', 1);
	DB.DBA.RDF_GRAPH_GROUP_INS('http://university.edu/science_dept/microprocessor_course', 'http://university.edu/science_dept/microprocessor_course/student2');
	DB.DBA.RDF_GRAPH_GROUP_INS('http://university.edu/science_dept/microprocessor_course', 'http://university.edu/science_dept/microprocessor_course/student1');

	DB.DBA.RDF_GRAPH_GROUP_CREATE('http://university.edu/science_dept', 1);
	DB.DBA.RDF_GRAPH_GROUP_INS('http://university.edu/science_dept', 'http://university.edu/science_dept/microprocessor_course/student1');
	DB.DBA.RDF_GRAPH_GROUP_INS('http://university.edu/science_dept', 'http://university.edu/science_dept/microprocessor_course/student2');
	DB.DBA.RDF_GRAPH_GROUP_INS('http://university.edu/science_dept', 'http://university.edu/science_dept/operatingsystems_course/student3');
	DB.DBA.RDF_GRAPH_GROUP_INS('http://university.edu/science_dept', 'http://university.edu/science_dept/operatingsystems_course/student4');


	DB.DBA.RDF_GRAPH_USER_PERMS_SET ('http://university.edu/science_dept/operatingsystems_course', 'anna', 8);

	
5:	--Grant permissions to anna to be able to read all the graphs under a graph group
	DB.DBA.RDF_GRAPH_USER_PERMS_SET ('http://university.edu/science_dept/operatingsystems_course/student3','anna',1);
	DB.DBA.RDF_GRAPH_USER_PERMS_SET ('http://university.edu/science_dept/operatingsystems_course/student4','anna',1);


6:	log on to the http://localhost:8890/sparql-auth as anna and try the following SPARQL:

	SELECT ?x  ?y ?z
	FROM <http://university.edu/science_dept/operatingsystems_course>
	WHERE {  ?x ?y  ?z}
