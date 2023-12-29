package entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DBUtil;

public class DynamicQueryBuilder {
    Connection connection;
    public DynamicQueryBuilder() {
        connection = DBUtil.getDBConn();
    }

    public List<Object[]> executeDynamicQuery(String tableName, List<String> columns, String condition, String orderBy) {
        List<Object[]> result = new ArrayList<>();
        try {
            String query = buildDynamicQuery(tableName, columns, condition, orderBy);
            System.out.println("Generated Query: " + query);

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    while (resultSet.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            rowData[i - 1] = resultSet.getObject(i);
                        }
                        result.add(rowData);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String buildDynamicQuery(String tableName, List<String> columns, String condition, String orderBy) {
        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        if (columns.isEmpty()) {
            queryBuilder.append("*");
        } else {
            queryBuilder.append(String.join(", ", columns));
        }

        queryBuilder.append(" FROM ").append(tableName);
        if (condition != null && !condition.isEmpty()) {
            queryBuilder.append(" WHERE ").append(condition);
        }
        if (orderBy != null && !orderBy.isEmpty()) {
            queryBuilder.append(" ORDER BY ").append(orderBy);
        }

        return queryBuilder.toString();
    }

}
