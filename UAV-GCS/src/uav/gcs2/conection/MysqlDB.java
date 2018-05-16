package uav.gcs2.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDB {
    
    public Connection conn = null;
    public boolean status;
    
    String host = "";
    String ip = "192.168.205.105";
    String port = "3306";
    String user = "root";
    String pwd = "vertrigo";//"root"
    String nomeBD = "od";
    
    public MysqlDB(){
	host = "jdbc:mysql://"+ip+":"+port +"/"+nomeBD+"?user="+user+"&password="+pwd;
	status = false;
    }
        
    public MysqlDB(String hosttmp){
        host = hosttmp;
	status = false;
    }
    
    public MysqlDB(String ip, String port){
	host = "jdbc:mysql://"+ip+":"+port +"/"+nomeBD+"?user="+user+"&password="+pwd;
	status = false;
    }
    
    public void connect() throws SQLException{ 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host);
	    status = true;
	    System.out.println("A conexao foi um sucesso");
	} catch (ClassNotFoundException e) {
            System.out.println("Excecao classe nao encontrada");
	    e.printStackTrace();
	} catch(SQLException e) {
            System.out.println("SQL Exception... Nao conectado");
	    e.printStackTrace();
	}
    }
    
    public void disconnect() throws SQLException{
        conn.close();
        status = false;
        System.out.println("Fechando conexao");
    }
    
    public boolean isConnected(){
        return status;
    }
}
