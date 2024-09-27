package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chat")
public class Chat implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "message", nullable = false)
    private String message;

    @JoinColumn(name = "from_user_id", nullable = false)
    @ManyToOne
    private User from_user;

    @JoinColumn(name = "to_user_id", nullable = false)
    @ManyToOne
    private User to_user;

    @Column(name = "date_time", nullable = false)
    private Date date_time;

    @JoinColumn(name = "chat_status_id", nullable = false)
    @ManyToOne
    private ChatStatus chat_status;

    public Chat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getFrom_user() {
        return from_user;
    }

    public void setFrom_user(User from_user) {
        this.from_user = from_user;
    }

    public User getTo_user() {
        return to_user;
    }

    public void setTo_user(User to_user) {
        this.to_user = to_user;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public ChatStatus getChat_status() {
        return chat_status;
    }

    public void setChat_status(ChatStatus chat_status) {
        this.chat_status = chat_status;
    }

}
