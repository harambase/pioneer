package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.view.StudentView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class StudentDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Long getCountByMapPageSearchOrdered(String search, String status) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        Long count = 0L;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM studentview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(status))
                queryString += "AND status = '" + status + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(" +
                        "student_id   LIKE  '%" + search + "%' OR " +
                        "max_credits LIKE  '%" + search + "%' OR " +
                        "status      LIKE  '%" + search + "%' OR " +
                        "sname       LIKE  '%" + search + "%' OR " +
                        "complete    LIKE  '%" + search + "%' OR " +
                        "progress    LIKE  '%" + search + "%' OR " +
                        "incomplete  LIKE  '%" + search + "%')";
            }
            logger.info(queryString);
            rs = stmt.executeQuery(queryString);

            if (rs.next())
                count = rs.getLong("count");

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

    public List<LinkedHashMap> getByMapPageSearchOrdered(int currentIndex, int pageSize, String search,
                                                         String order, String orderColumn, String status) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<LinkedHashMap> studentList = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return studentList;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM studentview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(status))
                queryString += "AND status = '" + status + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(" +
                        "student_id  LIKE  '%" + search + "%' OR " +
                        "max_credits LIKE  '%" + search + "%' OR " +
                        "status      LIKE  '%" + search + "%' OR " +
                        "sname       LIKE  '%" + search + "%' OR " +
                        "complete    LIKE  '%" + search + "%' OR " +
                        "progress    LIKE  '%" + search + "%' OR " +
                        "incomplete  LIKE  '%" + search + "%')";
            }
            queryString += "order by " + orderColumn + " " + order + " "
                    + "limit " + currentIndex + "," + pageSize;
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            studentList = ResultSetHelper.getObjectAsLinkedHashMap(rs, StudentView.class);
            return studentList;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public LinkedHashMap findOne(String studentId) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return null;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM studentview WHERE student_id='" + studentId + "'";
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            List<LinkedHashMap> studentList = ResultSetHelper.getObjectAsLinkedHashMap(rs, StudentView.class);

            if (studentList.isEmpty())
                return null;

            return studentList.get(0);

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public List<LinkedHashMap> getStudentList(String crn, String search) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<LinkedHashMap> studentViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return studentViews;

            stmt = connection.createStatement();
            String queryString = "SELECT * FROM studentview s WHERE s.student_id IN (SELECT t.student_id FROM transcript t WHERE t.crn = '" + crn + "')";
            if (StringUtils.isNotEmpty(search))
                queryString += "AND(" +
                        "student_id  LIKE  '%" + search + "%' OR " +
                        "sname       LIKE  '%" + search + "%')";

            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            studentViews = ResultSetHelper.getObjectAsLinkedHashMap(rs, StudentView.class);
            return studentViews;

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
