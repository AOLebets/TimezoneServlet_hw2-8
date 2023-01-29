package com.timezone_servlet;

import java.io.*;
import java.sql.Date;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class TimeServlet
 */
@WebServlet
public class TimeServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String timezoneOffset = request.getParameter("timezone");
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Time in your timezone right now!</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Time in your timezone right now!</h1>");

        try {
            if (timezoneOffset != null) {
                ZoneOffset offset = ZoneOffset.of(timezoneOffset);
                ZoneId zoneid = ZoneId.ofOffset("UTC", offset);
                ZonedDateTime zdt = ZonedDateTime.now(zoneid);

                out.println("<h2>"+ DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(zdt) + " UTC" + offset + "</h2>");
                out.println("<h2>Right now displaying in timezone specified above!</h2>");
                out.println("<h2>"
                        + "If you want to change your "
                        + "timezone, write it in the window below "
                        + "in format \"+08:00\", \"-11:00\"!</h2>");

                out.println("Current timezone:");
                out.println(" = UTC" + timezoneOffset + "<br>");

                out.println("<P>");
                out.print("<form>");
                out.println("Your timezone: UTC");
                out.println("<input type=text size=6 id=timezone name=timezone>");
                out.println("<br>");
                out.println("<input type=submit>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");

            } else {

                Instant instant = Instant.now();
                LocalDateTime ldt = instant.atZone(ZoneOffset.UTC).toLocalDateTime();


                out.println("<h2>"+ DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(ldt) + " UTC</h2>");
                out.println("<h2>Right now displaying in timezone specified above!</h2>");
                out.println("<h2>"
                        + "By default, it's UTC. If you want to specify your "
                        + "timezone, write it in the window below "
                        + "in format \"+08:00\", \"-11:00\"!</h2>");

                out.println("Current timezone:");
                out.println(" = UTC <br>");

                out.println("<P>");
                out.print("<form>");
                out.println("Your timezone: UTC");
                out.println("<input type=text size=6 id=timezone name=timezone>");
                out.println("<br>");
                out.println("<input type=submit>");
                out.println("</form>");
                out.println("</body>");
                out.println("</html>");
                
            }
        } catch (Exception e) {
        
        	
            out.println("Filter didn't work");
            System.out.println(e + "\nFilter didn't work");
        	
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);
    }
}

