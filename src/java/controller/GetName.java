package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "GetName", urlPatterns = {"/GetName"})
public class GetName extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("leters", "😊");
        
        try {

            String mobile = req.getParameter("mobile");

            Session session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("mobile", mobile));

            if (!criteria.list().isEmpty()) {

                User user = (User) criteria.uniqueResult();

                String leters = user.getFirst_name().charAt(0) + "" + user.getLast_name().charAt(0);

                responseObject.addProperty("leters", leters);

            } 

            session.close();
            
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseObject));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
