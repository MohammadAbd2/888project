<!--#include virtual="include/include.asp" -->
<%
on error resume next
set xmlhttp = CreateObject("MSXML2.ServerXMLHTTP")
	xmlhttp.open "Post", strAPI & "&Command=AccountsList&Fields=Player", false
	xmlhttp.send ""
	strResult = xmlhttp.responseText
set xmlhttp = nothing
if Err.number <> 0 then
%>
if ($(location).attr('href').replace("http://","").replace("/","")!='<%=replace(replace(strIPApp,"http://",""),"/","")%>'){
    $(location).attr('href','<%=strIPApp%>');
	$('overly').fadeIn();
}
<%
else
intTotalPlayer = Cint(ShowAPI(strResult,"Accounts"))
response.write("var data = """)
for intCounter = 1 to intTotalPlayer
	response.write(ShowAPI(strResult,"Player" & intCounter & ""))
	if intCounter < intTotalPlayer then response.write(",")
next
response.write(""".split("","");")
end if
%>
