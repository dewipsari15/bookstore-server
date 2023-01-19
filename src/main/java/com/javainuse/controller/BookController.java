package com.javainuse.controller;

import com.javainuse.model.BookDTO;
import com.javainuse.model.DAOBook;
import com.javainuse.service.JwtProductDetailService;
import com.javainuse.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    public static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private JwtProductDetailService productDetailService;


    // -------------------Create a Product-------------------------------------------

    @RequestMapping(value = "/book/", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> createProduct(@RequestBody BookDTO product) throws SQLException, ClassNotFoundException {
        logger.info("Creating Product : {}", product);

        productDetailService.save(product);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }


    // -------------------Retrieve All Products--------------------------------------------

    @RequestMapping(value = "/books/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<DAOBook>> listAllProducts() throws SQLException, ClassNotFoundException {

        List<DAOBook> products = productDetailService.findAll();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    // -------------------Retrieve Single Product By Id------------------------------------------

    @RequestMapping(value = "/book/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        logger.info("Fetching Product with id {}", id);

        Optional<DAOBook> product = productDetailService.findById(id);

        if (product == null) {
            logger.error("Product with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Product with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    // ------------------- Update a Product ------------------------------------------------
    @RequestMapping(value = "/book/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @RequestBody BookDTO product) throws SQLException, ClassNotFoundException {
        logger.info("Updating Product with id {}", id);

        Optional<DAOBook> currentProduct = productDetailService.findById(id);

        if (currentProduct == null) {
            logger.error("Unable to update. Product with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to upate. Product with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        currentProduct.orElseThrow().setTitle(product.getTitle());
        currentProduct.orElseThrow().setAuthor(product.getAuthor());
        currentProduct.orElseThrow().setCover(product.getCover());
        currentProduct.orElseThrow().setDescription(product.getDescription());
        currentProduct.orElseThrow().setPrice(product.getPrice());
        currentProduct.orElseThrow().setStocks(product.getStocks());

        productDetailService.update(currentProduct.get().getId());
        return new ResponseEntity<>(currentProduct, HttpStatus.OK);

    }

    // ------------------- Delete a Product-----------------------------------------

    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        logger.info("Fetching & Deleting Book with id {}", id);

        productDetailService.delete(id);
        return new ResponseEntity<DAOBook>(HttpStatus.NO_CONTENT);
    }

}