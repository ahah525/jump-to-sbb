package com.ll.exam.sbb.question;

import com.ll.exam.sbb.answer.Answer;
import com.ll.exam.sbb.user.SiteUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    // Question : Answer = 1 : N
    // DB에 컬럼이 생기지 않고 클래스에만 씀(DB 컬럼에는 정보를 1개만 넣을 수 있기 때문)
    // Question 에서 Answer로 쉽게 접근 가능하도록(굳이 안해도 됨)
    // CascadeType.Remove: Question 삭제하면 Answer 자동 삭제됨
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answerList = new ArrayList<>();

    @ManyToOne
    private SiteUser author;

    // Question : Answer = m : n
    @ManyToMany
    Set<SiteUser> voter;    // 추천인

    public void addAnswer(Answer answer) {
        answer.setQuestion(this);
        getAnswerList().add(answer);
    }
}