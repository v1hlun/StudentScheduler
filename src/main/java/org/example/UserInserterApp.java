package org.example;

import org.example.Repository.UserRepository;
import org.example.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@SpringBootApplication
public class UserInserterApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UserInserterApp.class, args);
        UserInserter inserter = context.getBean(UserInserter.class);
        inserter.insertUser();
    }
}

@Component
class UserInserter {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInserter(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void insertUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите логин: ");
        String username = scanner.nextLine();

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        userRepository.save(user);
        System.out.println("Пользователь сохранён в БД!");
    }
}
