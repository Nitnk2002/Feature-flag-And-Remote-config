package com.nitnk.FeFlagAndReConfig.filter;

import com.nitnk.FeFlagAndReConfig.entity.ApplicationEntity;
import com.nitnk.FeFlagAndReConfig.repository.ApplicationRepository;
import com.nitnk.FeFlagAndReConfig.services.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!request.getRequestURI ().startsWith ("/api/client")){
            filterChain.doFilter (request,response);
            return;
        }

        String apiKey = request.getHeader ("X-API-KEY");

        if(apiKey == null || apiKey.isEmpty ()){
            response.setStatus (HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter ().write ("Missing X-API-KEY Header");
            return;
        }
        ApplicationEntity redisAppEntity = redisService.get ("API-KEY-"+apiKey,ApplicationEntity.class);
        if(redisAppEntity != null){
            request.setAttribute ("applicationId", redisAppEntity.getId ());
        }else {
            ApplicationEntity app = applicationRepository.findByApiKey (apiKey);
            if (app == null) {
                response.setStatus (HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter ().write ("Invalid API key");
                return;
            }
            redisService.set ("API-KEY-" + app.getApiKey (), app, 86400L);
            request.setAttribute ("applicationId", app.getId ());
        }
        filterChain.doFilter (request,response);
    }
}
