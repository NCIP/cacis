/*
 * Copyright Ekagra Software Technologies Ltd.
 * Copyright SAIC, Inc
 * Copyright 5AM Solutions
 * Copyright SemanticBits Technologies
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cacis/LICENSE.txt for details.
 */
var BaseImg = BaseImg || "images/";
var _id = 0, _pid = 0, _lid = 0, _pLayer;
var _mLists = new Array();
var str2 = "";
var docColor="#DDDDDD"
//new Array('Initial Load Top Module TextColor', 'other colors didn't made any difference to layout)
var normalColor = new Array("#000000","#FFFFFF","#000000","#000000","#FFFFFF");
//var highlightColor = new Array("#100F70","#100F70","#100F70","#100F70","#100F70");
//new Array('on hover color of top module text', 
var highlightColor = new Array("#FFFFFF","#FFFFFF","#FFFFFF","#FFFFFF","#FFFFFF");
//new Array('background color of the menu on load', 
var normalBgColor = new Array("#CCCCCC","#006699","#657F7A","#E2BDF5","#E9DEB9");
//var highlightBgColor = new Array("#FFFF40","#FFFF40","#FFFF40","#FFFF40","#FFFF40");
//new Array('on hover background color of top module', 'on hover background color of sub menu items', 'rest of the color codes didn't have any affect');
var highlightBgColor = new Array("#A90101","#A90101","#000000","#000000","#000000");
var borderLightColor = new Array("#F9F9F9","#8AA8FF","#657F7A","#FFFFFF","#FFFFFF");
var borderDarkColor = new Array("#666666","#000066","#4F87AF","#9F80A0","#9F987F");
var highlightedLayer;
var highlightedLayer2;
document.lists = _mLists;
function _init() {
    if (document.layers) {
        init();
    }
}
function List(visible, width, height, bgColor) {
  this.setIndent = setIndent;
  this.addItem = addItem;
  this.addList = addList;
  this.build = build;
  this.rebuild = rebuild;
  this._writeList = _writeList;
  this._showList = _showList;
  this._updateList = _updateList;
  this._updateParent = _updateParent;
  this.onexpand = null; this.postexpand = null;
  this.onClickEvent=onClickEvent;
  this.onClickEvent2=onClickEvent2;
  this.mouseOverEvent=mouseOverEvent;
  this.mouseOutEvent=mouseOutEvent;
  this.lists = new Array(); // sublists
  this.layerId = new Array(); // layers
  this.layer2Id = new Array(); // second layers for NS4 to support color change.
  this.types = new Array(); // type
  this.strs = new Array();  // content or onclick value
  this.labels = new Array();  
  this.x = 0;
  this.y = 0;
  this.visible = visible;
  this.id = _id;
  this.i = 0;
  this.level = 0;
  this.space = true;
  this.pid = 0;
  //this.width = 164;
  this.width = document.body.clientWidth;
//  this.height = height || 22;
  this.height = 22;
  this.parLayer = false;
  this.built = false;
  this.shown = false;
  this.needsUpdate = false;
  this.needsRewrite = false;
  this.parent = null;
  this.l = 0;
  if(bgColor) this.bgColor = bgColor;
  else this.bgColor = "#1E117B";

  _mLists[_id++] = this;
}
function setIndent(indent) { this.i = 0; if(this.i < 0) { this.i = 0; this.space = false; } }
function setDocColor(color) {docColor = color;}
function _writeList() {
  self.status = "List: Writing list...";
  var layer, str, clip;
  for(var i = 0; i < this.types.length; i++) { 
    layer = this.layerId[i];
    setVisibility(self,layer,"hidden");
    if (document.layers) {
        layer2 = this.layer2Id[i];
        setVisibility(self,layer2,"hidden");
    }
    var curLayer = this;
    for(this.level = 0; this.level < 5; this.level++) {
        if (curLayer.parent) { 
            curLayer=curLayer.parent;
        } else {
            break;
        }
    }
    formStart = "<FORM>";
    hiddenFields = "<input type='hidden' name='level' style='height:0;width:0' value='"+this.level+"'>";
    hiddenFields += "<input type='hidden' name='layerId' style='height:0;width:0' value='"+this.layerId[i]+"'>";

    if(this.types[i] == "list") {
       hiddenFields += "<input type='hidden' name='listId' style='height:0;width:0' value='"+this.lists[i].id+"'>";
    } else {
      if (this.labels[i]) {
       hiddenFields += "<input type='hidden' name='onClickCmd' style='height:0;width:0' value=\""+this.strs[i]+"\">";
      }
    }
    str = "";
    thisTable="_table"+this.layerId[i];
    tableString = "<TABLE  ID="+thisTable+" NAME=" +thisTable;
    tableString +=" WIDTH="+this.width+" border=0 frame=border NOWRAP CELLPADDING=0 RULES=ROWS CELLSPACING=0 ";
    tableString += "onMouseOver='mouseOver(this,"+this.level+")' STYLE='border-collapse:collapse' ";
    tableString += "BORDERCOLORDARK=" + borderDarkColor[this.level] + " BORDERCOLORLIGHT=" + borderLightColor[this.level];
    tableString += " >";
    thisRow="_row"+this.layerId[i];
    if (document.layers) {
        tableString += "<TR ID="+thisRow;
    } else {
        tableString += "<TR ID="+thisRow+" bgColor='"+normalBgColor[this.level]+"' ";
    }
    if(this.types[i] == "list") {
        tableString += "onclick='expand("+this.lists[i].id+");return false;'";
    } else if (this.labels[i]) {
            tableString += "onclick=\""+this.strs[i]+";\"";
    }
    thisCell="_cell"+this.layerId[i];
    tableString += " onMouseOver='mouseOver(this,"+this.level+")' onMouseOut='mouseOut(this,"+this.level+")'";
    tableString += " STYLE='border-style: solid; border-top-width: 0px;border-bottom-width: 1px;border-left-width: 0px;	border-right-width: 0px;'>";
    if(this.l>0 && this.i>0) tableString += "<TD WIDTH="+this.l*this.i+" NOWRAP> &nbsp;</TD>";
    if(this.types[i] == "list") {
      tableString += "<TD WIDTH=15 NOWRAP VALIGN=MIDDLE height=" + this.height;
      tableString += " onMouseOver='changeColor(\"" + thisCell+"\",\""+highlightColor[this.level]+"\")'";
      tableString += " onMouseOut='changeColor(\"" + thisCell+"\",\""+normalColor[this.level]+"\")'";
      tableString += ">";
      imageString = "  <IMG BORDER=0 SRC=\""+BaseImg+"true.gif\" alt=\"Expand\" title=\"Expand\" NAME=\"_img"+this.lists[i].id+"\" ID=\"_img"+this.lists[i].id+"\">";

   // Create two image layers because IE 5.x goes back to server when image is replaced using source.  
      if (!document.layers) {
         imageString2 = "<DIV ID="+this.layerId[i]+"true ";
         imageString2 += " STYLE='position:absolute;top:6;left:2;visibility:visible;height:12;width:12'>" ;
         imageString = imageString2 +imageString+ "</DIV>";
         imageString += "<DIV ID="+this.layerId[i]+"false ";
         imageString += " STYLE='position:relative;top:-0;left:1;visibility:visible;height:12;width:12'>";
         imageString += " <IMG BORDER=0 SRC=\""+BaseImg+"false.gif\" alt=\"Close\" title=\"Close\" NAME=\"_img"+this.lists[i].id+"\" ID=\"_img"+this.lists[i].id+"\">";

         imageString += "</DIV>";
      }

      tableString += imageString + "</TD>";
      _pid++;
    } else {
//          if(this.space && this.l > 0) {
             tableString += "<TD WIDTH=15 NOWRAP ALIGN=LEFT VALIGN=MIDDLE height="+this.height;
      tableString += " onMouseOver='changeColor(\"" + thisCell+"\",\""+highlightColor[this.level]+"\")'";
      tableString += " onMouseOut='changeColor(\"" + thisCell+"\",\""+normalColor[this.level]+"\")'";
      tableString += ">";
             tableString += "&nbsp;</TD>"
 //         }
    }
    fontStr =";font-size: 9pt;font-family: Verdana, Arial, Helvetica, \"Sans Serif\"; font-style: normal; font-weight: bold;text-align:left;'";
    tableString += "<TD NAME='"+thisCell+"' ID='"+thisCell+"' height="+this.height+" WIDTH="+(this.width-15)+" VALIGN=MIDDLE ALIGN=LEFT ";
    tableString += " style='color: " + normalColor[this.level ]+ fontStr;
    tableString += " onMouseOver='changeColor(\"" + thisCell+"\",\""+highlightColor[this.level]+"\")'";
    tableString += " onMouseOut='changeColor(\"" + thisCell+"\",\""+normalColor[this.level]+"\")'";
    tableString += ">";
    spanStr1 = "<SPAN STYLE='color:"+normalColor[this.level] + fontStr + ">";
    spanStr2 = "<SPAN STYLE='color:"+highlightColor[this.level] + fontStr + ">";
    if (this.labels[i]) {
        str3 = this.labels[i]
    } else {
        str3 = this.strs[i];
    }
    finalTags = "</TD></TR></TABLE>";
    if (!document.layers) {
       setHTML(self,layer,tableString+str3+finalTags);
       //setFont(thisCell);
    } else {
       altLayerStr1 = "<input type='hidden'  style='height:0;width:0' name='altLayer'  value='"+this.layer2Id[i]+"'>";
       altLayerStr2 = "<input type='hidden'  style='height:0;width:0' name='altLayer' value='"+this.layerId[i]+"'>";
       finalTags = "</SPAN>" + finalTags + "</FORM>";
       setHTML(self,layer,formStart+hiddenFields+altLayerStr1+tableString+spanStr1+str3+finalTags);
       setHTML(self,layer2,formStart+hiddenFields+altLayerStr2+tableString+spanStr2+str3+finalTags);
      // setFont(thisCell);
      // setFont(thisCell2);
       document.layers[layer].captureEvents(Event.MOUSEOVER);
       document.layers[layer2].captureEvents(Event.MOUSEOUT);
       document.layers[layer].onMouseOver=this.mouseOverEvent;
       document.layers[layer2].onMouseOut=this.mouseOutEvent;
       document.layers[layer].bgColor=normalBgColor[this.level];
       document.layers[layer2].bgColor=highlightBgColor[this.level];
       mouseOverStr="this.visibility = 'hide';"+this.layer2Id[i]+".visibility='show'";
   //    document.layers[this.layer2Id[i]].clip.bottom = document.layers[this.layer2Id[i]].clip.bottom - 17;
   //    document.layers[this.layerId[i]].clip.bottom = document.layers[this.layerId[i]].clip.bottom - 17;
       document.layers[this.layer2Id[i]].clip.bottom = this.height + 5;
       document.layers[this.layerId[i]].clip.bottom = this.height + 5;
       document.layers[layer2].captureEvents(Event.MOUSEDOWN);
       document.layers[layer].captureEvents(Event.MOUSEDOWN);
       if (this.types[i] == "list") {
          document.layers[layer].onMouseDown=this.onClickEvent;
          document.layers[layer2].onMouseDown=this.onClickEvent;
       } else {
         if (this.labels[i]) {
          document.layers[layer].onMouseDown=this.onClickEvent2;
          document.layers[layer2].onMouseDown=this.onClickEvent2;
         }
       }
    }
    if(this.types[i] == "list" && this.lists[i].visible)
      this.lists[i]._writeList();
  }
  this.built = true;
  this.needsRewrite = false;
  self.status = '';
}
function _showList() {
  var layer;
  for(var i = 0; i < this.types.length; i++) { 
    if (document.layers) {
        setVisibility(self,this.layerId[i],"visible"); 
        setVisibility(self,this.layer2Id[i],"hidden"); 
    }    
    var bg = this.bgColor;
    if((bg == null) || (bg == "null")) bg = "";
    if(this.types[i] == "list" && this.lists[i].visible)
      this.lists[i]._showList();
  }
  this.shown = true;
  this.needsUpdate = false;
}
function _updateList(pVis, x, y) {
  var currTop = y, layer;
  for(var i = 0; i < this.types.length; i++) { 
    if(this.visible && pVis) {
      setTopCoordinate(self,this.layerId[i],currTop);
      setLeftCoordinate(self,this.layerId[i],x);
      setVisibility(self,this.layerId[i],"visible");
      if (document.layers) {
          setTopCoordinate(self,this.layer2Id[i],currTop);
          setLeftCoordinate(self,this.layer2Id[i],x);
          currTop +=document.layers[this.layerId[i]].clip.bottom;
      } else { 
          currTop += document.getElementById("_table"+this.layerId[i]).offsetHeight;
      }
    } else {
          setVisibility(self,this.layerId[i],"hidden");
          setTopCoordinate(self,this.layerId[i],-100);
          setLeftCoordinate(self,this.layerId[i],-100);
          if (document.layers) {
              setTopCoordinate(self,this.layer2Id[i],0);
              setLeftCoordinate(self,this.layer2Id[i],0);
          }    
    }
    if(this.types[i] == "list") {
      if(this.lists[i].visible) {
        if(!this.lists[i].built || this.lists[i].needsRewrite) this.lists[i]._writeList();
        if(!this.lists[i].shown ||  this.lists[i].needsUpdate) this.lists[i]._showList();

         // For NS 4.x replace arrow image using source, otherwise swap visible layers
         // because IE 5.x goes back to server when image is replaced using source
            if (document.layers) {
               image=BaseImg+"true.gif";
            } else {
               setVisibility(self,this.layerId[i]+"false","hidden");
               setVisibility(self,this.layerId[i]+"true","visible");
            }
      } else {
            if (document.layers) {
               image=BaseImg+"false.gif";
            } else {
               setVisibility(self,this.layerId[i]+"true","hidden");
               setVisibility(self,this.layerId[i]+"false","visible");
            }
      }
      if (document.layers) {
          setSource(self,this.layerId[i],"_img"+this.lists[i].id,image);
          setSource(self,this.layer2Id[i],"_img"+this.lists[i].id,image);
      }
      if(this.lists[i].built){
        currTop = this.lists[i]._updateList(this.visible && pVis, x, currTop);
      }
    }
  }
  return currTop;
}
function _updateParent(pid, l) {
  var layer;
  if(!l) l = 0;
  this.pid = pid;
  this.l = l;
  for(var i = 0; i < this.types.length; i++)
    if(this.types[i] == "list")
      this.lists[i]._updateParent(pid, l+1);
}
function expand(i) {
  _mLists[i].visible = !_mLists[i].visible;
  if(_mLists[i].onexpand != null) _mLists[i].onexpand(_mLists[i].id);
  _mLists[_mLists[i].pid].rebuild();
  if(_mLists[i].postexpand != null) _mLists[i].postexpand(_mLists[i].id);
}
function build(x, y) {
    if (!document.layers) {
        if (str2) {
            if (!isObject(self,"menus")) {
                alert("<DIV> menus is not defined");
            } else {
                //setPosition(self,"menus","absolute");
                setHTML(self,"menus",str2);
           }
        }
    }
  this._updateParent(this.id);
  this._writeList();
  this._showList();
  this._updateList(true, x, y);
  this.x = x; this.y = y;
  document.bgColor=docColor;
}
function rebuild() { this._updateList(true, this.x, this.y); }
function addItem(str, label) {
     var layer2;
     var quote='"';
     var lyrName = quote+"lItem"+_lid+quote;
     if (document.layers) {
        layer = new Layer(this.width);
        layer = layer.id;
        layer2 = new Layer(this.width);
        layer2 = layer2.id;
     } else {
        layer = "lItem"+_lid;
        if (!isObject(self,layer)) {
           str2 = str2+"<div id="+lyrName+" name="+lyrName+" style=\"position:absolute;\"></div>\n";
        } else {
           setPosition(self,layer,"absolute");
        } 
     }
  this.labels[this.labels.length] = label;
  this.layerId[this.layerId.length] = layer;
  this.layer2Id[this.layer2Id.length] = layer2;
  this.types[this.types.length] = "item";
  this.strs[this.strs.length] = str;
  _lid++;
}
function addList(list, str, bgColor, layer) {
  this.addItem(str, bgColor, layer);
  this.lists[this.types.length-1] = list;
  this.types[this.types.length-1] = "list";
  list.parent = this;
}
function mouseOverEvent(e) {
    if (highlightedLayer) {
        highlightedLayer2.visibility="hidden";
        highlightedLayer.visibility="visible";
    }
    highlightedLayer = this;
    highlightedLayer2 = document.layers[this.document.forms[0].altLayer.value];
    this.visibility="hidden";
    highlightedLayer2.visibility="visible";

}
function mouseOutEvent(e) {
    this.visibility="hidden";
    document.layers[this.document.forms[0].altLayer.value].visibility="visible";
}
function onClickEvent(e) {
    expand(this.document.forms[0].listId.value);
}
function onClickEvent2(e) {

            eval(this.document.forms[0].onClickCmd.value);
}
function mouseOver(x,level) {
  x.bgColor = highlightBgColor[level];
}
function mouseOut(x,level) {

  x.bgColor = normalBgColor[level];
}
function changeColor(x,color) {
    setColor(self,x, color);
}
function loadHref(url,target) {
    target.location=url;
}
function setFont(objectName)
{

   if (document.getElementById)
        {
                document.getElementById(objectName).style.fontSize="8pt";
                document.getElementById(objectName).style.fontFamily="arial, helvetica, sans-serrif";
        }
        else if(document.all)
        {
                        document.all(objectName).style.fontSize="8pt";
        }
        else if(document.layers)
        {
                        document.layers[objectName].fontSize="8pt";;

        }

}//End setFont
function setColor(frame,objectName,color)
{

   if (document.getElementById)
        {
                frame.document.getElementById(objectName).style.color=color;

        }
        else if(document.all)
        {
                        frame.document.all(objectName).style.color=color;
        }
        else if(document.layers)
        {
                    //    frame.document.layers[objectName].fgcolor=color;

        }

}//End setColor
function setVisibility(frame,objectName,visibility)
{
  if (document.getElementById)
        {
                frame.document.getElementById(objectName).style.visibility=visibility;
        }
        else if(document.all)
        {
                frame.document.all(objectName).style.visibility=visibility;
        }
        else if(document.layers)
        {
                frame.document.layers[objectName].visibility=visibility;
        }

}//End setVisibility
function setPosition(frame,objectName,position)
{
  if (document.getElementById)
        {
                frame.document.getElementById(objectName).style.position=position;
        }
        else if(document.all)
        {
                frame.document.all(objectName).position=position;
        }
        else if(document.layers)
        {
                frame.document.layers[objectName].position=position;
        }

}//End setPosition
function setSource(frame,layer,objectName,source)
{
  if (document.getElementById)
        {
                frame.document.getElementById(objectName).src=source;
        }
        else if(document.all)
        {
                frame.document.all(objectName).src=source;
        }
        else if(document.layers)
        {
                frame.document.layers[layer].document.images[objectName].src=source;
        }

}//End setSource
function setTopCoordinate(frame,objectName,coordinate)
{
  if (document.getElementById)
        {
                frame.document.getElementById(objectName).style.top=coordinate;
        }
        else if(document.all)
        {
                frame.document.all(objectName).style.top=coordinate;
        }
        else if(document.layers)
        {
                frame.document.layers[objectName].top=coordinate;
        }

}//End setTopCoordinate
function setLeftCoordinate(frame,objectName,coordinate)
{
  if (document.getElementById)
        {
                frame.document.getElementById(objectName).style.left=coordinate;
        }
        else if(document.all)
        {
                frame.document.all(objectName).style.left=coordinate;
        }
        else if(document.layers)
        {
                frame.document.layers[objectName].left=coordinate;
        }

}//End setLeftCoordinate
function setHTML(frame,objectName,source)
{
  if (document.getElementById)
        {
                frame.document.getElementById(objectName).innerHTML=source;
        }
        else if(document.all)
        {
                frame.document.all(objectName).innerHTML=source;
        }
        else if(document.layers)
        {
                frame.document.layers[objectName].document.open();
                frame.document.layers[objectName].document.writeln(source);
                frame.document.layers[objectName].document.close();
        }

}//End setHTML

function isObject(frame,objectName)
{
         if (document.getElementById)
        {
                if(frame.document.getElementById(objectName))
                 return(1);
                else
                 return(0);
        }
        else if(document.all)
        {
                        if(frame.document.all(objectName))
                 return(1);
                else
                 return(0);
        }

        else if(document.layers)
        {
                        if(frame.document.layers[objectName])
                 return(1);
                else
                 return(0);
        }

}//End isObject

