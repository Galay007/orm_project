package com.example.ORM_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseResponseDto {
    private Long id;
    private String title;
    private String description;
    private String duration;
    private String start_date;
    private Long teacher_id;
    private Long category_id;
    private Set<Long> tag_ids;
}
