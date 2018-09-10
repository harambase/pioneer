package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.view.TranscriptView;
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
public class TranscriptDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<String> getDistinctColumnByInfo(String column, String info) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<String> studentNames = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return studentNames;

            stmt = connection.createStatement();

            String queryString = "SELECT DISTINCT " + column + " as " + column + " FROM transcriptview WHERE info='" + info + "'";

            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                studentNames.add(rs.getString(column));
            }
            return studentNames;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public Long getCountByMapPageSearchOrdered(String search, String studentId, String crn, String info, String complete) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        Long count = 0L;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM transcriptview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(studentId))
                queryString += "AND student_id = '" + studentId + "' ";
            if (StringUtils.isNotEmpty(crn))
                queryString += "AND crn = '" + crn + "' ";
            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";
            if (StringUtils.isNotEmpty(complete))
                queryString += "AND complete = '" + complete + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(" +
                        "student_id  LIKE  '%" + search + "%' OR " +
                        "crn         LIKE  '%" + search + "%' OR " +
                        "grade       LIKE  '%" + search + "%' OR " +
                        "complete    LIKE  '%" + search + "%' OR " +
                        "assign_time LIKE  '%" + search + "%' OR " +
                        "sname       LIKE  '%" + search + "%' OR " +
                        "cname       LIKE  '%" + search + "%')";
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

    public List<TranscriptView> getByMapPageSearchOrdered(int currentIndex, int pageSize, String search,
                                                          String order, String orderColumn,
                                                          String studentId, String crn, String info, String complete) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<TranscriptView> transcriptViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return transcriptViews;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM transcriptview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(studentId))
                queryString += "AND student_id = '" + studentId + "' ";
            if (StringUtils.isNotEmpty(crn))
                queryString += "AND crn = '" + crn + "' ";
            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";
            if (StringUtils.isNotEmpty(complete))
                queryString += "AND complete = '" + complete + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(" +
                        "student_id  LIKE  '%" + search + "%' OR " +
                        "crn         LIKE  '%" + search + "%' OR " +
                        "grade       LIKE  '%" + search + "%' OR " +
                        "complete    LIKE  '%" + search + "%' OR " +
                        "assign_time LIKE  '%" + search + "%' OR " +
                        "sname       LIKE  '%" + search + "%' OR " +
                        "cname       LIKE  '%" + search + "%')";
            }

            queryString += "order by " + orderColumn + " " + order + " "
                    + "limit " + currentIndex + "," + pageSize;
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            transcriptViews = ResultSetHelper.getObjectFor(rs, TranscriptView.class);
            return transcriptViews;

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
