package com.harambase.pioneer.server.dao.base;

import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.view.MessageView;
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
public class MessageDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public int countMessageByStatus(String receiver_id, String sender_id, String box, String status) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return 0;

            stmt = connection.createStatement();

            String queryString = "SELECT count(*) as count FROM messageview WHERE status = '" + status + "' "
                    + whereBuilderByLabel(receiver_id, sender_id, box);

            logger.info(queryString);
            rs = stmt.executeQuery(queryString);

            int ret = 0;
            if (rs.next()) {
                ret = rs.getInt("count");
            }
            return ret;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }

    }

    public long getCountByMapPageSearchOrdered(String receiver_id, String sender_id,
                                               String box, String search) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return 0;

            stmt = connection.createStatement();

            String queryString = "select count(*) as count from messageview where 1 = 1 ";

            queryString += whereBuilderByLabel(receiver_id, sender_id, box);

            if (StringUtils.isNotEmpty(search)) {
                queryString += "" +
                        "and(email   LIKE '%" + search + "%' or" +
                        "    subject LIKE '%" + search + "%' or" +
                        "    status  LIKE '%" + search + "%' or" +
                        "    sender  LIKE '%" + search + "%' or" +
                        "    title   LIKE '%" + search + "%' or" +
                        "    date    LIKE '%" + search + "%') ";
            }
            logger.info(queryString);
            rs = stmt.executeQuery(queryString);
            int ret = 0;
            if (rs.next()) {
                ret = rs.getInt("count");
            }
            return ret;

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public List<MessageView> getByMapPageSearchOrdered(String receiver_id, String sender_id, String box, String search, int currentIndex,
                                                       int pageSize, String order, String orderColumn) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<MessageView> messageViewViewList = new ArrayList<>();
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return messageViewViewList;

            stmt = connection.createStatement();

            String queryString = "select * from messageview where 1 = 1 ";
            queryString += whereBuilderByLabel(receiver_id, sender_id, box);

            if (StringUtils.isNotEmpty(search)) {
                queryString += "" +
                        "and(email   LIKE '%" + search + "%' or" +
                        "    subject LIKE '%" + search + "%' or" +
                        "    status  LIKE '%" + search + "%' or" +
                        "    sender  LIKE '%" + search + "%' or" +
                        "    title   LIKE '%" + search + "%' or" +
                        "    date    LIKE '%" + search + "%') ";
            }
            queryString += "" +
                    " order by " + orderColumn + " " + order + " " +
                    " limit " + currentIndex + "," + pageSize;
            logger.info(queryString);
            rs = stmt.executeQuery(queryString);
            messageViewViewList = ResultSetHelper.getObjectFor(rs, MessageView.class);

            return messageViewViewList;
        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    public MessageView findOne(Integer id) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null)
                return null;

            stmt = connection.createStatement();

            String queryString = "SELECT * FROM messageview WHERE id=" + id + "";
            logger.info(queryString);

            rs = stmt.executeQuery(queryString);
            List<MessageView> messageViewList = ResultSetHelper.getObjectFor(rs, MessageView.class);

            if (messageViewList.isEmpty())
                return null;

            return messageViewList.get(0);

        } finally {
            if (stmt != null)
                stmt.close();
            if (rs != null)
                rs.close();
            if (connection != null)
                connection.close();
        }
    }

    private String whereBuilderByLabel(String receiver_id, String sender_id, String box) {

        String queryString = "";

        if (box.equals("inbox"))
            queryString += "AND receiver_id like '%" + receiver_id + "%' AND (status = 'read' OR status = 'unread') ";
        if (box.equals("important"))
            queryString += "AND receiver_id like '%" + receiver_id + "%' AND (labels LIKE '%重要%' OR labels LIKE '%紧急%') AND (status = 'read' OR status = 'unread') ";
        if (box.equals("urgent"))
            queryString += "AND receiver_id like '%" + receiver_id + "%' AND labels LIKE '%紧急%' AND (status = 'read' OR status = 'unread') ";
        if (box.equals("draft"))
            queryString += "AND sender_id = '" + sender_id + "'";
        if (box.equals("trash"))
            queryString += "AND (receiver_id like '%" + receiver_id + "%' OR sender_id = '" + sender_id + "') AND status = 'trashed'";

        return queryString;
    }
}
