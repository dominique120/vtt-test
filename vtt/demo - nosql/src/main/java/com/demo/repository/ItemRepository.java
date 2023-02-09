package com.demo.repository;

import com.demo.entity.Item;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;

import java.util.Date;
import java.util.HashMap;

@Repository
public class ItemRepository {

    @Autowired
    private DynamoDbEnhancedClient dynamoDbenhancedClient;

    // save item
    public void save(Item item) {
        DynamoDbTable<Item> itemTable = getTable();
        item.setCreatedAt(new Date().getTime());
        itemTable.putItem(item);
    }

    // get one item
    public Item getitem(Long itemID) {
        DynamoDbTable<Item> itemTable = getTable();
        Key key = Key.builder().partitionValue(itemID).build();
        return itemTable.getItem(key);
    }

    // update to state deleted
    public Item deleteItem(Long id, String username) {
        DynamoDbTable<Item> table = getTable();

        Key key = Key.builder().partitionValue(id).build();
        Item i = table.getItem(key);
        i.setState(new HashMap<>(){{put("DELETED", "2");}});
        i.setUpdatedAt(new Date().getTime());
        return table.updateItem(i);
    }

    // update item
    public Item updateItem(Item item){
        DynamoDbTable<Item> table = getTable();
        item.setUpdatedAt(new Date().getTime());
        return table.updateItem(item);
    }

    private DynamoDbTable<Item> getTable() {
        return dynamoDbenhancedClient.table("Item", TableSchema.fromBean(Item.class));
    }

    @PostConstruct
    void init() {
        // create table with no schema
        // Lo ideal seria crear un tipo de schema,
        // o utilizar un patron de diseño como listas adyacentes.
        try {
            getTable().createTable(builder -> builder.provisionedThroughput(b -> b.readCapacityUnits(10L).writeCapacityUnits(10L).build()));
            System.out.println("table created");

        } catch (ResourceInUseException e) {
            System.out.println("table already exists");
        }
    }
}


