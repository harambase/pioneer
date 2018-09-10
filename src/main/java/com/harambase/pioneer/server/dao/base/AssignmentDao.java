package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Statement;

@Component
public class AssignmentDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String createAssignmentTable(String crn) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return null;

            stmt = connection.createStatement();
            String tableName = "assignment_" + crn;
            String queryString = "CREATE TABLE " + tableName + " (" +
                    "   id INT(11) NOT NULL AUTO_INCREMENT," +
                    "   assignment_id VARCHAR(20) NOT NULL," +
                    "   name VARCHAR(50) NOT NULL," +
                    "   due_date VARCHAR(50) DEFAULT NULL," +
                    "   create_time VARCHAR(50) DEFAULT NULL," +
                    "   update_time VARCHAR(50) DEFAULT NULL," +
                    "   info VARCHAR(50) DEFAULT NULL," +
                    "   attachment TEXT," +
                    "   submission TEXT," +
                    "   extend_column_1 VARCHAR(255) DEFAULT NULL," +
                    "   extend_column_2 VARCHAR(255) DEFAULT NULL," +
                    "   extend_column_3 TEXT," +
                    "   PRIMARY KEY (id)" +
                    " ) ENGINE=InnoDB DEFAULT CHARSET=utf8";

            logger.info(queryString);
            int ret = stmt.executeUpdate(queryString);

            return ret == 1 ? tableName : null;

        } finally {
            if (stmt != null)
                stmt.close();
            if (connection != null)
                connection.close();
        }
    }

//    public boolean dropAssignmentTable(String crn) {
////        Connection connection = null;
////        try {
////            connection = DataServiceConnection.openDBConnection();
////            if (connection == null)
////                return null;
////
////            Statement stmt = connection.createStatement();
////            String tableName = "assignment_" + crn;
////            String queryString = "CREATE TABLE " + tableName + " (" +
////                    "   id INT(11) NOT NULL AUTO_INCREMENT," +
////                    "   assignment_id VARCHAR(20) NOT NULL," +
////                    "   name VARCHAR(50) NOT NULL," +
////                    "   due_date VARCHAR(50) DEFAULT NULL," +
////                    "   create_time VARCHAR(50) DEFAULT NULL," +
////                    "   update_time VARCHAR(50) DEFAULT NULL," +
////                    "   info VARCHAR(50) DEFAULT NULL," +
////                    "   attachment TEXT," +
////                    "   submission TEXT," +
////                    "   extend_column_1 VARCHAR(255) DEFAULT NULL," +
////                    "   extend_column_2 VARCHAR(255) DEFAULT NULL," +
////                    "   extend_column_3 TEXT," +
////                    "   PRIMARY KEY (id)" +
////                    " ) ENGINE=InnoDB DEFAULT CHARSET=utf8";
////
////            logger.info(queryString);
////            boolean succ = stmt.executeUpdate(queryString);
////
////            return succ;
////
////        } finally {
////            if (connection != null)
////                connection.close();
////        }
//    }

    public static void main(String[] args) {
        AssignmentDao assignmentDao = new AssignmentDao();
        try {
            assignmentDao.createAssignmentTable("120170164");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

