package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.User;
import entity.UserStatus;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.HibernateUtil;
import model.Validations;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@MultipartConfig
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Gson gson = new Gson();
            JsonObject responseObject = new JsonObject();
            responseObject.addProperty("success", false);

            String mobile = req.getParameter("mobile");
            String password = req.getParameter("password");
            String firstName = req.getParameter("first_name");
            String lastName = req.getParameter("last_name");

            System.out.println(password);

            Part avatarImage = req.getPart("avatarImage");

            if (mobile.trim().isEmpty()) {
                responseObject.addProperty("message", "Please fill  your Mobile Number");
            } else if (!Validations.isMobile(mobile)) {
                responseObject.addProperty("message", "Invalid mobile number");
            } else if (firstName.trim().isEmpty()) {
                responseObject.addProperty("message", "Please fill  your first name");
            } else if (lastName.trim().isEmpty()) {
                responseObject.addProperty("message", "Please fill  your last name");
            } else if (!Validations.isPasswordValid(password)) {
                responseObject.addProperty("message", "Invalid password");
            } else {

                Session session = HibernateUtil.getSessionFactory().openSession();

                Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("mobile", mobile));

                if (!criteria.list().isEmpty()) {
                    responseObject.addProperty("message", "Mobile number already Used.");
                } else {

                    User user = new User();

                    user.setFirst_name(firstName);
                    user.setLast_name(lastName);
                    user.setMobile(mobile);
                    user.setPassword(password);
                    user.setRegistered_date_time(new Date());

                    UserStatus userStatus = (UserStatus) session.get(UserStatus.class, 2);

                    user.setUserStatus(userStatus);

                    session.save(user);
                    session.beginTransaction().commit();

                    if (avatarImage != null) {
                        String serverPath = req.getServletContext().getRealPath("");

                        String avatarImagePath = serverPath + File.separator + "avatar_image" + File.separator + mobile + ".png";
                        File file = new File(avatarImagePath);

                        Files.copy(avatarImage.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    }
                    
                    session.close();

                    responseObject.addProperty("success", true);
                    responseObject.addProperty("message", "Registration Complete");
                }
            }

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseObject));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
