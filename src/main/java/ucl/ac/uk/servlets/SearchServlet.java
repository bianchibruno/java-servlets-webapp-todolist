package ucl.ac.uk.servlets;

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


// The servlet invoked to perform a search.
// The url http://localhost:8080/runsearch.html is mapped to calling doPost on the servlet object.
// The servlet object is created automatically, you just provide the class.
@WebServlet("/runsearch.html")
public class SearchServlet extends HttpServlet {
    //Upon getting a GET request
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Use the model to do the search and put the results into the request object sent to the
        // Java Server Page used to display the results.
        //Get access to the Model
        Model model = ModelFactory.getModel();

        //The first ArrayList in the ArrayList returned by model.contains() stores all the strings that has the
        //Parameter "searched"
        ArrayList<String> stringsContainingSubstring = model.contains(request.getParameter("searched")).get(0);
        //The second ArrayList contains all the Lists that contain the strings that have "searched" as a substring.
        ArrayList<String> listContainingString = model.contains(request.getParameter("searched")).get(1);

        //Giving the jsp access to data.
        request.setAttribute("searched", request.getParameter("searched"));
        request.setAttribute("searchResult", model.contains(request.getParameter("searched")));
        request.setAttribute("Strings", stringsContainingSubstring);
        request.setAttribute("Lists", listContainingString);

        // Invoke the JSP page.
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/searchResult.jsp");
        dispatch.forward(request, response);
    }
}