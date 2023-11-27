package com.ecom.dynamic.service;

import java.util.List;
import java.util.Map;

public interface IProductService {
    void createTableAndInsertRecords(Map<String, Object> requestData);

    List<Map<String, Object>> getAllProducts();

}
