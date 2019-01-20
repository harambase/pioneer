package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.view.FeedbackView;
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
public class FeedbackDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Long getFeedbackCountByMapPageSearchOrdered(String facultyId, String info, String search) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        Long count = 0L;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM feedbackview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(facultyId))
                queryString += "AND faculty_id = '" + facultyId + "' ";
            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(faculty_id LIKE '%" + search + "%' OR " +
                        "    fname      LIKE '%" + search + "%')";
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

    public List<FeedbackView> getFeedbackByMapPageSearchOrdered(String facultyId, String info, String search, int currentIndex, int pageSize,
                                                              String order, String orderColumn) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<FeedbackView> feedbackViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return feedbackViews;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM feedbackview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(facultyId))
                queryString += "AND faculty_id = '" + facultyId + "' ";
            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(faculty_id LIKE '%" + search + "%' OR " +
                        "    fname      LIKE '%" + search + "%')";
            }
            queryString += "order by " + orderColumn + " " + order + " "
                    + "limit " + currentIndex + "," + pageSize;
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            feedbackViews = ResultSetHelper.getObjectFor(rs, FeedbackView.class);
            return feedbackViews;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public FeedbackView findOne(Integer id) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return null;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM feedbackview WHERE id=" + id + "";
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            List<FeedbackView> feedbackViewList = ResultSetHelper.getObjectFor(rs, FeedbackView.class);

            if (feedbackViewList.isEmpty())
                return null;

            return feedbackViewList.get(0);

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
