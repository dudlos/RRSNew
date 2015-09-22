package com.dudlo.reservationsystem.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dudlo.reservationsystem.model.RestTable;
import com.dudlo.reservationsystem.model.Restaurant;

@Repository
@Transactional
public class RestTableRepositoryImpl implements RestTableRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public RestTable createTable(int capacity, String tableNumber, Restaurant restaurant) {
		RestTable table = new RestTable(capacity, tableNumber, restaurant);
		entityManager.persist(table);
	
		return table;
	}

	@Override
	public RestTable findById(Long id) {
		return entityManager.find(RestTable.class, id);
	}

	@Override
	public List<RestTable> findAll() {
		return entityManager.createQuery("from RestTable", RestTable.class)
				.getResultList();
	}

	@Override
	public List<RestTable> findAllByRestaurant(Restaurant rest) {
		return entityManager.createQuery(
						"Select distinct c from RestTable c where c.restaurant = :restparam",
						RestTable.class).setParameter("restparam", rest).getResultList();
		}

}
