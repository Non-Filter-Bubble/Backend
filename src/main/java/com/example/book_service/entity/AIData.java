//package com.example.book_service.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//public class AIData {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ElementCollection
//    private List<Integer> dataList;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "username", referencedColumnName = "username")
//    private UserEntity user;
//
//
//    public void setUsername(String username) {
//    }
//
//    // 생성자, Getter, Setter는 생략하였습니다. 필요에 따라 추가하세요.
//}
