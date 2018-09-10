package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.view.ContractView;
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
public class ContractDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<LinkedHashMap> getContractBySearch(String search, String type, String status, String maxLength) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<LinkedHashMap> contractList = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return contractList;

            stmt = connection.createStatement();

            String queryString = "select * from contractview where 1=1 ";
            if (StringUtils.isNotEmpty(type))
                queryString += "AND type LIKE '%" + type + "%' ";
            if (StringUtils.isNotEmpty(status))
                queryString += "AND status = '" + status + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "" +
                        "AND (contract_id   LIKE '%" + search + "%' OR" +
                        "     oname         LIKE '%" + search + "%' OR" +
                        "     owner_id      LIKE '%" + search + "%' OR" +
                        "     last_name     LIKE '%" + search + "%')";
            }

            if (StringUtils.isNotEmpty(maxLength))
                queryString += "limit 0," + Integer.parseInt(maxLength);
            else
                queryString += "limit 0,6";

            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            contractList = ResultSetHelper.getObjectAsLinkedHashMap(rs, ContractView.class);
            return contractList;
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public Long getCountByMapPageSearchOrdered(String search, String type, String status) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Long count = 0L;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return count;

            stmt = connection.createStatement();

            String queryString = "SELECT COUNT(*) AS count FROM contractview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(type))
                queryString += "AND type LIKE '%" + type + "%' ";
            if (StringUtils.isNotEmpty(status))
                queryString += "AND status = '" + status + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "" +
                        "AND (contract_id   LIKE '%" + search + "%' OR" +
                        "     oname         LIKE '%" + search + "%' OR" +
                        "     owner_id      LIKE '%" + search + "%' OR" +
                        "     last_name     LIKE '%" + search + "%')";
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

    public List<LinkedHashMap> getByMapPageSearchOrdered(int currentIndex, int pageSize, String search,
                                                         String order, String orderColumn, String type, String status) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<LinkedHashMap> contractList = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return contractList;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM contractview WHERE 1=1 ";
            if (StringUtils.isNotEmpty(type))
                queryString += "AND type LIKE '%" + type + "%' ";
            if (StringUtils.isNotEmpty(status))
                queryString += "AND status = '" + status + "' ";
            if (StringUtils.isNotEmpty(search)) {
                queryString += "" +
                        "AND (contract_id   LIKE '%" + search + "%' OR" +
                        "     oname         LIKE '%" + search + "%' OR" +
                        "     owner_id      LIKE '%" + search + "%' OR" +
                        "     last_name     LIKE '%" + search + "%')";
            }


            queryString += "order by " + orderColumn + " " + order + " "
                    + "limit " + currentIndex + "," + pageSize;
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            contractList = ResultSetHelper.getObjectAsLinkedHashMap(rs, ContractView.class);
            return contractList;

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
