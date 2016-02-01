/*package com.dudlo.reservationsystem.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class MyCustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            String url = (String) request.getSession().getAttribute("url_prior_login");
            if (url != null) {

                response.sendRedirect(url);

            }
        }
        
        
        
     
    }

}
*/