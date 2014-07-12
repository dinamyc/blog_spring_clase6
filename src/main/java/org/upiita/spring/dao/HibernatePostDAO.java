package org.upiita.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.upiita.spring.entidades.Post;

@Component(value="postDAO")
@Transactional
public class HibernatePostDAO implements PostDAO {
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see org.upiita.spring.dao.PostDAO#buscaPorId(java.lang.Integer)
	 */
	@Override
	public Post buscaPorId(Integer id){
		Post elPost = null;
		
		//TRANSACCION PROGRAMATICA		
		//Session sesion = sessionFactory.openSession();		
		//sesion.beginTransaction();
		
		Session sesion = sessionFactory.getCurrentSession();
		
		System.out.println("BUSCANDO post con id:" + id);
		
		//UNA VEZ INICIADA LA TRANSACCION PODEMOS HACER CONSULTAS
		//O MODIFICAR LA BASE
		//SI HIBERNATE ENCUENTRA EL RENGLON, EL OBJETO SE RELLENA AUTOMATICAENTE
		//SI NO LO ENCUENTRA, HIBERNATE REGRESA NULL
		elPost = (Post) sesion.get(Post.class,id);
		
		//LE INDICA A HIBERNATE QUE QUEREMOS SUS COMENTARIOS ASOCIADOS
		//Hibernate.initialize(elPost.getComentarios());
		Hibernate.initialize(elPost.getCategorias());
		
		//TERMINADO TODO LO QUE NECESITAMOS HACER EN LA BASE
		//CERRAMOS LA SESION DE HIBERNATE
		//sesion.close();
				
		//SIMULAMOS LA CONSULTA  AL ABASE
		//NOS REGRESO ESTE OBJETO
		/*
		elPost = new Post();
		elPost.setTitulo("titulo prueba");
		elPost.setContenido("contenido prueba");
		*/		
		
		return elPost;
	}
	
	/* (non-Javadoc)
	 * @see org.upiita.spring.dao.PostDAO#guardar(org.upiita.spring.entidades.Post)
	 */
	@Override
	public Integer guardar(Post post){
		
		//Session sesion = sessionFactory.openSession();		
		//sesion.beginTransaction();
		
		Session sesion = sessionFactory.getCurrentSession();
		
		//UNA VEZ INICIADA LA TRANSACCION PODEMOS HACER CONSULTAS
		//O MODIFICAR LA BASE
		//SI HIBERNATE ENCUENTRA EL RENGLON, EL OBJETO SE RELLENA AUTOMATICAENTE
		//SI NO LO ENCUENTRA, HIBERNATE REGRESA NULL
		//ESTE METODO NO REGRESA NADA
		// PERO!!!
		// ACUTALIZA EL OBJETO QUE LE PASAN 
		// EN PARTICULAR EL ID
		sesion.saveOrUpdate(post);
		
		//TERMINADO TODO LO QUE NECESITAMOS HACER EN LA BASE
		//CERRAMOS LA SESION DE HIBERNATE
		//SI CAMBIAN DATOS HACER EL COMMIT!!!
		//SI NO SE VEN LOS CAMBIOS
		//sesion.getTransaction().commit();
		//sesion.close();		
		
		return post.getId();
	}

	
	@Override
	//LISTAS PUEDEN DE 2 TIPOS : ArrayList LinkedList => List
	public List<Post> buscaPorTitulo(String titulo){
		
		Session sesion = sessionFactory.getCurrentSession();
		//A PARTIR DE LA SESION CREAMOS EL CRITERIO
		//USANDO EL .CLASS QUE REPRESENTA A LA TABLA
		Criteria criterio = sesion.createCriteria(Post.class);
		//AGREGARMOS CRITERIOS BUSQUEDA
		//PRIMER ARGUMENTO DEL LIKE (EN GENERAL DE LA RESTRICCION)
		// ES LA PROPIEDAD DE LA ENTIDAD A BUSCAR
		criterio.add(Restrictions.like("titulo","%" + titulo + "%"));
		
		//LE DECIMOS A HIBERNATE QUE BUSQUE Y OBTENGA UNA LISTA
		//DE RESULTADOS
		//@SuppressWarnings("unchecked")
		List<Post> postsEncontrados = criterio.list();
		
		return postsEncontrados;		
	}
	
	
		
	//LISTAS PUEDEN DE 2 TIPOS : ArrayList LinkedList => List
	@Override
	public List<Post> buscaDiferentesDeTitulo(String titulo){
		
		Session sesion = sessionFactory.getCurrentSession();
		//A PARTIR DE LA SESION CREAMOS EL CRITERIO
		//USANDO EL .CLASS QUE REPRESENTA A LA TABLA
		Criteria criterio = sesion.createCriteria(Post.class);
		//AGREGARMOS CRITERIOS BUSQUEDA
		//PRIMER ARGUMENTO DEL LIKE (EN GENERAL DE LA RESTRICCION)
		// ES LA PROPIEDAD DE LA ENTIDAD BUSCAR
		criterio.add(Restrictions.not(Restrictions.like("titulo","%" + titulo + "%")));
		
		//LE DECIMOS A HIBERNATE QUE BUSQUE Y OBTENGA UNA LISTA
		//DE RESULTADOS
		//@SuppressWarnings("unchecked")
		List<Post> postsEncontrados = criterio.list();
		
		return postsEncontrados;		
	}

}






