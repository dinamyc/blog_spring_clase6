package org.upiita.spring.testing;

import static org.springframework.util.Assert.notEmpty;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.upiita.spring.dao.UsuarioDAO;
import org.upiita.spring.entidades.DatosUsuario;
import org.upiita.spring.entidades.Usuario;

public class UsuarioDAOTest {

	// HAY 2 TIPOS DE PRUEBAS:
	// PRUEBA UNITARIA (
	// PRUEBA DE INTEGRACION (BD o CHECAR QUE LA APLICACION WEB FUNCIONA)

	private static ApplicationContext contexto;

	private static UsuarioDAO usuarioDAO;

	@BeforeClass
	public static void inicializar() {

		contexto = new ClassPathXmlApplicationContext("dao-context-testing.xml");
		usuarioDAO = (UsuarioDAO) contexto.getBean("usuarioDAO");
	}

	/*----------- SOLUCION EJERCICIO --------------*/
	@Test
	public void guardarUsuarioTest() {

		Usuario usuario = new Usuario();
		// no ponemos un id por que la secuencia generadora
		// ya esta poniendo el id
		usuario.setEmail("email@test.com");
		usuario.setNombre("usuario para el test");
		usuario.setPassword("12345");

		// Guardamos el usuario
		Integer idUsuarioBD = usuarioDAO.guardar(usuario);

		// obtenemos el usuario guardado y comparamos que se haya
		// guardado correctamente
		Usuario usuarioBD = usuarioDAO.buscaPorId(idUsuarioBD);
		Assert.assertNotNull("no se guardo el usuario!", usuarioBD);

		Assert.assertEquals(idUsuarioBD, usuarioBD.getId());

		Assert.assertNotNull(usuarioBD.getNombre());
		Assert.assertEquals(usuario.getNombre(), usuarioBD.getNombre());

		Assert.assertNotNull(usuarioBD.getEmail());
		Assert.assertEquals(usuario.getEmail(), usuarioBD.getEmail());

		Assert.assertNotNull(usuarioBD.getPassword());
		Assert.assertEquals(usuario.getPassword(), usuarioBD.getPassword());

	}

	@Test
	public void mapeoUsuarioDatosUsuarioTest() {

		Usuario usuarioBD = usuarioDAO.buscaPorId(1);
		System.out.println("usuario nombre:" + usuarioBD.getNombre());

		System.out.println("datos usuario:" + usuarioBD.getDatosUsuario());

		DatosUsuario datosUsuario = usuarioBD.getDatosUsuario();
		System.out.println("usuario nombre(desde datos usuario):"
				+ datosUsuario.getUsuario().getNombre());

	}

	// ----------------SOLUCION EJERCICIO 1-N ------------------
	@Test
	public void mapeoUsuarioPosts() {

		Usuario usuarioBD = usuarioDAO.buscaPorId(1);
		System.out.println("usuario nombre:" + usuarioBD.getNombre());

		Assert.assertNotNull(usuarioBD);
		notEmpty(usuarioBD.getPosts());

		System.out.println("usuario posts:" + usuarioBD.getPosts());
	}

	// ----------------SOLUCION EJERCICIO N-N ------------------
	@Test
	public void mapeoUsuariosDepartamentos() {

		Usuario usuarioBD = usuarioDAO.buscaPorId(1);
		System.out.println("usuario nombre:" + usuarioBD.getNombre());

		Assert.assertNotNull(usuarioBD);
		notEmpty(usuarioBD.getDepartamentos());
		
		System.out.println("departamentos en los que trabaja el usuario:" + usuarioBD.getDepartamentos() );

	}

}
