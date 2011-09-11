package server;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NeverReadServer {
    public void start(int port) {
        Server server = new Server(port);
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String s, HttpServletRequest request, HttpServletResponse response, int i) throws IOException, ServletException {
                response.setContentType("text/html");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("" +
                        "<html>\n" +
                        "<head>\n" +
                        "<title>neverread</title>\n" +
                        "</head>\n" +
                        "<h1>\n" +
                        "neverread\n" +
                        "</h1>\n" +
                        "<form>\n" +
                        "<input type=\"text\" name=\"url\"/>\n" +
                        "</form>\n" +
                        "<br/>\n" +
                        "<h2>Pending articles</h2>\n" +
                        "<ul>\n" +
//                        "<li>one</li>\n" +
                        "</ul>\n" +
                        "</html>"
                );
                ((Request)request).setHandled(true);
            }
        });

        try {
            server.start();
            System.out.println("Never Read Server started at http://localhost:" + port);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Never Read Server could not be started", e
            );
        }
    }

    public static void main(String[] args) {
        new NeverReadServer().start(8081);
    }
}