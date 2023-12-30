package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void executeUpdate(String sql, PreparedStatementSetter pss){
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setParameters(pstmt);

            pstmt.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException(e);
        } finally {
            try{
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }catch (SQLException e){
                throw new DataAccessException(e);
            }

        }
    }

    public void executeUpdate(String sql, Object... parameters){
        executeUpdate(sql, createPreparedStatementSetter(parameters));
    }

    public <T> T executeQuery(String sql, PreparedStatementSetter pss, RowMapper<T> rm) {
        List<T> list = list(sql, pss, rm);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    public <T> List<T> list(String sql, PreparedStatementSetter pss, RowMapper<T> rm) {
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
        } catch (SQLException e){
            throw new DataAccessException(e);
        } finally {
            try{
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }catch (SQLException e){
                throw new DataAccessException(e);
            }

        }
    }

    public <T> List<T> list(String sql, RowMapper<T> rm, Object... parameters){
        return list(sql, createPreparedStatementSetter(parameters), rm);
    }

    public <T> T executeQuery(String sql, RowMapper<T> rm, Object... parameters){
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
