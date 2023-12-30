package next.support.context;

import java.sql.ResultSet;
import java.sql.SQLException;
import next.model.User;

public interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}
