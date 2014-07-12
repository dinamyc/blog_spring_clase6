package org.upiita.spring.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.upiita.spring.formas.FormaSesion;

@Controller
//@Scope("request")
public class LoginControlador {
	
	@Autowired
	private FormaSesion formaSesion;

	@RequestMapping(value="/login")
	
	public String login(Boolean errorLogin, Model modelo,HttpSession sesion){
		
		System.out.println("LOGIN ENTRANDO");
		modelo.addAttribute("errorLogin",errorLogin);
		
		formaSesion.setItemsComprados(100);
		
		sesion.setAttribute("variable", 10);
		
		return "login";
	}
	
	@RequestMapping(value="/error-403")
	public String error403(Authentication autenticacion, Model modelo){
		
		//name para el nombre del usuario (en nuestro caso email)
		System.out.println("NOMBRE:" + autenticacion.getName());
		//credentials para el password
		System.out.println("PASSWORD:" + autenticacion.getCredentials());
		//authorities para los roles
		System.out.println("ROLES:" + autenticacion.getAuthorities());

		modelo.addAttribute("email",autenticacion.getName());
		
		return "error-403";
	}

}
