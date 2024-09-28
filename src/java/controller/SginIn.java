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
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "SginIn", urlPatterns = {"/SginIn"})
public class SginIn extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try {
            Gson gson = new Gson();
            JsonObject responseObject = new JsonObject();
            responseObject.addProperty("success", false);
            
            JsonObject requestObject = gson.fromJson(req.getReader(), JsonObject.class);
            
            String mobile = requestObject.get("mobile").getAsString();
            String password = requestObject.get("password").getAsString();
            
            if (mobile.trim().isEmpty()) {
                responseObject.addProperty("message", "Please fill  your Mobile Number");
            } else if (!Validations.isMobile(mobile)) {
                responseObject.addProperty("message", "Invalid mobile number");
            } else if (!Validations.isPasswordValid(password)) {
                responseObject.addProperty("message", "Invalid password");
            } else {
                Session session = HibernateUtil.getSessionFactory().openSession();
                Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("mobile", mobile)).add(Restrictions.eq("password", password));
                
                if (!criteria.list().isEmpty()) {
                    
                    User user = (User) criteria.uniqueResult();
                    
                    responseObject.addProperty("success", true);
                    
                     responseObject.addProperty("message", "Sigin Complete");
                    responseObject.add("user", gson.toJsonTree(user));
                    
                } else {
                    responseObject.addProperty("message", "Invalid Credentials!");
                    
                }
                
            }
            
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
