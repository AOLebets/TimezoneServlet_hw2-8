package com.timezone_servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.imageio.metadata.IIOMetadataFormatImpl;

/**
 * Servlet Filter implementation class TimezoneValidateFilter
 */
@WebFilter("/time?timezone=")
public class TimezoneValidateFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public TimezoneValidateFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
    @Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse res = (HttpServletResponse) response;
	
	PrintWriter out = res.getWriter();
	
	try {
		boolean isRightFormat = false;
		boolean isValid = false;
		
		String tzStr = req.getParameter("timezone");
		TimeZone timezone = TimeZone.getTimeZone(req.getParameter("timezone"));
		try {
		
		System.out.println("I'm starting filtering!");
		
		try {
			if (Pattern.matches("[\\+\\-]\\d{2}\\:\\d{2}", tzStr)) {
	            isRightFormat = true;
			} else {
				System.out.println("Invalid timezone on regex datetime catch");
			}
		} catch (java.time.DateTimeException e) {
				out.println("Invalid timezone on regex datetime catch");
		}
		
		if (isRightFormat == true) {
		String[] validIDs = TimeZone.getAvailableIDs();
		
		for (int i = 0; i < validIDs.length; i++) {
		    if (validIDs[i].equals(timezone.getID())) {
		    	isValid = true;
		} else if (i == validIDs.length) {
		    	isValid = false;
		    }
		}
		}
		if (isValid == true) {
		  chain.doFilter(request, response);
	      System.out.println("Filter worked! isValid == true");
		  } else {
		  res.setStatus(400);  
		  out.println("Invalid timezone, status: " + res.getStatus());
		  System.out.println("Filter worked, status: " + res.getStatus());
	      }
		
		      
		     } catch (java.time.DateTimeException e) {
				out.println("Invalid timezone on general datetime catch");
		     }
	      } catch (NullPointerException e) {
			chain.doFilter(request, response);
	      }
	
   }

}
