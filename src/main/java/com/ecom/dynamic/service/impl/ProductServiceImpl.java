package com.ecom.dynamic.service.impl;

import com.ecom.dynamic.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void createTableAndInsertRecords(Map<String, Object> requestData) {
        createTable(requestData);
        insertRecords(requestData);
    }

    @Override
    public List<Map<String, Object>> getAllProducts() {
        return jdbcTemplate.queryForList("SELECT * FROM products");
    }

    private void createTable(Map<String, Object> requestData) {
        String tableName = (String) requestData.get("table");
        @SuppressWarnings("unchecked") List<Map<String, Object>> records = (List<Map<String, Object>>) requestData.get("records");

        if (!records.isEmpty()) {
            String createTableSql = buildCreateTableSql(tableName, records.get(0));
            jdbcTemplate.execute(createTableSql);
        }
    }

    private String sanitizeTableName(String tableName) {
        // Implement logic to sanitize and validate table name
        // For simplicity, just a basic example is provided here
        return tableName.replaceAll("[^a-zA-Z0-9_]", "");
    }

    private String buildCreateTableSql(String tableName, Map<String, Object> record) {
        String columnsSql = record.keySet().stream().map(key -> key + " VARCHAR(255)") // Simplified assumption: all fields are VARCHAR
                .collect(Collectors.joining(", "));

        return "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnsSql + ")";
    }

    private void insertRecords(Map<String, Object> requestData) {
        String tableName = sanitizeTableName((String) requestData.get("table"));
        @SuppressWarnings("unchecked") List<Map<String, String>> records = (List<Map<String, String>>) requestData.get("records");

        for (Map<String, String> record : records) {
            String sql = buildInsertSql(tableName, record);
            Object[] values = record.values().toArray();
            jdbcTemplate.update(sql, values);
        }
    }

    private String buildInsertSql(String tableName, Map<String, String> record) {
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");

        for (String column : record.keySet()) {
            columns.add(column);
            placeholders.add("?");
        }

        return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
    }
}



