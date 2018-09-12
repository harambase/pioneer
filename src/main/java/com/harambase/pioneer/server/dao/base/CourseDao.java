package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.view.CourseView;
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
public class CourseDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<CourseView> findTop5ByStatusAndSearch(String search, String status, String info) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<CourseView> courseViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return courseViews;

            stmt = connection.createStatement();

            String queryString = "select * from courseview where 1=1 ";

            if (StringUtils.isNotEmpty(status))
                queryString += "AND status = '" + status + "' ";

            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";

            if (StringUtils.isNotEmpty(search)) {
                queryString += "" +
                        "AND (crn      LIKE '%" + search + "%' OR" +
                        "     name     LIKE '%" + search + "%' OR" +
                        "     comment  LIKE '%" + search + "%')";
            }
            queryString += "order by id desc limit 0,4";
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            courseViews = ResultSetHelper.getObjectFor(rs, CourseView.class);
            return courseViews;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public Long getCountByMapPageSearchOrdered(String facultyId, String info, String status, String search) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        Long count = 0L;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM courseview c WHERE 1=1 ";
            if (StringUtils.isNotEmpty(facultyId))
                queryString += "AND c.faculty_id = '" + facultyId + "' ";
            if (StringUtils.isNotEmpty(info))
                queryString += "AND c.info = '" + info + "' ";
            if (StringUtils.isNotEmpty(status))
                queryString += "AND c.status = '" + status + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(" +
                        " c.crn     LIKE '%" + search + "%' or c.name     LIKE '%" + search + "%' or c.credits LIKE '%" + search + "%' or" +
                        " c.level   LIKE '%" + search + "%' or c.section  LIKE '%" + search + "%' or c.day     LIKE '%" + search + "%' or" +
                        " c.time    LIKE '%" + search + "%' or c.capacity LIKE '%" + search + "%' or c.remain  LIKE '%" + search + "%' or" +
                        " c.faculty LIKE '%" + search + "%' or c.date     LIKE '%" + search + "%' or c.update_time LIKE '%" + search + "%')";
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

    public List<LinkedHashMap> getByMapPageSearchOrdered(String facultyId, String info, String status, String search, int currentIndex, int pageSize,
                                                         String order, String orderColumn) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<LinkedHashMap> courseViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return courseViews;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM courseview c WHERE 1=1 ";
            if (StringUtils.isNotEmpty(facultyId))
                queryString += "AND c.faculty_id = '" + facultyId + "' ";
            if (StringUtils.isNotEmpty(info))
                queryString += "AND c.info = '" + info + "' ";
            if (StringUtils.isNotEmpty(status))
                queryString += "AND c.status = '" + status + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "AND(" +
                        " c.crn     LIKE '%" + search + "%' or c.name     LIKE '%" + search + "%' or c.credits LIKE '%" + search + "%' or" +
                        " c.level   LIKE '%" + search + "%' or c.section  LIKE '%" + search + "%' or c.day     LIKE '%" + search + "%' or" +
                        " c.time    LIKE '%" + search + "%' or c.capacity LIKE '%" + search + "%' or c.remain  LIKE '%" + search + "%' or" +
                        " c.faculty LIKE '%" + search + "%' or c.date     LIKE '%" + search + "%' or c.update_time LIKE '%" + search + "%')";
            }
            queryString += "order by " + orderColumn + " " + order + " "
                    + "limit " + currentIndex + "," + pageSize;
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            courseViews = ResultSetHelper.getObjectAsLinkedHashMap(rs, CourseView.class);
            return courseViews;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public List<CourseView> findAllCoursesViewByInfo(String info) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<CourseView> courseViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return courseViews;

            stmt = connection.createStatement();
            String queryString = "select * from courseview where info= '" + info + "'";
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            courseViews = ResultSetHelper.getObjectFor(rs, CourseView.class);
            return courseViews;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public List<CourseView> findCourseViewByFacultyIdAndInfo(String facultyId, String info) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<CourseView> courseViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return courseViews;

            stmt = connection.createStatement();
            String queryString = "select * from courseview where faculty_id= '" + facultyId + "' and  info = '" +info+ "'";

            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            courseViews = ResultSetHelper.getObjectFor(rs, CourseView.class);
            return courseViews;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public CourseView findByCrn(String crn) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return null;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM courseview WHERE crn='" + crn + "'";
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            List<CourseView> courseViewList = ResultSetHelper.getObjectFor(rs, CourseView.class);

            if (courseViewList.isEmpty())
                return null;

            return courseViewList.get(0);

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }

    }

    public List<CourseView> findCourseViewByStudentId(String status, String studentId) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<CourseView> courseViews = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return courseViews;

            stmt = connection.createStatement();
            String queryString = "SELECT * FROM courseview c WHERE c.crn IN (SELECT t.crn FROM transcript t WHERE t.student_id = '" + studentId + "')";
            if (StringUtils.isNotEmpty(status))
                queryString += " AND c.status = " + status + "";
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            courseViews = ResultSetHelper.getObjectFor(rs, CourseView.class);
            return courseViews;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public int countAllByStatus(String status) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return 0;

            stmt = connection.createStatement();
            String queryString = "SELECT count(*) AS count FROM courseview WHERE status='" + status + "'";
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            if (rs.next()) {
                return rs.getInt("count");
            }

            return 0;

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
