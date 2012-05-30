<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK href="cacisweb.css" rel="stylesheet" type="text/css">

<TITLE><s:text name="navleft.title" /></TITLE>
<SCRIPT LANGUAGE="JavaScript" src="script/list.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="script/resize.js"></SCRIPT>

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

</HEAD>
<BODY CLASS="menuBody" ONLOAD="_init()" BGCOLOR="#DDDDDD">
<div id="menus"></div>
<div id=endOfPage></div>
<SCRIPT>
	if (!document.layers) {
		init();
	}
</SCRIPT>

</BODY>

</HTML>




