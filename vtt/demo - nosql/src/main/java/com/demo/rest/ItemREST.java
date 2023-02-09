package com.demo.rest;

import com.demo.entity.Item;
import com.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ItemREST {

    @Autowired
    ItemRepository itemRepository;

    // Ejemplo simple de lo que se podria hacer con una base de datos NoSQL
    // en este caso se utilizó DynamoDB.
    // Se podrian implementar operaciones adicionales como queries
    // para realizar consultas especificas para las cuales se debe diseñar
    // la tabla.
    // Se podrian implementar scans pero su uso no es recomendado.

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id){
        return new ResponseEntity<>(itemRepository.getitem(id), HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<Item> saveItem(@RequestBody Item item){
        itemRepository.save(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/items")
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        itemRepository.updateItem(item);
        return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/items/{id}/{username}")
    public ResponseEntity<Item> deleteItem(@PathVariable Long id, @PathVariable String username){

        itemRepository.deleteItem(id, username);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
