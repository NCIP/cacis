--
-- Copyright 5AM Solutions Inc
-- Copyright SemanticBits LLC
-- Copyright AgileX Technologies, Inc
-- Copyright Ekagra Software Technologies Ltd
--
-- Distributed under the OSI-approved BSD 3-Clause License.
-- See http://ncip.github.com/cacis/LICENSE.txt for details.
--

INSERT INTO tbl_resource(resource_type, entityid) VALUES ('operation-store',-101);
INSERT INTO tbl_subject(entityid, dn) VALUES (-101, 'EMAILADDRESS=kherm@semanticbits.com, CN=M, OU=caCIS, O=CBIIT, L=Rockville, ST=MD, C=US');
INSERT INTO tbl_resource_tbl_subject(tbl_resource_entityid, subjects_entityid) VALUES (-101, -101);