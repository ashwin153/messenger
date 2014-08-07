package com.ashwin.messenger.server.repository;

public interface GenericRepository<E, K>{

	E add(E entity);
	E update(E entity);
	void remove(E entity);
	
}
