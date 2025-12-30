package com.example.ORM_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestCaseResponseDto {
    private Long id;
    private Long module_id;
    private String title;
}
