package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.base.TempCourse;
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
public class TempCourseDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public long getCountByMapPageSearchOrdered(String search, String status, String faculty_id) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        Long count = 0L;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM tempcourse WHERE 1=1 ";
            if (StringUtils.isNotEmpty(status))
                queryString += "AND status = '" + status + "' ";
            if (StringUtils.isNotEmpty(faculty_id))
                queryString += "AND faculty_id = '" + faculty_id + "' ";
            if (StringUtils.isNotEmpty(search))
                queryString += "AND (crn LIKE  '%" + search + "%' OR faculty_id LIKE '%" + search + "%')";

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

    public List<TempCourse> getByMapPageSearchOrdered(int currentIndex, int pageSize, String search,
                                                      String order, String orderColumn, String status, String faculty_id) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<TempCourse> tempCourses = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return tempCourses;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM tempcourse WHERE 1=1 ";
            if (StringUtils.isNotEmpty(status))
                queryString += "AND status = '" + status + "' ";
            if (StringUtils.isNotEmpty(faculty_id))
                queryString += "AND faculty_id = '" + faculty_id + "' ";
            if (StringUtils.isNotEmpty(search))
                queryString += "AND (crn LIKE  '%" + search + "%' OR faculty_id LIKE '%" + search + "%')";

            queryString += "order by " + orderColumn + " " + order + " "
                    + "limit " + currentIndex + "," + pageSize;
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            tempCourses = ResultSetHelper.getObjectFor(rs, TempCourse.class);
            return tempCourses;

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
