
package dat.supportweb.entities;

public class Ticket {
    
    private int id;
    private Student student;

    public Ticket(Student student) {
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    
    
    
}
