<%@ page import="java.util.List" %>
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
    <h2>LISTS
        <%--Form to run search, searchs for the string typed in the search box--%>
        <form method="GET" action="runsearch.html" style="float: right">
            <input type="text" name="searched" placeholder="Search through the lists"/>
            <input type="submit" value="Search"/>
        </form>
    </h2>
    <%--Display list via a table--%>
    <table width="100%">
        <%

            ArrayList<String> mainList = (ArrayList<String>) request.getAttribute("mainList");
            for (String listName : mainList) {
                //generate link for every list
                String href = "list.html?listName=" + listName;
        %>
        <tr>
            <%--Displays the list name, and attaches a link that redirects the user to that list's page--%>
            <td><a href="<%=href%>"><%=listName%></a></td>
            <td>
                <%--Form with buttons to delete a List--%>
                <form method="POST" action="/mainList.html">
                    <input type="submit" value="Delete" name="deleteListButton" id="deleteListButton"
                           style="float: right"/>
                    <input type="hidden" name="deleteListName" value="<%=listName%>"/>
                </form>
            </td>
        </tr>
        <% } %>
        <tr>
            <%--Creates a new list--%>
            <form method="POST" action="/mainList.html">
                <input type="text" name="newListName" placeholder="Enter new list name"/>
                <input type="submit" value="Add list"/>
            </form>
        </tr>

    </table>
</div>
<%--<jsp:include page="/footer.jsp"/>--%>
</body>
</html>
