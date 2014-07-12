package org.upiita.spring.controladores;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.upiita.spring.dao.PostDAO;
import org.upiita.spring.entidades.Post;
import org.upiita.spring.formas.FormaSesion;
import org.upiita.spring.formas.PostForma;

//ESTO ES ANNOTATION Y LE DICE A SPRING
//QUE ESTA CLASE ES UN CONTROLADOR
@Controller
public class BlogControlador {

	@Autowired
	private FormaSesion formaSesion;

	@Autowired
	@Qualifier(value = "postDAO")
	private PostDAO postDAO;

	// EXPRESION REGULAR:
	// [] = DEFINIR RANGOS
	// + = AL MENOS UN ELEMENTO DEL ANTERIOR EXPRESION
	@RequestMapping(value = "/blog/{postId:[0-9]+}")
	public String mostrarInicio(@PathVariable(value = "postId") Integer postId,
			@RequestParam(required = false) Integer limite,
			@RequestParam(required = false) String nombre, Model modelo) {

		System.out.println("limite:" + limite);
		System.out.println("despachando url /blog/" + postId);

		Post elPost = postDAO.buscaPorId(postId);

		modelo.addAttribute("post", elPost);
		modelo.addAttribute("nombre", nombre);
		
		System.out.println("ITEMS COMPRADOS:" + formaSesion.getItemsComprados());

		return "post";
	}

	// POR DEFAULT, LAS URLS SON DEL TIPO GET
	@RequestMapping(value = "/blog/{postId:[0-9]+}/editar")
	public String mostrarEditor(
			@RequestParam(required = false) Boolean actualizado,
			@PathVariable Integer postId, Model modelo) {
		
		System.out.println("MODELO:" + modelo);
		
		//SI HUBO ERRORES AL GUARDAR
		if(modelo.containsAttribute("post")){
			
			System.out.println("hubo errores,MODELO:" + modelo);
			
			
		} else {
			//SI TODO SALIO BIEN O SI ES LA PRIMERA VEZ
			//QUE ENTRA

			Post post = postDAO.buscaPorId(postId);
			modelo.addAttribute("post", post);
			modelo.addAttribute("actualizado", actualizado);		
		}


		return "post_editar";
	}

	@RequestMapping(value = "/blog/guardar", method = RequestMethod.POST)
	public String guardarPost(@Valid @ModelAttribute("post") PostForma forma,
			BindingResult validacion, RedirectAttributes atributos) {
		
		String urlRedirect = null;

		System.out.println("id:" + forma.getId() + "titulo:"
				+ forma.getTitulo() + ", contenido:" + forma.getContenido());
		// @TODO: MAS ADELANTE GUARDAMOS ESTOS EN LA BASE DE DATOS

		if (validacion.hasErrors()) {
			
			//-- ESTO ESTA DISPONIBLE DESDE LA VERSION 3.1 DE SPRING
			// ES PARA PRESERVAR DATOS EN EL REDIRECT
			atributos.addFlashAttribute("post", forma);
			atributos.addFlashAttribute("org.springframework.validation.BindingResult.post",validacion);
			
			urlRedirect = "redirect:/blog/" + forma.getId() + "/editar";

		} else {
			//SI NO HAY ERRORES, GUARDAMOS EL POST

			Post post = new Post();
			post.setId(forma.getId());
			post.setTitulo(forma.getTitulo());
			post.setContenido(forma.getContenido());

			Integer postIdGuardado = postDAO.guardar(post);

			// PATRON
			// POST - REDIRECT - GET
			urlRedirect =  "redirect:/blog/" + postIdGuardado
					+ "/editar?actualizado=true";

		}
		
		return urlRedirect;
	}

}
