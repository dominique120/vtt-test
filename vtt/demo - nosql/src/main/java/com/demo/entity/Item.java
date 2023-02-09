package com.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@DynamoDbBean
public class Item {

    private Long id;

    private String name;

    private Boolean refrigerationRequired;

    private String createdBy;

    private Integer unitQuantity;

    private Long createdAt;

    private Long updatedAt;

    private Map<String, String> type;

    private Map<String, String> packaging;

    private Map<String, String> state;

    private Map<String, String> unit;

    @DynamoDbPartitionKey
    public Long getId() {
        return id;
    }

    public void setId(Long pk) {
        this.id = pk;
    }

}
