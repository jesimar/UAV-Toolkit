package uav.gcs.conection.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class MysqlQuery {
    
    public Connection database = null; 
    public boolean status;
    public ResultSet rs;  // Contem o conjunto de dados retornado por uma consulta SQL
    public Statement stm; // Controla e executa uma instrucao SQL

    public MysqlQuery(Connection db) {
        try {
            database = db;
            stm = database.createStatement();
            status = false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.print("Error create Query!");
        }
    }
    
    /*
     * Utilizado quando deseja acessar os dados da consulta
     */
    public void open(String sql) {
        try {
            rs = stm.executeQuery(sql);
            status = true;
	} catch (SQLException ex) {
            status = false;
            System.out.print("Error exec Query!");
            ex.printStackTrace();
	}
    }
    
    /*
     * Quando a instrução retorna true ou false
     */
    public void execute(String sql) {
        try {
            stm.execute(sql);
            status = true;
	} catch (SQLException ex) {
            status = false;
            System.out.print("Error exec Query!");
            ex.printStackTrace();
	}
    }
    
    public String fieldByName(String field) {
        String value = null;
	try {
            if (rs.getString(1) != null)
                status = false;
            if (status)
                rs.next();
            value = rs.getString(field);
            status = true;
        } catch (SQLException ex) {
            System.out.println("Field invalid!");
	}
	return value;
    }
    
    /*
     * Utilizado em consultas SELECT
     */
    public ResultSet result(String sql) {
        try {
            rs = stm.executeQuery(sql);
            return rs;
	} catch (SQLException ex) {
            ex.printStackTrace();
	}
	return null;
    }
    
    /*
     * Retorna quantas registros a consulta retornou
     */
    public int recordCount() {
        int tot = 0;
	try {
            rs.first();
            while (rs.next())
                tot++;
	} catch (SQLException ex) {
            status = false;
            System.out.print("Error exec Query!");
            ex.printStackTrace();
	}
	return tot;
    }
    
    /*
     * Percorrer retorno da instrução
     */
    public boolean next() {
        boolean result = false;
	try {
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException ex) {
            System.out.print("Error exec Query!");
        }
	return result;
    }
    
    /*
     * Retornar um indice quando esta percorrendo
     */
    public boolean prev() { 
	boolean result = false;
	try {
            if (rs.previous()) {
                result = true;
            }
	} catch (SQLException ex) {
            System.out.print("Error exec previous!");
        }
	return result;
    }
    
    public boolean first() {
        boolean result = false;
	try {
            if (rs.first()) {
                result = true;
            }
	} catch (SQLException ex) {
            System.out.print("Error exec first!");
	}
	return result;
    }
    
    public boolean last() {
        boolean result = false;
	try {
            if (rs.last()) {
                result = true;
            }
	} catch (SQLException ex) {
            System.out.print("Error exec last!");
	}
	return result;
    }
}

