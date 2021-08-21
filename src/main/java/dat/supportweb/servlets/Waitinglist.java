
package dat.supportweb.servlets;

import dat.supportweb.entities.Student;
import dat.supportweb.entities.Ticket;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "waitinglist", urlPatterns = {"/waitinglist"})
public class Waitinglist extends HttpServlet { 

    private List<Ticket> ticketList;
    private List<Student> studentList;
    
    public void init() throws ServletException
    {
        ticketList = new ArrayList<>();
        studentList = new ArrayList<>();
        studentList.add(new Student("Blondie"));
        studentList.add(new Student("BJønke"));
        studentList.add(new Student("Makrel"));
        studentList.add(new Student("Ludvig"));
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setAttribute("ticketList", ticketList);
        
        request.getRequestDispatcher("/waitinglist.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
        
            String command = request.getParameter("command");
            
            switch (command){
                case "add":
                    String name = request.getParameter("requestname");
                    ticketList.add(new Ticket(new Student(name)));
                    break;
                case "remove":
                    if (ticketList.size() > 0)
                    {
                        ticketList.remove(0);
                    }
                    break;
            
            }
            request.setAttribute("ticketList", ticketList);
            request.getRequestDispatcher("/waitinglist.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
