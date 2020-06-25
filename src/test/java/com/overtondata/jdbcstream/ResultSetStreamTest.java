/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.overtondata.jdbcstream;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
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

    static String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    static String createTable = "create table if not exists test("
            + "id int primary key"
            + ", first varchar(255)"
            + ", last varchar(255)"
            + ", somedate date)";
    static String truncateTable = "truncate table test";
    static String insertRow = "insert into test(id, first, last, somedate) values(?,?,?,?)";

    @BeforeAll
    public static void setupTests() throws SQLException {
        try ( Connection conn = DriverManager.getConnection(url, "sa", "");  PreparedStatement ps = conn.prepareStatement(createTable);) {
            ps.execute();
        }
    }

    @BeforeEach
    public void setUp() throws SQLException {
        try ( Connection conn = DriverManager.getConnection(url, "sa", "");  PreparedStatement trunc = conn.prepareStatement(truncateTable);  PreparedStatement insert = conn.prepareStatement(insertRow);) {
            trunc.execute();
            for (int i = 0; i < 10; i++) {
                insert.setInt(1, i);
                insert.setString(2, String.format("first%s", i));
                insert.setString(3, String.format("last%s", i));
                insert.setDate(4, Date.valueOf(LocalDate.of(2020, Month.JUNE, i + 1)));
                insert.executeUpdate();
            }
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testIterator() throws SQLException {
        String sql = "select * from test order by id";
        try ( Connection conn = DriverManager.getConnection(url, "sa", "");  PreparedStatement query = conn.prepareStatement(sql);) {
            ResultSet rs = query.executeQuery();
            ResultSetStream s = new ResultSetStream(rs);
            ArrayList<Map<String, Object>> l = new ArrayList<>();
            for (Map<String, Object> map : s) {
                for (String colName : map.keySet()) {
                    System.out.println(colName + ":" + map.get(colName));
                }
                System.out.println("");
                l.add(map);
            }
            assertEquals(10, l.size());
        }
    }

}
