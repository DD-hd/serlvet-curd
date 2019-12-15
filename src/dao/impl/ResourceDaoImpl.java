package dao.impl;

import dao.ResourceDao;
import domain.Resource;
import utils.JdbcDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ResourceDaoImpl implements ResourceDao {
    @Override
    public int add(Resource learnResouce) {
        Connection conn = JdbcDataSource.getConn();
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement("insert into `learn_resource`(`author`,`title`,`url`) values (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, learnResouce.getAuthor());
            statement.setString(2, learnResouce.getTitle());
            statement.setString(3, learnResouce.getUrl());
            statement.executeUpdate();
            ResultSet resultSet= statement.getGeneratedKeys();
            int id=-1;
            if(resultSet.next()){
                id=resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(int id, Resource learnResouce) {
        Connection conn = JdbcDataSource.getConn();
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement("update `learn_resource` set `author`=?,`title`=?,`url`=?) where id=?");
            statement.setString(1, learnResouce.getAuthor());
            statement.setString(2, learnResouce.getTitle());
            statement.setString(3, learnResouce.getUrl());
            statement.setInt(4, id);

            int row = statement.executeUpdate();

            statement.close();
            return row;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(int id) {
        Connection conn = JdbcDataSource.getConn();
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement("delete from `learn_resource` where id=?");
            statement.setInt(1, id);
            int row = statement.executeUpdate();
            statement.close();
            return row;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource queryLearnResouceById(int id) {
        Connection conn = JdbcDataSource.getConn();
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement("SELECT * from `learn_resource` where id=?");
            statement.setInt(1, id);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                return null;
            }
            Resource resource = new Resource();
            resource.setAuthor(resultSet.getString("author"));
            resource.setTitle(resultSet.getString("title"));
            resource.setUrl(resultSet.getString("url"));
            resource.setId(resultSet.getInt("id"));
            statement.close();
            return resource;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Resource> queryLearnResouceList( int offset, int limit) {
        Connection conn = JdbcDataSource.getConn();
        PreparedStatement statement = null;
        List<Resource> resources = new ArrayList<Resource>();
        try {
            statement = conn.prepareStatement("SELECT * from `learn_resource` LIMIT ?,?");
            statement.setInt(1, offset);
            statement.setInt(2, limit);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Resource resource = new Resource();
                resource.setAuthor(resultSet.getString("author"));
                resource.setTitle(resultSet.getString("title"));
                resource.setUrl(resultSet.getString("url"));
                resource.setId(resultSet.getInt("id"));
                resources.add(resource);
            }
            statement.close();
            return resources;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
