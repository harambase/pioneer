package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.base.Role;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoleDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<Role> getByMapPageSearchOrdered(String search, String order, String orderColumn) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<Role> roles = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return roles;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM role WHERE 1=1 ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(" +
                        "role_id   LIKE '%" + search + "%' OR " +
                        "role_name LIKE '%" + search + "%' OR " +
                        "role_code LIKE '%" + search + "%')";
            }
            queryString += "order by " + orderColumn + " " + order + " ";
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            roles = ResultSetHelper.getObjectFor(rs, Role.class);
            return roles;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }
}
