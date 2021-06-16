package com.example.Servlet_OOP;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class DBConnection {

    private static final Logger log = Logger.getLogger(DBConnection.class.getName());
//    public static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    public static Connection createConnection() throws SQLException, ClassNotFoundException, InterruptedException {
        Class.forName("org.postgresql.Driver");
//        System.out.println("Driver version: " + org.postgresql.Driver.getVersion());

        String jdbcUrl = "jdbc:postgresql://balarama.db.elephantsql.com:5432/tjnttvbp";
        String user = "tjnttvbp";
        String pass = "qaUTEcBGLLftne9QMvRYySo1CJKUVNxR"; // super secure

        return DriverManager.getConnection(jdbcUrl, user, pass);
//        return connectionPool.getConnection();
    }

    private static void run(String... sqls) {
        Connection connection = null;
        try {

            connection = createConnection();
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            for (String sql : sqls) {
                statement.executeUpdate(sql);
            }
            // connectionPool.releaseConnection(connection);
        } catch (SQLException | ClassNotFoundException | InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static int getId(String sql) {
        Connection connection = null;
        try {
            connection = createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int res = resultSet.getInt(1);
                connection.close();
                return res;
            }
            // connectionPool.releaseConnection(connection);
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        return -1;

    }

    public static boolean addUser(User user) {
        String sql = "INSERT INTO Clients (CName, CSURNAME, CUSERNAME, CPASSWORD)" +
                " VALUES ('" + user.getFirstName() + "', '" + user.getSecondName() + "', '" + user.getUsername() + "', '" + user.getPassword() + "')";
        run(sql);
        log.info("Add user ");
        return true;
    }

    public static void deleteUser(int userId) {
        String sql = "DELETE FROM Clients WHERE CLIENTID=" + userId;
        run(sql);
        log.info("delete user " + userId);
    }

    public static void addCard(int userId, String cardName) {
        String sql = "INSERT INTO ACCOUNT (BALANCE)" +
                " VALUES (" + 0 + ")";
        String sql2 = "INSERT INTO CARDS (CLIENTID ,ACCOUNTID, CARDNAME)" +
                " VALUES (" + userId + ", currval('ACCOUNT_ACCOUNTID_SEQ') " + ", '" + cardName + "')";
        run(sql, sql2);
        log.info("add card");
    }

    public static void deleteCard(int cardId) {
        String sql = "DELETE FROM CARDS WHERE ACCOUNTID=" + cardId;
        String sql2 = "DELETE FROM ACCOUNT WHERE ACCOUNTID=" + cardId;
        run(sql, sql2);
        log.info("delete card " + cardId);
    }

    public static void addPayment(int userId, int cardNo, int sum, String comment) {
        int cardId = getCardId(userId, cardNo);
        String sql = "INSERT INTO Payment (accountId,pay, commentt, paymentDate) " +
                    " VALUES (" + cardId + "," + sum + ",'" + comment + "', CURRENT_DATE)";
        String sql2 = "UPDATE ACCOUNT  " +
                " SET BALANCE =BALANCE +" + sum +
                " WHERE ACCOUNTID=" + cardId;
        addToPayment(cardId, sum);
        run(sql, sql2);
        log.info("add payment");
    }

    private static void addToPayment(int cardId, int sum) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String startDay = formatter.format(new Date());

        addPaymentMonth(cardId, startDay);
        String sql = "UPDATE PAYMENT_MONTH " +
                "SET ";
        if (sum > 0) {
            sql = sql.concat("replenishmentSum = " + sum + " + replenishmentSum");
        } else {
            sql = sql.concat("paymentSum = " + sum + " + paymentSum");
        }
        sql = sql.concat(" WHERE accountID=" + cardId + " AND firstDate=TO_DATE('" + startDay + "', 'yyyy-MM')");
        run(sql);
    }

    public static void addPaymentMonth(int accountID, String startDay) {
        String sql = "SELECT accountID FROM PAYMENT_MONTH " +
                "WHERE accountID = " + accountID + " AND firstDate = TO_DATE('" + startDay + "', 'yyyy-MM')";
        System.out.println(sql);
        if (getId(sql) == -1) {
            sql = "INSERT INTO PAYMENT_MONTH (firstDate, accountId, paymentSum, replenishmentSum) " +
                    "VALUES (TO_DATE('" + startDay + "', 'yyyy-MM'), " + accountID + ", 0, 0)";
            run(sql);
        }
    }

    public static void deletePayments(int accountId) {
        String sql = "DELETE FROM Payment " +
                "WHERE accountId = " + accountId;
        run(sql);
        log.info("delete payments id=" + accountId);
    }

    public static int getCardId(int userId, int cardNo) {
        int result = -1;
        String sql = "SELECT  ACCOUNTID " +
                "FROM CARDS " +
                "WHERE CLIENTID=" + userId;
        Statement statement = null;
        Connection connection = null;
        try {

            connection = createConnection();
            statement = connection.createStatement();
            ResultSet userSet = statement.executeQuery(sql);
            while (userSet.next()) {
                if (cardNo == 0) {
                    result = userSet.getInt(1);
                }
                cardNo--;
            }
            // connectionPool.releaseConnection(connection);
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void setBlocked(int userID, int cardNo, boolean blocked) {
        String sql = "UPDATE CARDS " +
                "SET BLOCKED= " + (blocked ? 1 : 0) +
                " WHERE ACCOUNTID=" + cardNo;
        run(sql);
        log.info("set card " + cardNo + "blocked = " + blocked);
    }

    public static int checkUser(String username, String password) {
        String sql = "SELECT  CLIENTID " +
                "FROM CLIENTS " +
                "WHERE CUSERNAME='" + username + "' AND CPASSWORD='" + password + "'";
        return getId(sql);
    }

    public static List<User> getUsers() {
        List<User> users = new LinkedList<>();
        String sql = "SELECT  CLIENTID, CUSERNAME " +
                "FROM CLIENTS ";
        Connection connection = null;
        try {
            connection = createConnection();
            Statement statement = connection.createStatement();
            ResultSet userSet = statement.executeQuery(sql);
            while (userSet.next()) {

                users.add(new User(userSet.getString("CUSERNAME"), getCards(userSet.getInt("CLIENTID"))));

            }
            // connectionPool.releaseConnection(connection);
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return users;
    }


    public static List<Card> getCards(int userID) {
        String sql = "SELECT  ACCOUNTID, BALANCE, CARDNAME,BLOCKED " +
                "FROM CARDS INNER JOIN ACCOUNT USING (ACCOUNTID) " +
                "WHERE CLIENTID=" + userID;

        List<Card> cards = new LinkedList<>();
        Connection connection = null;
        try {
            connection = createConnection();
            Statement statement = connection.createStatement();
            ResultSet cardSet = statement.executeQuery(sql);
            while (cardSet.next()) {
                int balance = cardSet.getInt("BALANCE");
                int accountId = cardSet.getInt("ACCOUNTID");
                String cardName = cardSet.getString("CARDNAME");
                boolean blocked = cardSet.getInt("BLOCKED") == 1;

                sql = "SELECT  PAY, COMMENTT " +
                        "FROM PAYMENT " +
                        "WHERE ACCOUNTID=" + accountId;
                Statement statement2 = connection.createStatement();
                ResultSet paymentSet = statement2.executeQuery(sql);
                List<Payment> payments = new ArrayList<>();
                while (paymentSet.next()) {
                    int pay = paymentSet.getInt("PAY");
                    String comment = paymentSet.getString("COMMENTT");
                    payments.add(new Payment(pay, comment));
                }
                cards.add(new Card(accountId, blocked, cardName, balance, payments));
            }
            // connectionPool.releaseConnection(connection);
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return cards;
    }

    public static List<Card> getCards() {
        String sql = "SELECT  ACCOUNTID, CARDNAME " +
                "FROM CARDS INNER JOIN ACCOUNT USING (ACCOUNTID) ";
        List<Card> cards = new LinkedList<>();
        try {
            Connection connection = createConnection();
            Statement statement = connection.createStatement();
            ResultSet cardSet = statement.executeQuery(sql);
            while (cardSet.next()) {
                int accountId = cardSet.getInt("ACCOUNTID");
                String cardName = cardSet.getString("CARDNAME");

                cards.add(new Card(accountId, false, cardName, 0,null));
            }
            // connectionPool.releaseConnection(connection);
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public static List<Card> getCards(String startDay, String endDay) {
        String sql = "SELECT  ACCOUNTID, CARDNAME " +
                "FROM CARDS INNER JOIN ACCOUNT USING (ACCOUNTID) ";
        List<Card> cards = new LinkedList<>();
        try {
            Connection connection = createConnection();
            Statement statement = connection.createStatement();
            ResultSet cardSet = statement.executeQuery(sql);
            while (cardSet.next()) {
                int accountId = cardSet.getInt("ACCOUNTID");
                String cardName = cardSet.getString("CARDNAME");

                cards.add(new Card(accountId, false, cardName, 0,
                        getPayments(accountId, startDay, endDay)));
            }
            // connectionPool.releaseConnection(connection);
        } catch (SQLException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        return cards;
    }


    public static int getPaymentSumInMonth(int accountId, String startDay, String endDay) {
        String sql = "SELECT sum(pay) FROM payment " +
                "WHERE accountId=" + accountId + " AND pay<0 AND " +
                "paymentDate >= TO_DATE('" + startDay + "', 'yyyy-MM-dd') AND paymentDate < TO_DATE('" + endDay + "', 'yyyy-MM-dd')";
        return getId(sql);
    }

    public static int getReplenishmentSumInMonth(int accountId, String startDay, String endDay) {
        String sql = "SELECT sum(pay) FROM payment " +
                "WHERE accountId=" + accountId + "  AND pay>0 AND " +
                "paymentDate >= TO_DATE('" + startDay + "', 'yyyy-MM-dd') AND paymentDate < TO_DATE('" + endDay + "', 'yyyy-MM-dd')";
        return getId(sql);
    }


    public static List<Payment> getPayments(int accountId, String startDay, String endDay) {
        List<Payment> payments = new ArrayList<>();
        String startMonth = startDay.substring(0, 7);
        String endMonth = startDay.substring(0, 7);
        String sql1 = "SELECT sum(paymentSum) FROM PAYMENT_MONTH " +
                "WHERE accountId=" + accountId + " AND firstDate>=TO_DATE('" + startMonth + "', 'yyyy-MM') AND " +
                "firstDate<TO_DATE('" + endMonth + "', 'yyyy-MM')";
        String sql2 = "SELECT sum(replenishmentSum) FROM PAYMENT_MONTH " +
                "WHERE accountId=" + accountId + " AND firstDate>=TO_DATE('" + startMonth + "', 'yyyy-MM') AND " +
                "firstDate<TO_DATE('" + endMonth + "', 'yyyy-MM')";
        int paymentSum = getId(sql1);
        int replenishmentSum = getId(sql2);
        startMonth = startMonth.concat("-01");
        endMonth = endMonth.concat("-01");
        paymentSum -= getPaymentSumInMonth(accountId, startMonth, startDay);
        paymentSum += getPaymentSumInMonth(accountId, endMonth, endDay);
        replenishmentSum -= getReplenishmentSumInMonth(accountId, startMonth, startDay);
        replenishmentSum += getReplenishmentSumInMonth(accountId, endMonth, endDay);

        payments.add(new Payment(paymentSum, ""));
        payments.add(new Payment(replenishmentSum, ""));
        System.out.println("Payments" + payments.toString());
        return payments;

    }


}
