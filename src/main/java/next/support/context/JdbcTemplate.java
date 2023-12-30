package next.support.context;

import core.jdbc.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import next.model.User;

public class JdbcTemplate {

    public void executeUpdate(String sql, PreparedStatementSetter pss) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setParameters(pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void executeUpdate(String sql, Object... parameters) throws SQLException {
        executeUpdate(sql, createPreparedStatementSetter(parameters));
    }

    public <T> T executeQuery(String sql, PreparedStatementSetter pss, RowMapper<T> rm) throws SQLException {
        List<T> list = list(sql, pss, rm);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    public <T> List<T> list(String sql, PreparedStatementSetter pss, RowMapper<T> rm) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setParameters(pstmt);
            rs = pstmt.executeQuery();

            List<T> list = new ArrayList<T>();
            while(rs.next()){
                list.add(rm.mapRow(rs));
            }

            return list;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public <T> List<T> list(String sql, RowMapper<T> rm, Object... parameters) throws SQLException{
        return list(sql, createPreparedStatementSetter(parameters), rm);
    }

    public <T> T executeQuery(String sql, RowMapper<T> rm, Object... parameters) throws SQLException {
        return executeQuery(sql, createPreparedStatementSetter(parameters), rm);
    }

    private static PreparedStatementSetter createPreparedStatementSetter(Object[] parameters) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                for (int i = 0; i < parameters.length; i++) {
                    pstmt.setObject(i + 1, parameters[i]);
                }
            }
        };
        return pss;
    }

}
