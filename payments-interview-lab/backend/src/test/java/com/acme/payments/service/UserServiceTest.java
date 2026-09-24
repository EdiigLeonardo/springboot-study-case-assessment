package com.acme.payments.service;
import com.acme.payments.domain.User;
import com.acme.payments.repository.UserRepository;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
class UserServiceTest {
 @Test void filtersUsersWithAge30(){
  UserRepository repo=mock(UserRepository.class);
  when(repo.findAll()).thenReturn(List.of(new User("Ana",30,"a@x"),new User("Bob",31,"b@x")));
  assertThat(new UserService(repo).usersExactly30()).extracting(User::getName).containsExactly("Ana");
 }
 @Test void shouldDeduplicateNamesIgnoringCase(){
  UserRepository repo=mock(UserRepository.class);
  when(repo.findAll()).thenReturn(List.of(new User("Ana",30,"a@x"),new User("ANA",25,"b@x"),new User("Bob",31,"c@x")));
  assertThat(new UserService(repo).uniqueNamesCaseInsensitiveSlow()).hasSize(2);
 }
}
