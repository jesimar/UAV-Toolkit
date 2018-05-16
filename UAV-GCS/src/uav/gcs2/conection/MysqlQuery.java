package uav.gcs2.conection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlQuery {
    
    public Connection database = null; 
    public boolean status;
    public ResultSet rs; // Contem o conjunto de dados retornado por uma consulta SQL
    public Statement stm; // Controla e executa uma instrucao SQL

    public MysqlQuery(Connection db) {
        try {
            database = db;
            stm = database.createStatement();
            status = false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Erro ao criar Query!");
        }
    }
    
    public void open(String sql) { // Utilizado quando deseja acessar os dados da consulta
        try {
            rs = stm.executeQuery(sql);
            status = true;
	} catch (SQLException e) {
            status = false;
            System.out.print("Erro ao executar Query!");
            e.printStackTrace();
	}
    }
    
    public void execute(String sql) { // Quando a instrucao retorna true ou false
        try {
            stm.execute(sql);
            status = true;
	} catch (SQLException e) {
            status = false;
            System.out.print("Erro ao executar Query!");
            e.printStackTrace();
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
        } catch (SQLException e) {
            System.out.println("Field invalido!");
	}
	return value;
    }
    
    public ResultSet result(String sql) { // Utilizado em consultas SELECT
        try {
            rs = stm.executeQuery(sql);
            return rs;
	} catch (SQLException eSQL) {
            eSQL.printStackTrace();
	}
	return null;
    }
    
    public int recordCount() { // Retorna quantas registros a consulta retornou
        int tot = 0;
	try {
            rs.first();
            while (rs.next())
                tot++;
	} catch (SQLException e) {
            status = false;
            System.out.print("Erro ao executar Query!");
            e.printStackTrace();
	}
	return tot;
    }
    
    public boolean next() { // Percorrer retorno da instrucao
        boolean result = false;
	try {
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            System.out.print("Erro ao executar Query!");
        }
	return result;
    }
    
    public boolean prev() { // Retornar um indice quando esta percorrendo
	boolean result = false;
	try {
            if (rs.previous()) {
                result = true;
            }
	} catch (SQLException e) {
            System.out.print("Erro ao executar previous!");
        }
	return result;
    }
    
    public boolean first() {
        boolean result = false;
	try {
            if (rs.first()) {
                result = true;
            }
	} catch (SQLException e) {
            System.out.print("Erro ao executar first!");
	}
	return result;
    }
    
    public boolean last() {
        boolean result = false;
	try {
            if (rs.last()) {
                result = true;
            }
	} catch (SQLException e) {
            System.out.print("Erro ao executar last!");
	}
	return result;
    }
}

