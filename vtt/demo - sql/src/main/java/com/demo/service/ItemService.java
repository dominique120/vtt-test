package com.demo.service;

import com.demo.entity.Item;
import com.demo.entity.State;
import com.demo.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class ItemService implements BaseService<Item, Long, String> {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository){
        this.repository = repository;
    }

    @Override
    public void create(Item entity) {
        entity.setCreatedAt(new Date().getTime());
        entity.setUpdatedAt(new Date().getTime());
        repository.save(entity);
    }

    @Override
    public void update(Item entity) {
        entity.setUpdatedAt(new Date().getTime());
        repository.save(entity);
    }

    @Override
    public void deleteById(Long id, String userName) {
        Item item = repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "ID was not found"));

        item.setUpdatedAt(new Date().getTime());
        item.setState(new State(3L, "DELETED"));

        repository.save(item);
    }

    @Override
    public List<Item> findAll() {
        return repository.findAll();
    }

    @Override
    public Item findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
