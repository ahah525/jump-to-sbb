package com.ll.exam.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

// <어떤 Entity의 Repository인지, Key의 type>
public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
