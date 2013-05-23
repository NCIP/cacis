<%--

    Copyright Ekagra Software Technologies Ltd.
    Copyright SAIC, Inc
    Copyright 5AM Solutions
    Copyright SemanticBits Technologies

    Distributed under the OSI-approved BSD 3-Clause License.
    See http://ncip.github.com/cacis/LICENSE.txt for details.

--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK href="cacisweb.css" rel="stylesheet" type="text/css">

<TITLE><s:text name="navleft.title" /></TITLE>
<SCRIPT LANGUAGE="JavaScript" src="script/list.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="script/resize.js"></SCRIPT>

<%--  DO NOT FORMAT THE CODE USING ECLIPSE. <s:text TAGS GET REPLACED --%>

<SCRIPT language="JavaScript" type="text/javascript">
	function init() {
		mainList = new List(true, null, null, null);
		subList1 = new List(false, null, null, null);
		subList1.addItem(
				"loadHref('secureEmailRecipientList.action', top.mainFrame)",
				'<s:text name="navleft.secureEmail" />');
		subList1.addItem(
				"loadHref('secureFTPRecipientList.action', top.mainFrame)",
				'<s:text name="navleft.secureFTP" />');
		subList1.addItem(
				"loadHref('secureXDSNAVRecipientList.action', top.mainFrame)",
				'<s:text name="navleft.secureXDSNAV" />');
		mainList.addList(subList1, '<s:text name="navleft.recipient" />');

		subList1 = new List(false, null, null, null);
		subList1.addItem(
				"loadHref('cdwUserPermission.action', top.mainFrame)",
				'<s:text name="navleft.cdwUserPermission" />');
		mainList.addList(subList1, '<s:text name="navleft.cdwsetup" />');

		mainList.build(0, 0);
	}
</SCRIPT>

<div style="position: absolute;"><a href="#skip"> <img
	src="http://cabig-ut.nci.nih.gov/skipnav.gif" border="0" height="1"
	width="1" alt="Skip Navigation" title="Skip Navigation" /> </a></div>

</HEAD>
<BODY CLASS="menuBody" ONLOAD="_init()" BGCOLOR="#DDDDDD">
<a name="skip" id="skip"></a>
<div id="menus"></div>
<div id=endOfPage></div>
<SCRIPT>
	if (!document.layers) {
		init();
	}
</SCRIPT>

</BODY>

</HTML>




