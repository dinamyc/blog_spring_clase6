package org.upiita.spring.controladores;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.upiita.spring.dao.UsuarioDAO;
import org.upiita.spring.entidades.Usuario;
import org.upiita.spring.formas.UsuarioForma;

/* --------------------- SOLUCIONES EJERCICIOS ------------------*/

@Controller
public class UsuarioControlador {

	@Autowired
	private UsuarioDAO usuarioDAO;

	@RequestMapping(value = "/usuario/{usuarioId:[0-9]+}")
	public String muestraUsuario(@PathVariable Integer usuarioId, Model modelo) {

		System.out.println("mostrando /usuario/" + usuarioId);

		Usuario usuario = usuarioDAO.buscaPorId(usuarioId);
		modelo.addAttribute("usuario", usuario);

		return "usuario";
	}

	@RequestMapping(value = "/usuario/{usuarioId:[0-9]+}/editar")
	public String mostrarEditor(
			@RequestParam(required = false) Boolean actualizado,
			@PathVariable Integer usuarioId, Model modelo) {

		System.out.println("modelo usuario:" + modelo);

		// SOLUCION EJERCICIO VALIDACIONES
		if (!modelo.containsAttribute("usuario")) {

			System.out.println("mostrando el editor para el usuario");
			Usuario usuario = usuarioDAO.buscaPorId(usuarioId);
			modelo.addAttribute("usuario", usuario);
			modelo.addAttribute("actualizado", actualizado);
		}

		return "usuario_editar";
	}

	@RequestMapping(value = "/usuario/guardar", method = RequestMethod.POST)
	// SOLUCION EJERCICIO VALIDACIONES
	public String guardarUsuario(
			@Valid @ModelAttribute("usuario") UsuarioForma forma,
			BindingResult validacion, RedirectAttributes atributos) {

		String rutaRedirect = null;

		System.out.println("guardando usuario, id:" + forma.getId()
				+ ", nombre:" + forma.getNombre() + ", email:"
				+ forma.getEmail());

		if (validacion.hasErrors()) {

			//SI HAY ERRORES, REDIRECCIONAMOS AL EDITOR DE USUARIOS
			rutaRedirect = "redirect:/usuario/" + forma.getId() + "/editar";
			atributos.addFlashAttribute("usuario", forma);
			atributos.addFlashAttribute("org.springframework.validation.BindingResult.usuario",validacion);

		} else {

			// creamos un objeto usuario con los datos que nos pasan,
			// posteriormente guardamos dicho objeto con hibernate en la tabla
			// usuaurios
			Usuario usuario = new Usuario();
			usuario.setId(forma.getId());
			usuario.setNombre(forma.getNombre());
			usuario.setEmail(forma.getEmail());
			usuario.setPassword(forma.getPassword());

			Integer idUsuarioDB = usuarioDAO.guardar(usuario);
			// NOTA: OBSERVE QUE USANDO REDIRECT ATTRIBUTES PODRIAMOS REMOVER EL
			// QUERY STRING|
			rutaRedirect = "redirect:/usuario/" + idUsuarioDB + "/editar?actualizado=true";

		}

		// usamos el patron post-redirect-get
		return rutaRedirect;

	}

}
