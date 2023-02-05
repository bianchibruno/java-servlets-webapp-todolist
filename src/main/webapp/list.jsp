<%@ page import="java.util.ArrayList" %>
<%--allows us to use java throughout the file--%>
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
    <%--Title of the list will be the attribute "myListName" sent by the servlet--%>
    <h2><%=request.getAttribute("myListName")%>
        <%--Form where we can input a new list name, it'll forward us to the listURL specified in ViewListServlet--%>
        <form method="POST" action="<%=request.getAttribute("listURL")%>" style="float: right">
            <%--Text box where we can input our new list name--%>
            <input type="text" name="newListName" placeholder="Rename this list"/>
            <%--Submit button, it submits the form with the specified attributes attached to it--%>
            <input type="submit" value="Rename"/>
        </form>
    </h2>
    <%--Display list via a table--%>
    <table width="100%">
        <%
            //For every element in myList, create a new row
            for (String element : (ArrayList<String>) request.getAttribute("myList")) {
        %>
        <tr>
            <%--First value in the row will be the element--%>
            <td><%=element%>
            </td>
            <td>
                <%--Generate a button that deletes the element--%>
                <form method="POST" action="<%=request.getAttribute("listURL")%>">
                    <input type="submit" value="Delete" name="delete" id="deleteButton" style="float: right"/>
                    <input type="hidden" name="deleteElement" value="<%=element%>"/>
                </form>
            </td>
            <td>
                <%--Text box used to rename the element--%>
                <form method="POST" action="<%=request.getAttribute("listURL")%>">
                    <input type="text" name="newElementName" placeholder="Rename this item" style="float: right"/>
                    <input type="submit" value="Rename" name="renameElementButton" id="renameElementButton"
                           style="float: right"/>
                    <input type="hidden" name="oldElementName" value="<%=element%>"/>
                </form>
            </td>
        </tr>
        <% } %>
        <tr>
            <%--Text box that allows us to add a new Element--%>
            <form method="POST" action="<%=request.getAttribute("listURL")%>">
                <input type="text" name="newElement" placeholder="Enter new item"/>
                <input type="submit" value="Add Element"/>
            </form>
        </tr>

    </table>
</div>

</body>
</html>
