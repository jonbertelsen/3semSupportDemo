
package dat.supportweb.servlets;

import dat.supportweb.entities.Student;
import dat.supportweb.entities.Ticket;
import dat.supportweb.exceptions.DatabaseException;
import dat.supportweb.persistence.WaitingListMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try 
        {
            ticketList = WaitingListMapper.getAllTickets();
        } 
        catch (DatabaseException ex) 
        {
            Logger.getLogger(Waitinglist.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
                    Ticket ticket = new Ticket(new Student(name));
                    try 
                    {
                        WaitingListMapper.createTicket(ticket);
                    }
                    catch (DatabaseException ex) 
                    {
                        Logger.getLogger(Waitinglist.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "remove":     
                    try 
                    {
                        WaitingListMapper.removeFirstTicket();
                    } 
                    catch (DatabaseException ex) 
                    {
                        Logger.getLogger(Waitinglist.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
            
        try 
        {
            ticketList = WaitingListMapper.getAllTickets();
        } 
        catch (DatabaseException ex) 
        {
            Logger.getLogger(Waitinglist.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            request.setAttribute("ticketList", ticketList);
            request.getRequestDispatcher("/waitinglist.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
