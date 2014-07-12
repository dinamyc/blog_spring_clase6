package org.upiita.spring.testing;


//ESTO ES UN STATIC IMPORT
import static org.springframework.util.Assert.notEmpty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.upiita.spring.dao.PostDAO;
import org.upiita.spring.entidades.Post;

public class PostDAOTest {
	
	//HAY 2 TIPOS DE PRUEBAS:
	//  PRUEBA UNITARIA (	
	//  PRUEBA DE INTEGRACION (BD o CHECAR QUE LA APLICACION WEB FUNCIONA)

	private static ApplicationContext contexto;
	
	private static PostDAO postDAO;

	@BeforeClass
	//ESTE METODO SE EJECUTA ANTES DE TODOS LOS TESTS
	public static void inicializar() {
		
		contexto = new ClassPathXmlApplicationContext("dao-context-testing.xml");
		postDAO = (PostDAO) contexto.getBean("postDAO");
	}
	
	//EN JUNIT NO TIENEN GARANTIA DE QUE LOS TESTS SE EJECUTAN
	//EN ORDEN, SOLO DE QUE SE EJECUTAN TODOS
	//CUANDO HAGAN UN TEST, EVITEN QUE EL RESULTADO DE UN TEST
	//INFLUYA EN OTRO TEST
	@Test
	public void buscaPostTest(){
		
		Post post = postDAO.buscaPorId(1);		
		
		System.out.println("post titulo:" + post.getTitulo());
		System.out.println("comentarios:" + post.getComentarios());
		System.out.println("categorias:" + post.getCategorias());
		
		Assert.assertNotNull("El metodo para buscar post regresa datos vacios",post);	
	}
	
	@Test
	public void guardarPostTest(){
		
		//ESTA ES LA ENTIDAD  DE PRUEBA A GUARDAR
		Post post = new Post();
		//post.setId(3);
		post.setTitulo("titulo test");
		post.setContenido("contentido test");
		
		Date fechaActual = new Date();
		post.setFechaCreacion(fechaActual);
		
		//GUARDAMOS EL POST Y OBTENEMOS SU ID QUE LE ASIGNO
		//EN LA BASE
		Integer postIdBD = postDAO.guardar(post);
		
		Post postBD = postDAO.buscaPorId(postIdBD);
		Assert.assertNotNull(postBD);
		
		//PRIMER PARAMETRO = ES EL VALOR QUE ESPERAN
		//SEGUNDO PARAMETRO = ES EL VALOR OBTENIDO
		Assert.assertNotNull(postBD.getId());
		Assert.assertEquals(post.getId(), postBD.getId());
		
		//REVISAR PARAMETRO TITULO
		System.out.println("titulo:" + postBD.getTitulo());
		Assert.assertNotNull(postBD.getTitulo());
		Assert.assertEquals(post.getTitulo(), postBD.getTitulo());
		
		Assert.assertNotNull(postBD.getContenido());
		Assert.assertEquals(post.getContenido(), postBD.getContenido());
		
		//TENER CUIDADO CUANDO COMPARAN FECHAS!!
		SimpleDateFormat formato =  new SimpleDateFormat("yyyy-MM-dd");		
		Assert.assertEquals(formato.format(post.getFechaCreacion()), formato.format(postBD.getFechaCreacion()));				
		
	}
	
	@Test
	public void buscaPorTituloTest(){
		
		List<Post> postsEncontrados = postDAO.buscaPorTitulo("post");
		
		System.out.println("posts encontrados:" + postsEncontrados);
		//notEmpty REVISA QUE LA COLECCION NO SEA NULA Y QUE NO VENGA VACIA
		//ES DECIR QUE TENGA UNO O MAS ELEMENTOS
		notEmpty(postsEncontrados);		
		
	}

}
