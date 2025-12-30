package com.example.ORM_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerOptionRequestDto {
    private Long question_id;
    private String text;
}
