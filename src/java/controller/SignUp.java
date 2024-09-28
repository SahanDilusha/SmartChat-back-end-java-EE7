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

@MultipartConfig
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            String mobile = req.getParameter("mobile");
            String firstName = req.getParameter("password");
            String lastName = req.getParameter("first_name");
            String password = req.getParameter("last_name");

            Part image = req.getPart("avatarImage");

            String serverPath = req.getServletContext().getRealPath("");

            String avatarImagePath = serverPath + File.separator + "avatar_image" + File.separator + mobile + ".png";
            File file = new File(avatarImagePath);

            Files.copy(image.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Gson gson = new Gson();

            JsonObject responseObject = new JsonObject();
            responseObject.addProperty("message", "Client:Hello");

            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseObject));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
