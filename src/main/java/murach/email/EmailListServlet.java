package murach.email;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import murach.business.User;

public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String url = "/index.html";

        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";  // default action
        }

        // perform action and set URL to appropriate page
        if (action.equals("join")) {
            url = "/index.html";    // the "join" page
        }
        else if (action.equals("add")) {
            // get parameters from the request (Bao gồm các trường mới từ form Survey)
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String dateOfBirth = request.getParameter("dateOfBirth");
            String source = request.getParameter("source");

            // Đối với checkbox, nếu người dùng không tích chọn thì giá trị nhận được sẽ là null
            String announcements = request.getParameter("announcements");
            String offers = request.getParameter("offers");

            String contactMethod = request.getParameter("contact_method");

            // Xử lý giá trị checkbox cho thân thiện nếu cần
            String receiveAnnouncements = (announcements != null) ? "Yes" : "No";
            String receiveOffers = (offers != null) ? "Yes" : "No";

            // store data in User object and save User object in db
            // (Lưu ý: Bạn có thể cập nhật thêm các thuộc tính này vào class User trong package murach.business nếu muốn lưu trữ đầy đủ)
            User user = new User(firstName, lastName, email);
//          UserDB.insert(user);

            request.setAttribute("user", user);
            request.setAttribute("dateOfBirth", dateOfBirth);
            request.setAttribute("source", source);
            request.setAttribute("receiveAnnouncements", receiveAnnouncements);
            request.setAttribute("receiveOffers", receiveOffers);
            request.setAttribute("contactMethod", contactMethod);

            url = "/thanks.jsp";    // the "thanks" page
        }

        // forward request and response objects to specified URL
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}