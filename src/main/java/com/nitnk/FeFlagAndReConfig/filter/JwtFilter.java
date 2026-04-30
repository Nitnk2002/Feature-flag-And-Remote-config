package com.nitnk.FeFlagAndReConfig.filter;

import com.nitnk.FeFlagAndReConfig.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return; // CRITICAL: Stop the rest of the filter from running!
        }
        String authorizationHeader  = request.getHeader ("Authorization");
        String username = null;
        String jwt = null;
        if(authorizationHeader != null && authorizationHeader.startsWith ("Bearer ")){
            jwt = authorizationHeader.substring (7).replaceAll("\\s+", "");
            try {
                // Your existing logic to extract the username
                username = jwtUtil.extractUsername(jwt);

            } catch (ExpiredJwtException e) {
                // 🔥 THE FIX: Explicitly handle the expired token and send CORS headers!
                System.out.println("JWT Token has expired");

                // 1. Allow React to read this error
                response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); // Change if your React port is different
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

                // 2. Set status to 401 Unauthorized
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                // 3. Send a clean JSON error message
                response.getWriter().write("{\"message\": \"Session expired. Please log in again.\"}");

                // 4. STOP THE REQUEST HERE
                return;
            } catch (Exception e) {
                // Catch any other malformed token errors
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        if(username != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername (username);
            if(jwtUtil.validateToken (jwt)){
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken (userDetails,null,userDetails.getAuthorities ());
                auth.setDetails (new WebAuthenticationDetailsSource ().buildDetails (request));
                SecurityContextHolder.getContext ().setAuthentication (auth);
            }
        }
        filterChain.doFilter (request,response);
    }
}
