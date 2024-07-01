package com.campus_date.entity;

import com.campus_date.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "studentDetails")
public class StudentDetails {

    @Id
    @Column(name="studentDetails_id", length = 45)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentDetailsId;

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Student studentId;

    @Column(name="interest_gender", length = 255)
    private String interest_gender;

    @Column(name="interest_age_limit", length = 255)
    private String interest_age_limit;

    @Column(name="interest_distric", length = 255)
    private String interest_distric;

    @ElementCollection
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

}
