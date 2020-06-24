/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overtondata.jdbcstream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author khostel3
 */
public class ResultSetStreamTest {
    
    String url = "jdbc:h2:test;DB_CLOSE_DELAY=-1";
    
    @BeforeAll
    public void setupTests() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, "sa", "")) {
            
        }
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testIterator() {
        System.out.println("iterator");
        ResultSetStream instance = null;
        Iterator<Map<String, Object>> expResult = null;
        Iterator<Map<String, Object>> result = instance.iterator();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
