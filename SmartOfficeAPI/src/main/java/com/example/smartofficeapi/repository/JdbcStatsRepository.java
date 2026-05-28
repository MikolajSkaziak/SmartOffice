package com.example.smartofficeapi.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcStatsRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcStatsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Integer> getUserRoleStats() {
        String sql = "SELECT role, COUNT(*) as count FROM users GROUP BY role";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        
        Map<String, Integer> stats = new HashMap<>();
        for (Map<String, Object> row : rows) {
            String role = (String) row.get("role");
            Integer count = ((Number) row.get("count")).intValue();
            stats.put(role, count);
        }
        return stats;
    }
}
