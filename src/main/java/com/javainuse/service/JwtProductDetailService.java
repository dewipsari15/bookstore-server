package com.javainuse.service;

import com.javainuse.dao.BookDao;
import com.javainuse.model.BookDTO;
import com.javainuse.model.DAOBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class JwtProductDetailService {

    @Autowired
    private BookDao bookDao;
    private long id;

    public JwtProductDetailService() {
    }

    public DAOBook save(BookDTO product) {

        DAOBook newProduct = new DAOBook();
        newProduct.setTitle(product.getTitle());
        newProduct.setAuthor(product.getAuthor());
        newProduct.setDescription(product.getDescription());
        newProduct.setCover(product.getCover());
        newProduct.setPrice(product.getPrice());
        newProduct.setStocks(product.getStocks());


        return bookDao.save(newProduct);
    }


    public Optional<DAOBook> findById(Long id) {
        return Optional.ofNullable(bookDao.findById(id));
    }


    public List<DAOBook> findAll() {
        List<DAOBook> products = new ArrayList<>();
        bookDao.findAll().forEach(products::add);
        return products;
    }


    public void delete(Long id) {
        DAOBook product = bookDao.findById(id);
        bookDao.delete(product);
    }


    public DAOBook update(Long id) {
        DAOBook product = bookDao.findById(id);
        return bookDao.save(product);
    }

}