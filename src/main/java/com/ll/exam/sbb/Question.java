package com.ll.exam.sbb;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // Question : Answer = 1 : N
    // DB에 컬럼이 생기지 않고 클래스에만 씀(DB 컬럼에는 정보를 1개만 넣을 수 있기 때문)
    // Question 에서 Answer로 쉽게 접근 가능하도록(굳이 안해도 됨)
    // CascadeType.Remove: Question 삭제하면 Answer 자동 삭제됨
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}