package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.view.TempAdviseView;
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
public class TempAdviseDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public long getCountByMapPageSearchOrdered(String search, String viewStatus, String info, String studentId, String facultyId) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        Long count = 0L;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM tempadviseview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(facultyId))
                queryString += "AND (first_id = '" + facultyId + "' OR second_id = '" + facultyId + "' OR third_id = '" + facultyId + "') ";
            if (StringUtils.isNotEmpty(studentId))
                queryString += "AND student_id = '" + studentId + "' ";
            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";
            if (StringUtils.isNotEmpty(viewStatus))
                queryString += "AND status = '" + viewStatus + "' ";
            if (StringUtils.isNotEmpty(search))
                queryString += "AND (student_id LIKE  '%" + search
                        + "%' OR first_id  LIKE '%" + search + "%'"
                        + "%' OR second_id LIKE '%" + search + "%'"
                        + "%' OR third_id  LIKE '%" + search + "%'"
                        + ")";

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

    public List<TempAdviseView> getByMapPageSearchOrdered(int currentIndex, int pageSize, String search,
                                                          String order, String orderColumn, String viewStatus, String info, String studentId, String facultyId) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<TempAdviseView> tempAdvise = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return tempAdvise;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM tempadviseview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(facultyId))
                queryString += "AND (first_id = '" + facultyId + "' OR second_id = '" + facultyId + "' OR third_id = '" + facultyId + "') ";
            if (StringUtils.isNotEmpty(studentId))
                queryString += "AND student_id = '" + studentId + "' ";
            if (StringUtils.isNotEmpty(info))
                queryString += "AND info = '" + info + "' ";
            if (StringUtils.isNotEmpty(viewStatus))
                queryString += "AND status = '" + viewStatus + "' ";
            if (StringUtils.isNotEmpty(search))
                queryString += "AND (student_id LIKE  '%" + search
                        + "%' OR first_id  LIKE '%" + search + "%'"
                        + "%' OR second_id LIKE '%" + search + "%'"
                        + "%' OR third_id  LIKE '%" + search + "%'"
                        + ")";

            if (StringUtils.isNotEmpty(search))
                queryString += "AND (student_id LIKE  '%" + search
                        + "%' OR first_id  LIKE '%" + search + "%'"
                        + "%' OR second_id LIKE '%" + search + "%'"
                        + "%' OR third_id  LIKE '%" + search + "%'"
                        + ")";


            queryString += "order by " + orderColumn + " " + order + " "
                    + "limit " + currentIndex + "," + pageSize;
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            tempAdvise = ResultSetHelper.getObjectFor(rs, TempAdviseView.class);
            return tempAdvise;

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
