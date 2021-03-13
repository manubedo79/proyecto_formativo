package springbootartacademy.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import springbootartacademy.models.entity.AuthenticationProvider;
import springbootartacademy.models.entity.Usuarios;
import springbootartacademy.models.service.IUsuariosService;

@Component
public class OAuth2LoginSuccessHandler  extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private IUsuariosService iusuarser;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UsuarioOAuth2Per auth2Per = (UsuarioOAuth2Per) authentication.getPrincipal();
		String email = auth2Per.getEmail();
		String name = auth2Per.getName();
		Usuarios usuarios = iusuarser.getUsuariosByCorreo(email);
		
		
		if(usuarios == null) {
			iusuarser.CreateNuevoUsuarioAfterOAuthLoginSuccess(name, email, AuthenticationProvider.GOOGLE);
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
