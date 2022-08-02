package com.davidcipriati.repository;

import com.davidcipriati.model.Reimbursement;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class ReimbursementRepository implements IReimbursementRepository {
    private DataSource dataSource;

    public ReimbursementRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public int createReimbursement(Reimbursement reimbursement) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "insert into reimbursement (amount, description, author_id, resolver_id, status, outcome, type) values (?, ?, ?, ?, ?, ?, ?)";
            String idSql = "select max(reimb_id) as reimb_id from reimbursement";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setFloat(1, reimbursement.getAmount());
            ps.setString(2, reimbursement.getDescription());
            ps.setInt(3, reimbursement.getAuthorId());
            ps.setNull(4, Types.NULL);
            ps.setString(5, reimbursement.getStatus());
            ps.setString(6, reimbursement.getOutcome());
            ps.setString(7, reimbursement.getType());

            ps.executeUpdate();

            ps = connection.prepareStatement(idSql);
            ResultSet rs = ps.executeQuery();

            int id = -1;
            while (rs.next()) {
                id = rs.getInt("reimb_id");
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
        }
        return -1;
    }

    // wrong you have to know the resolver_id
    // send in the entire reimbursement object
    // you really only need the reimb_id and manager id
    @Override
    public int updatePendingReimbursement(int reimbursementId) {
        int row = -1;
//        try (Connection connection = ConnectionManager.getConnection()) {
        try {
            Connection connection = dataSource.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
            // logging
        }

        return row;
    }

    @Override
    public List<Reimbursement> findAllPendingByUserId(int userId) {
        List<Reimbursement> pendingList = new ArrayList<>();
//        try (Connection connection = ConnectionManager.getConnection()) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select reimb_id, amount, description, author_id, resolver_id, status, outcome, type from reimbursement where author_id=? AND status='Pending'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reimbursement reimbursement = new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getFloat("amount"),
                        rs.getString("description"),
                        rs.getInt("author_id"),
                        rs.getInt("resolver_id"),
                        rs.getString("status"),
                        rs.getString("outcome"),
                        rs.getString("type")
                );
                pendingList.add(reimbursement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // logging
        }

        return pendingList;
    }

    @Override
    public List<Reimbursement> findAllResolvedByUserId(int userId) {
        List<Reimbursement> resolvedList = new ArrayList<>();
//        try (Connection connection = ConnectionManager.getConnection()) {
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select reimb_id, amount, description, author_id, resolver_id, status, outcome, type from reimbursement where author_id=? AND status='Resolved'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reimbursement reimbursement = new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getFloat("amount"),
                        rs.getString("description"),
                        rs.getInt("author_id"),
                        rs.getInt("resolver_id"),
                        rs.getString("status"),
                        rs.getString("outcome"),
                        rs.getString("type")
                );
                resolvedList.add(reimbursement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // logging
        }

        return resolvedList;
    }

    @Override
    public List<Reimbursement> findAllPending() {
        List<Reimbursement> pendingList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select reimb_id, amount, description, author_id, resolver_id, status, outcome, type from reimbursement where status='Pending'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reimbursement reimbursement = new Reimbursement(
                  rs.getInt("reimb_id"),
                  rs.getFloat("amount"),
                  rs.getString("description"),
                  rs.getInt("author_id"),
                  rs.getInt("resolver_id"),
                  rs.getString("status"), rs.getString("outcome"),
                  rs.getString("type")
                );
                pendingList.add(reimbursement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // logging
        }

            return pendingList;
    }

    @Override
    public List<Reimbursement> findAllResolved() {
        List<Reimbursement> resolvedList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select reimb_id, amount, description, author_id, resolver_id, status, outcome, type from reimbursement where status='Resolved'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reimbursement reimbursement = new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getFloat("amount"),
                        rs.getString("description"),
                        rs.getInt("author_id"),
                        rs.getInt("resolver_id"),
                        rs.getString("status"),
                        rs.getString("outcome"),
                        rs.getString("type")
                );
                resolvedList.add(reimbursement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // logging
        }

        return resolvedList;
    }

    @Override
    public List<Reimbursement> findAllByUserId(int userId) {
        List<Reimbursement> reimbursementList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select reimb_id, amount, description, author_id, resolver_id, status, outcome, type from reimbursement where author_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reimbursement reimbursement = new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getFloat("amount"),
                        rs.getString("description"),
                        rs.getInt("author_id"),
                        rs.getInt("resolver_id"),
                        rs.getString("status"),
                        rs.getString("outcome"),
                        rs.getString("type")
                );
                reimbursementList.add(reimbursement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // logging
        }

        return reimbursementList;
    }

    @Override
    public Reimbursement findByReimbursementId(int reimbID) {
        Reimbursement reimbursement = null;
        try {
            Connection connection = dataSource.getConnection();
            String sql = "select reimb_id, amount, description, author_id, resolver_id, status, outcome, type from reimbursement where reimb_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, reimbID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reimbursement = new Reimbursement(
                        rs.getInt("reimb_id"),
                        rs.getFloat("amount"),
                        rs.getString("description"),
                        rs.getInt("author_id"),
                        rs.getInt("resolver_id"),
                        rs.getString("status"),
                        rs.getString("outcome"),
                        rs.getString("type")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // logging
        }

        return reimbursement;
    }
}
