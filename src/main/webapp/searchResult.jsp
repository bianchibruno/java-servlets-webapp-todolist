<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <%--Make use of the styles.css file for styling--%>
    <jsp:include page="/meta.jsp"/>
    <title>CEOofLists</title>
</head>
<body>
<%--Use the header in header.jsp--%>
<jsp:include page="/header.jsp"/>
<div class="list">
    <h2>Search results for: <%=request.getAttribute("searched")%></h2>
    <table width="100%">
        <%
            //For every value in the result ArrayList given by model.contains()
            ArrayList<String> strings = (ArrayList<String>) request.getAttribute("Strings");
            ArrayList<String> lists = (ArrayList<String>) request.getAttribute("Lists");
            for (String value : strings) {
                int index = strings.indexOf(value);
                String listName = lists.get(index);
        %>
        <tr>
            <%--Attach the value to a row--%>
            <td><%=value%></td>
                <%--Display the list it belongs to--%>
            <td>in <a href="/list.html?listName=<%=listName%>"><%=listName%></a></td>
        </tr>
        
        <% } %>

    </table>
</div>
</body>
</html>
