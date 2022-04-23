package com.example.iss_bun.repo;



import com.example.iss_bun.domain.Agent;
import com.example.iss_bun.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class AgentRepo {
    private JdbcUtils dbUtils;
    public AgentRepo(Properties props) {
        dbUtils = new JdbcUtils(props);
    }


    public Agent findOneByUsername(String username) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select* from Agent where username=?")) {
            preStmt.setString(1,username);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Long id = result.getLong("id");
                    String parola = result.getString("password");
                   Agent ad=new Agent(username,parola);
                   ad.setId(id);
                   return ad;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return null;
    }

}
