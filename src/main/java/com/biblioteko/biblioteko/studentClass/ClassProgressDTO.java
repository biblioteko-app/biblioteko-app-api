package com.biblioteko.biblioteko.studentClass;

import java.util.List;

import com.biblioteko.biblioteko.read.ReadDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassProgressDTO {

    private float overallClassProgress;

    private List<ReadDTO> individualStudentProgressByBook;

    public ClassProgressDTO(float overallClassProgress, List<ReadDTO> individualStudentProgressByBook) {
        this.overallClassProgress = overallClassProgress;
        this.individualStudentProgressByBook = individualStudentProgressByBook;
    }

    
}
