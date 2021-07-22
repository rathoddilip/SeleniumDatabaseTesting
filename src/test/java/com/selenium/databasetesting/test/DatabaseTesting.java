package com.selenium.databasetesting.test;

import com.selenium.databasetesting.base.BaseClass;
import com.selenium.databasetesting.page.Login;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.*;

public class DatabaseTesting extends BaseClass {

    public Connection connection;
    public static int rowCount;
    public int noOfRowsAffected;

    @Test(priority = 1)
    public void getTableData() throws SQLException {

        connection = this.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from employee");
        System.out.println("************************ Table Data *******************");
        System.out.println("id " + "    username    " + "            password  " + "     salary   ");
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
    public void insert_RecordTest() throws SQLException {

        connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into employee (username,password,salary)values(?,?,?)");

        preparedStatement.setString(1, "arjun@gmail.com");
        preparedStatement.setString(2, "pass@123");
        preparedStatement.setString(3, "45000");
        preparedStatement.executeUpdate();

        noOfRowsAffected = preparedStatement.executeUpdate();
        //assertion the query is executed or not
        Assert.assertEquals(noOfRowsAffected, 1);
        getTableData();
    }

    @Test(priority = 3)
    public void update_record_whereProvidedCondition() {

        connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update employee set salary=? where id=?");
            preparedStatement.setString(1, "550000");
            preparedStatement.setInt(2, 3);

            noOfRowsAffected = preparedStatement.executeUpdate();
            //assertion the query is executed or not
            Assert.assertEquals(noOfRowsAffected, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 4)
    public void delete_row_from_employeeTable() throws SQLException {

        connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("delete from employee where id=?");
        preparedStatement.setInt(1, 5);
        preparedStatement.executeUpdate();
    }

    @Test(priority = 5)
    public void login_to_application_using_DB_data() throws InterruptedException, SQLException, IOException {

        setUpBrowserLaunching();
        ResultSet resultSet;
        String gmailId;
        String password;
        connection = this.getConnection();
        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from employee LIMIT 1");
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

}
