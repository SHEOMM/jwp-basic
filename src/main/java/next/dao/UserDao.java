package next.dao;

import java.sql.SQLException;
import java.util.List;
import next.model.User;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;

public class UserDao {

    public void insert(User user)  {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }


    public void update(User user)  {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE USERS SET userId=?, password=?, name=?, email=? WHERE userId=?";
        jdbcTemplate.executeUpdate(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() {
        RowMapper<User> rowMapper = rs -> {
            return new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"));
        };
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS";
        return jdbcTemplate.list(sql, rowMapper);
    }

    public User findByUserId(String userId)  {
        RowMapper<User> rowMapper = rs -> {
            return new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email"));
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return (User)jdbcTemplate.executeQuery(sql, rowMapper, userId);
    }


    
}
