package com.KayraAtalay.jwt;

import com.KayraAtalay.shared.exception.BaseException;
import com.KayraAtalay.shared.exception.ErrorMessage;
import com.KayraAtalay.shared.exception.MessageType;
import com.KayraAtalay.shared.jwt.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {

        String header = request.getHeader("Authorization");

        if(header == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token;
        String username;

        token = header.substring(7);

        try {
            username = jwtService.getUsernameByToken(token);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if(userDetails != null && jwtService.isTokenValid(token) == true) {
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(userDetails);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }


        }
        catch (ExpiredJwtException e) {
            throw new BaseException(new ErrorMessage(MessageType.TOKEN_EXPIRED,  e.getMessage()));
        }
        catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,  e.getMessage()));
        }

        filterChain.doFilter(request, response);

    }

}
