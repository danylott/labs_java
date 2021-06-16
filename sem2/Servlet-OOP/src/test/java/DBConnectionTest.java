import com.example.Servlet_OOP.Card;
import com.example.Servlet_OOP.DBConnection;
import com.example.Servlet_OOP.User;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    private final int sum = 20;

    private User generateUser() {
        return new User(generateStr(), generateStr(), generateStr(), generateStr());
    }

    private String generateStr() {
        byte[] array = new byte[4];
        new Random().nextBytes(array);
        return Base64.getEncoder().encodeToString(array);
    }

    @Test
    public void createConnection() throws SQLException, ClassNotFoundException, InterruptedException {
        Connection connection = DBConnection.createConnection();
        assertNotNull(connection);
        connection.close();
    }

    @Test
    public void testAddDeleteUser() {
        User user = generateUser();
        assertTrue(DBConnection.addUser(user));

        int id = DBConnection.checkUser(user.getUsername(), user.getPassword());
        assertNotEquals(id, -1);

        DBConnection.deleteUser(id);
        id = DBConnection.checkUser(user.getUsername(), user.getPassword());
        assertEquals(id, -1);
    }

    @Test
    public void testAddDeleteCard() {
        User user = generateUser();
        DBConnection.addUser(user);
        int userId = DBConnection.checkUser(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        DBConnection.addCard(userId, cardName);
        int cardId = DBConnection.getCardId(userId, 0);

        List<Card> cards = DBConnection.getCards(userId);
        assertEquals(cards.size(), 1);
        assertEquals(cards.get(0).getName(), cardName);

        DBConnection.deleteCard(cardId);
        cardId = DBConnection.getCardId(userId, 0);
        assertEquals(cardId, -1);

        DBConnection.deleteUser(userId);
    }

    @Test
    public void testGetCards(){
        User user = generateUser();
        DBConnection.addUser(user);
        int userId = DBConnection.checkUser(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        DBConnection.addCard(userId, cardName);
        List<Card> cards = DBConnection.getCards(userId);
        assertEquals(cards.size(),1);

        cardName = generateStr();
        DBConnection.addCard(userId, cardName);
        cards = DBConnection.getCards(userId);
        assertEquals(cards.size(),2);

        int cardId = DBConnection.getCardId(userId, 0);
        int cardId2 = DBConnection.getCardId(userId, 1);

        DBConnection.deleteCard(cardId);
        DBConnection.deleteCard(cardId2);
        DBConnection.deleteUser(userId);
    }

    @Test
    public void testAddDeletePayment() {
        User user = generateUser();
        String comment = generateStr();
        DBConnection.addUser(user);
        int userId = DBConnection.checkUser(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        DBConnection.addCard(userId, cardName);
        int cardId = DBConnection.getCardId(userId, 0);
        DBConnection.addPayment(userId, 0, sum, comment);

        List<Card> cards = DBConnection.getCards(userId);

        assertEquals(comment, cards.get(0).getPayments().get(0).getComment());
        assertEquals(sum, cards.get(0).getPayments().get(0).getPay());

        DBConnection.deletePayments(cardId);
        DBConnection.deleteCard(cardId);
        cardId = DBConnection.getCardId(userId, 0);
        assertEquals(cardId, -1);

        DBConnection.deleteUser(userId);
    }

    @Test
    public void testSetBlocked() {
        User user = generateUser();
        DBConnection.addUser(user);
        int userId = DBConnection.checkUser(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        DBConnection.addCard(userId, cardName);
        int cardId = DBConnection.getCardId(userId, 0);

        List<Card> cards = DBConnection.getCards(userId);
        DBConnection.setBlocked(userId, cards.get(0).getAccount().getId(), true);

        assertFalse(cards.get(0).isBlocked());
        cards = DBConnection.getCards(userId);
        assertTrue(cards.get(0).isBlocked());

        DBConnection.deleteCard(cardId);
        DBConnection.deleteUser(userId);
    }

    @Test
    public void testGetUsers(){
        User user1 = generateUser(); DBConnection.addUser(user1);
        int userId1 = DBConnection.checkUser(user1.getUsername(), user1.getPassword());
        User user2 = generateUser(); DBConnection.addUser(user2);
        int userId2 = DBConnection.checkUser(user2.getUsername(), user2.getPassword());

        List<User> users=DBConnection.getUsers();

        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));


        DBConnection.deleteUser(userId1);
        DBConnection.deleteUser(userId2);
    }
}