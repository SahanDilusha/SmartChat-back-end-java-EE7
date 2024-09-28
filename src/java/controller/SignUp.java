package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Validations;

@MultipartConfig
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            JsonObject responseObject = new JsonObject();
            String mobile = req.getParameter("mobile");
            String firstName = req.getParameter("password");
            String lastName = req.getParameter("first_name");
            String password = req.getParameter("last_name");

            Part image = req.getPart("avatarImage");
            
            if (mobile.trim().isEmpty()) {
                
            }else if (!Validations.isMobile(mobile)) {
                
            }  else if (firstName.trim().isEmpty()) {
                
            }else if (lastName.trim().isEmpty()) {
                
            }else if (!Validations.isPasswordValid(password)) {
                
            }
            

            String serverPath = req.getServletContext().getRealPath("");

            String avatarImagePath = serverPath + File.separator + "avatar_image" + File.separator + mobile + ".png";
            File file = new File(avatarImagePath);

            Files.copy(image.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Gson gson = new Gson();

            responseObject.addProperty("message", "Client:Hello");

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseObject));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
