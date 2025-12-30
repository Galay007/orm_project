package com.example.ORM_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubmissionResponseDto {
    private Long id;
    private Long assignment_id;
    private Long student_id;
    private String content;
    private String submitted_at;
}
