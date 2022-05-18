package repository;

/*import db.Db;*/

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil{

    @Resource(name = "java:comp/env/jdbc/pool")


    private static PreparedStatement getPreparedStatement(String sql, Object... args) throws SQLException, ClassNotFoundException {
        DataSource ds1 = null;
        PreparedStatement pst = ds1.getConnection().prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
            pst.setObject(i + 1, args[i]);
        }
        return pst;
    }

    public static boolean executeUpdate(String sql, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = getPreparedStatement(sql, args);
        return pst.executeUpdate() > 0;
    }

    public static ResultSet executeQuery(String sql, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = getPreparedStatement(sql, args);
        return pst.executeQuery();
    }

}
