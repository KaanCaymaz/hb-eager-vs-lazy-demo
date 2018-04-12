package com.luv2code.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.luv2code.demo.entity.Course;
import com.luv2code.demo.entity.Instructor;
import com.luv2code.demo.entity.InstructorDetail;

public class JoinFetchDemo {
	
	public static void main(String[] args) {
		
		SessionFactory factory= new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
		try {
			
			session.beginTransaction();
			int theId=1;
			
			Query<Instructor> query= session.createQuery("select i from Instructor i "
														+"JOIN FETCH i.courses "
														+"where i.id= :theInstructorId",Instructor.class);
			
			query.setParameter("theInstructorId", theId);
			
			Instructor theInstructor = query.getSingleResult();

			session.getTransaction().commit();
			session.close();
			
			System.out.println("Instructor : "+ theInstructor);
			System.out.println("Courses : "+ theInstructor.getCourses());
		}
		catch(Exception exp) {
			exp.printStackTrace();
		}
		finally {
			session.close();
			factory.close();
		}	
	}
}
