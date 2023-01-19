package com.javainuse.dao;

import com.javainuse.model.DAOBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface BookDao extends CrudRepository<DAOBook, Integer> {
    DAOBook findById(long id);
}
