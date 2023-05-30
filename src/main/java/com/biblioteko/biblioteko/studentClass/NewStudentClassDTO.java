package com.biblioteko.biblioteko.studentClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewStudentClassDTO {
	private String name;
    private int classYear;
	private String schoolSubject;
	private String photo;

    public NewStudentClassDTO(String name, int classYear, String schoolSubject, String photo){
        this.name = name;
        this.classYear = classYear;
        this.schoolSubject = schoolSubject;
        this.photo = photo;

    }
    
}
