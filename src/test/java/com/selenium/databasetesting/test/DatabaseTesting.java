package com.selenium.databasetesting.test;

import com.selenium.databasetesting.base.BaseClass;
import com.selenium.databasetesting.page.Login;
import com.selenium.databasetesting.utility.CustomException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.*;

public class DatabaseTesting extends BaseClass {

    public Connection connection;
    public static int rowCount;
    public int noOfRowsAffected;
    public ResultSet resultSet;

    @Test(priority = 1)
    public void retrieve_table_data() throws SQLException {

        connection = this.getConnection();
        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from user");
        System.out.println("************************ Table Data *******************");
        System.out.println("id " + "    username    " + "            password  " + "     city   ");
        System.out.println("---------------------------------------------------------");
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String username = resultSet.getString(2);
            String password = resultSet.getString(3);
            String salary = resultSet.getString(4);
            System.out.println(id + "  " + username + "     " + password + "     " + salary);
            rowCount++;
            System.out.println("---------------------------------------------------------");
        }
    }

    @Test(priority = 2)
    public void insert_record_test() throws SQLException, CustomException {
        connection = this.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("insert ignore into user (username,password,city)values(?,?,?)");

        preparedStatement.setString(1, "diliprathod32@gmail.com");
        preparedStatement.setString(2, "Login@123");
        preparedStatement.setString(3, "Jalgaon");

        noOfRowsAffected = preparedStatement.executeUpdate();
        if (noOfRowsAffected == 0) {
            throw new CustomException(CustomException.ExceptionType.USERNAME_ALLREADY_EXIST, "Username is already exist please enter other username");
        } else {
            //assertion the query is executed or not
            Assert.assertEquals(noOfRowsAffected, 1);
        }

        retrieve_table_data();
    }

    @Test(priority = 3)
    public void update_record_whereProvidedCondition() {

        connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update user set city=? where id=?");
            preparedStatement.setString(1, "Surat");
            preparedStatement.setInt(2, 6);

            noOfRowsAffected = preparedStatement.executeUpdate();
            //assertion the query is executed or not
            //Assert.assertEquals(noOfRowsAffected, 1);
            retrieve_table_data();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 4)
    public void delete_row_from_employeeTable() throws SQLException {

        connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from user where id=?");
        preparedStatement.setInt(1, 2);
        noOfRowsAffected = preparedStatement.executeUpdate();
        retrieve_table_data();
    }

    @Test(priority = 5)
    public void login_to_application_using_DB_data() throws InterruptedException, SQLException, IOException {

        setUpBrowserLaunching();
        ResultSet resultSet;
        String gmailId;
        String password;
        connection = this.getConnection();
        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from user LIMIT 1");
        while (resultSet.next()) {
            Login login = new Login(driver);
            login.signInUser();

            //gmail and password from database employee table, store in string variable
            gmailId = resultSet.getString(2);
            password = resultSet.getString(3);

            login.setEmailId(gmailId);
            login.setPassword(password);
            login.clickLogin();
            String expectedEmail = "diliprathod32@gmail.com";
            Assert.assertEquals(gmailId, expectedEmail);
            driver.close();
        }
    }

    @Test(priority = 6)
    public void delete_entire_table_data() throws SQLException {

        connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from employee");
        noOfRowsAffected = preparedStatement.executeUpdate();
        retrieve_table_data();
    }

}
