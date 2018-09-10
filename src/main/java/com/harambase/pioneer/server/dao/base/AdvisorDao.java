package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.view.AdvisorView;
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
public class AdvisorDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Long getAdvisorCountByMapPageSearchOrdered(String search) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        Long count = 0L;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM advisorview WHERE 1=1 ";

            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(user_id LIKE '%" + search + "%' OR " +
                        "   name    LIKE '%" + search + "%')";
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

    public List<AdvisorView> getAdvisorByMapPageSearchOrdered(String status, String search, int currentIndex, int pageSize,
                                                              String order, String orderColumn) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<AdvisorView> advisorViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return advisorViews;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM advisorview WHERE 1=1 ";

            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(user_id LIKE '%" + search + "%' OR " +
                        "   name    LIKE '%" + search + "%')";
            }
            queryString += "order by " + orderColumn + " " + order + " "
                    + "limit " + currentIndex + "," + pageSize;
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            advisorViews = ResultSetHelper.getObjectFor(rs, AdvisorView.class);
            return advisorViews;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public AdvisorView findOne(String userId) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return null;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM advisorview WHERE 1=1 AND user_id = '" + userId + "' ";

            logger.info(queryString);

            rs = stmt.executeQuery(queryString);

            return ResultSetHelper.getObjectFor(rs, AdvisorView.class).get(0);
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public long count() throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        Long count = 0L;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM advisorview";

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
}
