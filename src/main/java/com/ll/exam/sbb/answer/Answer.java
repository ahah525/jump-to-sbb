package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.user.SiteUser;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    // Answer : Question = N : 1
    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    // Answer : SiteUser = m : n
    @ManyToMany
    private Set<SiteUser> voter;    // 추천인
}