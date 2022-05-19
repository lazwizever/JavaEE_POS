package repository;

/*import db.Db;*/

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.sql.*;

public class CrudUtil{


    private static PreparedStatement getPreparedStatement(Connection connection ,String sql, Object... args) throws SQLException, ClassNotFoundException {

        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
            pst.setObject(i + 1, args[i]);
        }
        return pst;
    }

    public static boolean executeUpdate(Connection connection ,String sql, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = getPreparedStatement(connection,sql, args);
        return pst.executeUpdate() > 0;
    }

    public static ResultSet executeQuery(Connection connection,String sql, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = getPreparedStatement(connection,sql, args);
        return pst.executeQuery();
    }

}
