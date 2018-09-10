package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.view.AdviseView;
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
public class AdviseDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Long getCountByMapPageSearchOrdered(String facultyId, String studentId, String info, String search) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        Long count = 0L;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM adviseview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(facultyId))
                queryString += "AND faculty_id = '" + facultyId + "' ";
            if (StringUtils.isNotEmpty(studentId))
                queryString += "AND student_id = '" + studentId + "' ";
            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(student_id LIKE '%" + search + "%' OR " +
                        "    faculty_id LIKE '%" + search + "%' OR " +
                        "    sname      LIKE '%" + search + "%' OR " +
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

    public List<AdviseView> getByMapPageSearchOrdered(String facultyId, String studentId, String info, String search, int currentIndex, int pageSize,
                                                      String order, String orderColumn) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<AdviseView> adviseViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return adviseViews;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM adviseview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(facultyId))
                queryString += "AND faculty_id = '" + facultyId + "' ";
            if (StringUtils.isNotEmpty(studentId))
                queryString += "AND student_id = '" + studentId + "' ";
            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(" +
                        "student_id LIKE '%" + search + "%' OR " +
                        "faculty_id LIKE '%" + search + "%' OR " +
                        "sname      LIKE '%" + search + "%' OR " +
                        "fname      LIKE '%" + search + "%')";
            }
            queryString += "order by " + orderColumn + " " + order + " "
                    + "limit " + currentIndex + "," + pageSize;
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            adviseViews = ResultSetHelper.getObjectFor(rs, AdviseView.class);
            return adviseViews;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public AdviseView findOne(Integer id) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return null;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM adviseview WHERE id=" + id + "";
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            List<AdviseView> adviseViewList = ResultSetHelper.getObjectFor(rs, AdviseView.class);

            if (adviseViewList.isEmpty())
                return null;

            return adviseViewList.get(0);

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public AdviseView getAdviseViewByStudentId(String studentId) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return null;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM adviseview WHERE 1=1 AND student_id = '" + studentId + "' ";

            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            List<AdviseView> adviseViewList = ResultSetHelper.getObjectFor(rs, AdviseView.class);
            return adviseViewList.size() > 0 ? adviseViewList.get(0) : null;

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
