
package dat.supportweb.persistence;

import dat.supportweb.entities.Student;
import dat.supportweb.entities.Ticket;
import dat.supportweb.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WaitingListMapper {
    
    public static void createTicket(Ticket ticket ) throws DatabaseException  {
    
     try 
     {
        Connection con = Connector.connection();
        String SQL = "INSERT INTO Ticket (name) VALUES (?)";
        PreparedStatement ps = con.prepareStatement( SQL, Statement.RETURN_GENERATED_KEYS );
        ps.setString( 1, ticket.getStudent().getName() );
        ps.executeUpdate();
        ResultSet ids = ps.getGeneratedKeys();
        ids.next();
        int id = ids.getInt( 1 );
        ticket.setId(id);
        
     } 
        catch ( SQLException | ClassNotFoundException ex ) 
     {
        throw new DatabaseException( ex.getMessage() );
     }
     
    }
    
    public static List<Ticket> getAllTickets() throws DatabaseException  {
        List<Ticket> ticketList = new ArrayList<>();
        try 
        {
            Connection con = Connector.connection();
            String SQL = "SELECT * FROM ticket";
            PreparedStatement ps = con.prepareStatement(SQL);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) 
            {
                String name = rs.getString("name");
                ticketList.add(new Ticket(new Student(name)));
            }
           
        } 
        catch ( SQLException | ClassNotFoundException ex ) 
        {
            throw new DatabaseException( ex.getMessage() );
        }
        return ticketList;
    }
    
    public static void removeFirstTicket() throws DatabaseException  {
        try 
        {
            Connection con = Connector.connection();
            String SQL = "DELETE FROM ticket LIMIT 1";
            PreparedStatement ps = con.prepareStatement(SQL);

            int rowsAffected = ps.executeUpdate();   
        } 
        catch ( SQLException | ClassNotFoundException ex ) 
        {
            throw new DatabaseException( ex.getMessage() );
        }
    }
   
}
