package com.example.ORM_project.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tests")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TestSubmission> testSubmissions = new HashSet<>();

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Question> questions = new HashSet<>();
}
