package com.example.ORM_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionResponseDto {
    private Long id;
    private Long test_id;
    private String text;
}
