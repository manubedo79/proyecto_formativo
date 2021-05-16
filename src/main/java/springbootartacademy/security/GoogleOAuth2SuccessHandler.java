package springbootartacademy.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import springbootartacademy.models.dao.IRolesDao;
import springbootartacademy.models.dao.IUsuariosDao;
import springbootartacademy.models.entity.Roles;
import springbootartacademy.models.entity.Usuarios;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    IUsuariosDao userRepository;

    @Autowired
    IRolesDao roleRepository;

    

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttributes().get("email").toString();
        if(userRepository.getFindByCorreo(email).isPresent()){

        }else {
           Usuarios user = new Usuarios();
            
            user.setCorreo(email);
            user.setContraseña(null);
            Roles rol = roleRepository.findByNombre("CLIENTE");
    		Set<Roles> listaroles = new HashSet<Roles> (Arrays.asList(rol));
            
            user.setRoles(listaroles);
            userRepository.save(user);
        }

        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
    }



}
