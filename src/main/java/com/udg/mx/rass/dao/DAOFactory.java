package com.udg.mx.rass.dao;


import java.sql.SQLException; //excepciones
import javax.naming.NamingException; //excepciones
import java.util.Map; //(para usar convertObjToParam)

import java.sql.CallableStatement; //permite la utilización de sentencias SQL
import java.sql.Connection; //para obtener la conexion
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList; //arreglos
import java.util.List; //listas

import javax.naming.InitialContext; //para obtener la conexion
import javax.sql.DataSource; //para obtener la conexion



public abstract class DAOFactory<T> {

    //llamada a Base de datos configurada en wildfly
	private static final String DATASOURCE_CONTEXT = "java:jboss/datasources/AlumnoDS";
	//variables para realizar consultas a la base de datos. Definidas como final que indica que una vez definido el valor no se puede modificar.
	//lo que permanece constante es la asignación de un objeto a esa variable, 
    private final String SQL_ALL;
    private final String SQL_INSERT;
    private final String SQL_UPDATE;
    private final String SQL_DELETE;

    //iniciando el constructor con las variables en letras minusculas
    public DAOFactory(String sql_all, String sql_insert, String sql_update, String sql_delete) {
        this.SQL_ALL = sql_all;
        this.SQL_INSERT = sql_insert;
        this.SQL_UPDATE = sql_update;
        this.SQL_DELETE = sql_delete;
    }

//Metodos con la ejecucion de las funciones que haran con sus Exception definidas en caso de que falle una consulta
//se crea una variable de tipo "T"(generica) se va insertar una valor real en la implementacion de este DAO 
    //(Clase objeto) aqui va a recibir una clase y un objeto
    public int insert(T object) throws DAOException {
        try {
			//ejecuta SQL_INSERT y convierte Objeto (en este caso alumno de la Clase Alumno) a parametro y los envia a la
			//funcion executeUpdate
            return executeUpdate(SQL_INSERT, convertObjToParam(object));
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    public int update(T object) throws DAOException {
        try {
            return executeUpdate(SQL_UPDATE, convertObjToParam(object));
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    public int delete(T object) throws DAOException {
        try {
            return executeUpdate(SQL_DELETE, convertObjToParam(object));
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    public T find(String sql, Object... values) throws DAOException {
        T object = null;
        try {
            object = executeQuery(sql, values);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
        return object;
    }

    public List<T> findAll() throws DAOException {
		//Ejecuta directo la consulta definida en FamiliaImplDAO
        return list(SQL_ALL);
    }

    public List<T> list(String sql, Object... values) throws DAOException {
        List<T> list = null;
        try {
            list = executeQueryList(sql, values);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
        return list;
    }

    public Object singleResult(String sql, Object... values) throws DAOException {
        Object object = null;
        try {
            object = executeQuerySingleResult(sql, values);
        } catch (NamingException | SQLException e) {
            throw new DAOException(e);
        }
        return object;
    }

//recibe los valores de los metodos insert, update y delete,sentencia sql a ejecutar y el objeto
//realiza la conexion a la base de datos
    public int executeUpdate(String sql, Map<String, Object> params) throws SQLException, NamingException {
        
		Connection connection = null;
        //CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados. 
		//Los procedimientos almacenados son programas que tienen una interfaz de base de datos
		CallableStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareCall(sql);
			//recolecta la informacion y la envia a la funcion setParams
            setParams(statement, params, sql);
            return statement.executeUpdate();
        } finally {
			
			//cerramos resultSet, statement y connection para evitar dejar basura
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

//recibe los valores del metodo find, extrae el cursor de la base de datos
    private T executeQuery(String sql, Object... values) throws SQLException, NamingException {
        //crea una variable de tipo list y ejecuta executeQueryList pasandole los 2 parametros
		//luego devuelve la lista
		List<T> list = executeQueryList(sql, values);
        return list.size() > 0 ? list.get(0) : null;
    }
//recibe los valores de excuteQuery
    private List<T> executeQueryList(String sql, Object... values) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<T> list = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            setValues(statement, values);
            resultSet = statement.executeQuery();

            list = new ArrayList<>();
            while (resultSet.next()) {
                T object = convertDbToOjb(resultSet);
                list.add(object);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }
//recibe los valores del metodo singleResult
    private Object executeQuerySingleResult(String sql, Object... values) throws SQLException, NamingException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Object object = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            setValues(statement, values);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                object = resultSet.getObject(1);
            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return object;
    }

    //Funcion que obtiene la conexion
	private Connection getConnection() throws NamingException, SQLException {
        InitialContext initialContext = new InitialContext();
        DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
        return datasource.getConnection();
    }

//recibe los valores desde singleResult     
    private void setValues(PreparedStatement statement, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
    }

//recibe de la funcion executeUpdate los parametros para permitir la ejecucion de las consultas (CallableStatement)
//el objeto y la sentencia sql
    private void setParams(CallableStatement statement, Map<String, Object> values, String sql) throws SQLException {
        String[] split = sql.replaceAll(",", " ").split(" ");
        List<String> nameParams = new ArrayList<>();

        for (String string : split) {
			//startsWith se usa para verificar el prefijo de una cadena. Devuelve un valor booleano verdadero o falso 
			//basado en si la cadena dada comienza con la letra o palabra especificada.
            if (string.startsWith(":")) {
                nameParams.add(
                        string.
                        replaceAll(",", "").
                        replaceAll(":", "").
                        replaceAll("\\)", "").
                        replaceAll(";", ""));
            }
        }
        for (String string : nameParams) {
            Object object = values.get(string);
            //System.out.println("Key:" + string + "->Value:" + object.toString());
            statement.setObject(string, object);
        }
    }

    public java.sql.Timestamp convertDate(java.util.Date dateUtil) {
        return new java.sql.Timestamp(dateUtil.getTime());
    }
	//Metodo abstractp
	//se obliga a que todas las clases derivadas lo sobrescriban con el mismo formato utilizado en la declaración
    abstract Map<String, Object> convertObjToParam(T object);

    abstract T convertDbToOjb(ResultSet resultSet) throws SQLException;
}
