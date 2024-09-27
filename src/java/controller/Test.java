package controller;

import entity.ChatStatus;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Session;

@WebServlet(name = "Test", urlPatterns = {"/Test"})
public class Test extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Session session = HibernateUtil.getSessionFactory().openSession();

            ChatStatus chat_Status = new ChatStatus();

            chat_Status.setName("Seen");

            session.save(chat_Status);

            session.beginTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
