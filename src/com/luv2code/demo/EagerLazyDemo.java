package com.luv2code.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.demo.entity.Course;
import com.luv2code.demo.entity.Instructor;
import com.luv2code.demo.entity.InstructorDetail;

public class EagerLazyDemo {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration()
										.configure("hibernate.cfg.xml")
										.addAnnotatedClass(Instructor.class)
										.addAnnotatedClass(InstructorDetail.class)
										.addAnnotatedClass(Course.class)
										.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
			Instructor instructor = session.get(Instructor.class, 1);
			
			System.out.println("\nInstructor : " + instructor);
			
			session.getTransaction().commit();
			session.close();
			
			System.out.println("\nCourses : " + instructor.getCourses());

			session.getTransaction().commit();
		} catch (Exception exp) {
			exp.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}
}