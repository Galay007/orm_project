package com.example.ORM_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestSubmissionRequestDto {
    private Long test_id;
    private Long student_id;
    private Integer score;
    private String taken_at;
}
