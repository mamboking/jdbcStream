package com.overtondata.jdbcstream;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultSetStream implements Iterable<Map<String, Object>> {
    private static final Logger logger = Logger.getLogger(ResultSetStream.class.getName());
    
    ResultSet rs;
    
    public ResultSetStream(ResultSet rs) {
        this.rs = rs;
    }
    
    @Override
    public Iterator<Map<String, Object>> iterator() {
        try {
            return new ResultSetIterator(rs);
        } catch (SQLException ex) {
            return null;
        }
    }
    
    class ResultSetIterator implements Iterator<Map<String, Object>> {
        ResultSet rs;
        Map<String, Object> nextMap = null;
        List<String> columnNames;
        
        ResultSetIterator(ResultSet rs) throws SQLException {
            this.rs = rs;
            columnNames = new ArrayList<>();
            for (int i=1; i<=rs.getMetaData().getColumnCount(); i++) {
                columnNames.add(rs.getMetaData().getColumnName(i));
            }
        }

        @Override
        public boolean hasNext() {
            boolean gotNext = false;
            
            try {
                gotNext = rs.next();
                if (gotNext) {
                    nextMap = new HashMap<>();
                    for (String columnName : columnNames) {
                        nextMap.put(columnName, rs.getObject(columnName));
                    }
                }
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            
            
            return gotNext;
        }

        @Override
        public Map<String, Object> next() {
            return nextMap;
        }

    }
    
}
