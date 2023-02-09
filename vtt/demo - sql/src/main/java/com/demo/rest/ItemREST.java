package com.demo.rest;

import com.demo.entity.Item;
import com.demo.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemREST {

    private final ItemService service;

    ItemREST(ItemService service){
        this.service = service;
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems(){
        List<Item> items = service.findAll();
        if (items.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id){
        Item item = service.findById(id);
        if (item == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<Item> saveItem(@RequestBody Item item){
        service.create(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/items")
    public ResponseEntity<Item> updateItem(@RequestBody Item item){

        if(item.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (service.findById(item.getId()) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.update(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}/{username}")
    public ResponseEntity<Item> deleteItem(@PathVariable Long id, @PathVariable String username){

        if (service.findById(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.deleteById(id, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
