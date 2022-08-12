package com.ll.exam.sbb.answer;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.ll.exam.sbb.question.Question;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    // Answer : Question = N : 1
    @ManyToOne
    private Question question;
}