//package com.bordify.events;
//
//import com.bordify.dtos.RabbitMessage;
//import lombok.Getter;
//
//import java.time.LocalTime;
//import java.util.UUID;
//
//@Getter
//public class CreateUserDomainEvent extends RabbitMessage {
//
//    private UUID id;
//    private String username;
//    private String email;
//
//    private String firstName;
//
//    private String lastName;
//
//    private Boolean isVerified = false;
//
//    private String phoneNumber;
//
//    private LocalTime created;
//
//    private LocalTime lastLogin;
//
//    public CreateUserDomainEvent(
//            String id,  String username,
//            String email, String password, String firstName,
//            String lastName, Boolean isVerified,
//            String phoneNumber, LocalTime created, LocalTime lastLogin) {
//
//        super("user.1.event.user.created");
//
//        this.id = UUID.fromString(id);
//        this.username = username;
//        this.email = email;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.isVerified = isVerified;
//        this.phoneNumber = phoneNumber;
//        this.created = created;
//        this.lastLogin = lastLogin;
//    }
//
////
//
//}
