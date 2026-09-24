package com.acme.payments.service;
import com.acme.payments.domain.User;
import com.acme.payments.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo){this.repo=repo;}

    public List<User> usersExactly30(){
        // Interview exercise: rewrite/explain lambda, method references and stream laziness.
        return repo.findAll().stream().filter(u -> u.getAge() == 30).collect(Collectors.toList());
    }

    public Set<User> uniqueUsersByNameWrong(){
        // INTENTIONAL BUG: relies on User.equals/hashCode contract that is inconsistent for persistence lifecycle.
        return new HashSet<>(repo.findAll());
    }

    public List<String> uniqueNamesCaseInsensitiveSlow(){
        List<String> out=new ArrayList<>();
        for(User u: repo.findAll()){
            boolean exists=out.stream().anyMatch(n -> n.equalsIgnoreCase(u.getName()));
            if(!exists) out.add(u.getName());
        }
        return out;
    }
}
