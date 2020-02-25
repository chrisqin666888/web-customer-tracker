package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;

@Repository
@Transactional
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {

		Session currentSession = sessionFactory.getCurrentSession();

		Query<Customer> query = currentSession.createQuery("FROM Customer ORDER by lastName", Customer.class);

		List<Customer> customers = query.getResultList();

		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// save the customer
		currentSession.saveOrUpdate(customer);

	}

	@Override
	public Customer getCustomer(int id) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve / read from database using the primary key
		Customer customer = currentSession.get(Customer.class, id);

		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// delete object with primary key
		Query query = currentSession.createQuery("DELETE FROM Customer WHERE id=:customerId");

		query.setParameter("customerId", id);

		query.executeUpdate();

	}

	@Override
	public List<Customer> searchCustomers(String searchName) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		Query query = null;

		//
		// only search by name if the searchName is not empty
		//

		if (searchName != null && searchName.trim().length() > 0) {
			// search for first name or last name .. case sensitive
			query = currentSession.createQuery(
					"FROM Customer WHERE LOWER(firstName) " + "like :theName " + "OR LOWER(lastName) like :theName",
					Customer.class);
			query.setParameter("theName", "%" + searchName.toLowerCase() + "%");

		} else {
			query = currentSession.createQuery("FROM Customer", Customer.class);
		}

		List<Customer> customers = query.getResultList();

		return customers;
	}

}
