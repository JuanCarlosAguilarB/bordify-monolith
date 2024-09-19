package com.bordify.service;

import com.bordify.services.JwtService;
import com.bordify.shared.infrastucture.controlles.UnitTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static com.bordify.shared.domain.FactoryValues.generateRandomAlphanumeric;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

public class JwtServiceShould extends UnitTestBaseClass {


    // is important note that this test aren't unit test because we are coupling to dependency, jwt
    // so, we are working with integration test

    @InjectMocks
    JwtService service;

    @DisplayName("should generate token")
    @Test
    public void shouldGenerateToken(){

        int expirationInDays = generateRandomValue(5,20);
        String username = generateRandomAlphanumeric(generateRandomValue(5,20));

        String token = service.generateToken(expirationInDays, username);

        System.out.println(token);

        Assertions.assertNotNull(token);
        Assertions.assertNotEquals(token,"");
        Assertions.assertNotEquals(token," ");

    }


    @DisplayName("should generate access token")
    @Test
    public void shouldGenerateAccessToken(){

        String username = generateRandomAlphanumeric(generateRandomValue(5,20));

        String token = service.getAccessToken(username);

        System.out.println(token);

        Assertions.assertNotNull(token);
        Assertions.assertNotEquals(token,"");
        Assertions.assertNotEquals(token," ");

    }


    @DisplayName("should generate refresh token")
    @Test
    public void shouldGenerateRefreshToken(){

        String username = generateRandomAlphanumeric(generateRandomValue(5,20));

        String token = service.getRefreshToken(username);

        System.out.println(token);

        Assertions.assertNotNull(token);
        Assertions.assertNotEquals(token,"");
        Assertions.assertNotEquals(token," ");

    }

    @DisplayName("should retrieve userName from token")
    @Test
    public void shouldRetrieveUserNameFromToken(){

        String username = generateRandomAlphanumeric(generateRandomValue(5,20));
        String token = service.getRefreshToken(username);

        String UserNameRetrieve = service.getUsernameFromToken(token);

        Assertions.assertNotNull(UserNameRetrieve);
        Assertions.assertEquals(UserNameRetrieve,username);

    }

    @DisplayName("shouldn't retrieve userName from void or null token")
    @Test
    public void shouldNotRetrieveUserNameFromNoValidToken(){

        String nonAToken = generateRandomAlphanumeric(generateRandomValue(5,20));

        Assertions.assertThrows(Exception.class, ()->{
            service.getUsernameFromToken(null);
            service.getUsernameFromToken("");
            service.getUsernameFromToken(" ");
            service.getUsernameFromToken(nonAToken);

        });

    }

    @DisplayName("should validate a token")
    @Test
    public void shouldValidateAToken(){

        String username = generateRandomAlphanumeric(generateRandomValue(5,20));
        String token = service.getRefreshToken(username);

        UserDetails userDetails = getUserDetails(username);

        boolean isValidToken = service.isValidToken(token,userDetails);

        Assertions.assertTrue(isValidToken);

    }


    @DisplayName("should not validate a token")
    @Test
    public void shouldNotValidateAToken(){

        String username = generateRandomAlphanumeric(generateRandomValue(5,20));
        String token = service.getRefreshToken(username);

        Assertions.assertFalse(service.isValidToken(token, getUserDetails(null)));
        Assertions.assertFalse(service.isValidToken(token, getUserDetails("")));
        Assertions.assertFalse(service.isValidToken(token, getUserDetails(" ")));

    }

    private UserDetails getUserDetails(String username){
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }

}
