package springbootartacademy.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class Utilidad {
public static String getSitioUrl(HttpServletRequest request) {
	String sitioUrl = request.getRequestURL().toString();
	return sitioUrl.replace(request.getServletPath(), "");
}
@Bean
public static BCryptPasswordEncoder passwordencode()
{
	return new BCryptPasswordEncoder() ;
}
}
