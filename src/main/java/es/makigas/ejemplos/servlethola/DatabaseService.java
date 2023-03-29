package es.makigas.ejemplos.servlethola;

import jakarta.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class DatabaseService {

    private Connection conn;
    
    private static final String SCHEMA = """
        CREATE TABLE nombres(id INTEGER PRIMARY KEY, nombre VARCHAR, apellido VARCHAR);
    """;
    
    private static final String INSERT = """
        INSERT INTO nombres(id, nombre, apellido) VALUES(?, ?, ?);
    """;
    
    private static final String QUERY = """
        SELECT id, nombre, apellido FROM nombres WHERE id = ?;
    """;
    
    private static final String ALL = """
        SELECT id, nombre, apellido FROM nombres;
    """;
    
    private static final String COUNT = """
        SELECT COUNT(*) AS total FROM nombres;
    """;
    
    private void assertDatabaseConnection() {
        if (conn != null) {
            return;
        }
        try {
            Class.forName("org.duckdb.DuckDBDriver");
            conn = DriverManager.getConnection("jdbc:duckdb:quack.db");
            conn.createStatement().execute(SCHEMA);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertPerson(Person p) throws SQLException {
        assertDatabaseConnection();
        PreparedStatement stmt = conn.prepareStatement(INSERT);
        stmt.setInt(1, p.id());
        stmt.setString(2, p.name());
        stmt.setString(3, p.surname());
        stmt.execute();
    }
    
    public Person getPersonById(int id) throws SQLException {
        assertDatabaseConnection();
        PreparedStatement stmt = conn.prepareStatement(QUERY);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        return new Person(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"));
    }
    
    public List<Person> getPeople() throws SQLException {
        assertDatabaseConnection();
        ResultSet rs = conn.createStatement().executeQuery(ALL);
        List<Person> ps = new ArrayList<>();
        while (rs.next()) {
            Person p = new Person(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"));
            ps.add(p);
        }
        return ps;
    }
    
    public int getCount() {
        assertDatabaseConnection();
        try (ResultSet rs = conn.createStatement().executeQuery(COUNT)) {
            rs.next();
            return rs.getInt("total");
        } catch (SQLException ex) {
            return -1;
        }
    }
}
