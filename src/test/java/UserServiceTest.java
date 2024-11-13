import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.app.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    // Mock the repository or DAO layer if present
    @Mock
    private UserRepository userRepository;

    // UserService with mocked dependencies
    @InjectMocks
    private UserService userService;

    private User testUser;

    private Long id = 1L;
    private String firstName = "Taras";
    private String secondName = "Shepel";
    private String email = "shepel.taras@ukr.net";

    private String firstNameUpdated = "Rick";

    @BeforeEach
    public void setUp() {
        
        // Mockito setup
        MockitoAnnotations.openMocks(this);

        testUser = new User(id, firstName, secondName, email);
    }

    @Test
    public void testCreateUser() throws SuchUserAlreadyExistsException {
        // behaviour
        User user = new User(id, firstName, secondName, email);
        when(userRepository.create(argThat(userArg -> !userArg.equals(testUser)))).thenReturn(null);
        when(userRepository.create(testUser)).thenReturn(Optional.of(user));

        User createdUser = userService.createUser(testUser);

        verify(userRepository, times(1)).create(testUser);

        assertEquals(testUser.getId(), createdUser.getId());
        assertEquals(testUser.getFirstName(), createdUser.getFirstName());
        assertEquals(testUser.getSecondName(), createdUser.getSecondName());
        assertEquals(testUser.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testGetUserById() throws NoSuchUserException {
        // expected
        User user = new User(id, firstName, secondName, email);
        when(userRepository.getById(argThat(id -> id != 1L))).thenReturn(null);
        when(userRepository.getById(id)).thenReturn(Optional.of(user));

        // callby id
        User foundUser = userService.getUserById(id);

        verify(userRepository, times(1)).getById(id);

        assertEquals(testUser.getId(), foundUser.getId());
        assertEquals(testUser.getFirstName(), foundUser.getFirstName());
        assertEquals(testUser.getSecondName(), foundUser.getSecondName());
        assertEquals(testUser.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testUpdateUser() throws NoSuchUserException {
        User user = new User(id, firstNameUpdated, secondName, email);
        when(userRepository.update(argThat(userArg -> !userArg.equals(testUser)))).thenReturn(null);
        when(userRepository.update(testUser)).thenReturn(Optional.of(user));

        testUser.setFirstName(firstNameUpdated);

        User updatedUser = userService.updateUser(testUser);

        verify(userRepository, times(1)).update(testUser);

        assertEquals(testUser.getId(), updatedUser.getId());
        assertEquals(testUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(testUser.getSecondName(), updatedUser.getSecondName());
        assertEquals(testUser.getEmail(), updatedUser.getEmail());
    }
}
