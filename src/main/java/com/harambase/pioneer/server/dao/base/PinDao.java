package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.view.PinView;
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
public class PinDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Long getCountByMapPageSearchOrdered(String search, String info, String ownerId) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        Long count = 0L;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM pinview WHERE 1=1 ";

            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";
            if (StringUtils.isNotEmpty(ownerId))
                queryString += "AND owner_id = '" + ownerId + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(owner_id LIKE '%" + search + "%' OR " +
                        "    pin      LIKE '%" + search + "%' OR " +
                        "    oname    LIKE '%" + search + "%')";
            }
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);

            if (rs.next()) {
                count = rs.getLong("count");
            }
            return count;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public List<PinView> getByMapPageSearchOrdered(int currentIndex, int pageSize, String order,
                                                   String orderColumn, String search, String info, String ownerId) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<PinView> pinViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return pinViews;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM pinview WHERE 1=1 ";

            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";
            if (StringUtils.isNotEmpty(ownerId))
                queryString += "AND owner_id = '" + ownerId + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(owner_id LIKE '%" + search + "%' OR " +
                        "    pin      LIKE '%" + search + "%' OR " +
                        "    oname    LIKE '%" + search + "%')";
            }
            queryString += "order by " + orderColumn + " " + order + " "
                    + "limit " + currentIndex + "," + pageSize;
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            pinViews = ResultSetHelper.getObjectFor(rs, PinView.class);
            return pinViews;

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
