package ucl.ac.uk.servlets;

import ucl.ac.uk.model.List;
import ucl.ac.uk.model.Model;
import ucl.ac.uk.model.ModelFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/list.html"})
public class ViewListServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get the data from the model
        Model model = ModelFactory.getModel();

        // Then add the data to the request object that will be sent to the Java Server Page, so that
        // the JSP can access the data (a Java data structure).
        String myListName = request.getParameter("listName");
        List myList = model.getList(myListName);

        request.setAttribute("myListName", myList.getName());
        request.setAttribute("myList", myList.getList());
        request.setAttribute("listURL", request.getRequestURL() + "?listName=" + myList.getName());

        // Invoke the JSP.
        // A JSP page is actually converted into a Java class, so behind the scenes everything is Java.
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/list.jsp");
        dispatch.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get the data from the model
        Model model = ModelFactory.getModel();

        // Then add the data to the request object that will be sent to the Java Server Page, so that
        // the JSP can access the data (a Java data structure).
        String myListName = request.getParameter("listName");
        List myList = model.getList(myListName);

        //If we receive a parameter "newElement", add string newElement to myList and update the model
        if (request.getParameter("newElement") != null) {
            model.addElement(request.getParameter("newElement"), myListName);
        }

        //Delete deleteElement from myList and update the model
        if (request.getParameter("deleteElement") != null) {
            model.removeElement(request.getParameter("deleteElement"), myListName);
        }

        //Rename myList and update the model
        //Trim gets rid or leading and trailing spaces, to avoid undesired behavious
        if (request.getParameter("newListName") != null) {
            model.renameList(request.getParameter("newListName").trim(), myListName);
        }

        //Rename given element and update the model
        if (request.getParameter("newElementName") != null) {
            model.renameElement(request.getParameter("oldElementName"), request.getParameter("newElementName"), myListName);
        }

        //Give the JSP access to data.
        request.setAttribute("myListName", myList.getName());
        request.setAttribute("myList", myList.getList());
        request.setAttribute("listURL", request.getRequestURL() + "?listName=" + myList.getName());

        // Invoke the JSP.
        // A JSP page is actually converted into a Java class, so behind the scenes everything is Java.
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/list.jsp");
        dispatch.forward(request, response);
    }
}
