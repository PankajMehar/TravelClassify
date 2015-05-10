import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import cc.mallet.classify.Classifier;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

        //if (req.getRequestURI().contains("/new-job?")) {
            String userId = req.getParameter("id");;
            System.out.println(userId);
            ClassificationRunner R1 = new ClassificationRunner(userId);
            List<Map.Entry<String, Double>> list = R1.runClassification(); // at the moment run asynchronously
            resp.getWriter().print("400. Successful. User posts have been analyzed. Userid: " + userId + "\n");
            for (Map.Entry<String, Double> entry : list) {
                resp.getWriter().print(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        //}
        //else {
        //    showHome(req,resp);
        //}
    }

    private void showHome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("Hello from Java!");
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new Main()), "/*");
        server.start();
        server.join();
    }
}
