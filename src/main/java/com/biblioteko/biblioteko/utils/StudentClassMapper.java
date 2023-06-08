package com.biblioteko.biblioteko.utils;

import java.util.stream.Collectors;

import com.biblioteko.biblioteko.studentClass.StudentClass;
import com.biblioteko.biblioteko.studentClass.StudentClassDTO;
import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserDTO;

public class StudentClassMapper {

	public static StudentClassDTO convertToStudentClassDTO(StudentClass studentClass) {

		User owner = studentClass.getOwner();

		UserDTO ownerDto = new UserDTO(
				owner.getId(),
				owner.getName(),
				owner.getEmail(),
				owner.getRole(),
				owner.getReadingList().stream()
				.map(l -> BookMapper.convertToBookDTO(l.getBook()))
				.collect(Collectors.toSet()),
				owner.getStarredBooks().stream()
				.map(b -> BookMapper.convertToBookDTO(b))
				.collect(Collectors.toSet())
				);

		StudentClassDTO prev = new StudentClassDTO(studentClass.getId(),
				studentClass.getName(),
				studentClass.getClassYear(),
				studentClass.getSchoolSubject(),
				studentClass.getPhoto(),
				ownerDto,
				null,
				null);

		if (studentClass.getStudents() != null) {

			prev.setStudents(studentClass.getStudents()
					.stream()
					.map(u -> u.getId())
					.collect(Collectors.toSet()));
		}

		if(studentClass.getSuggestedBooks() != null) {

			prev.setSuggestedBooks(studentClass.getSuggestedBooks()
					.stream()
					.map(u -> u.getId())
					.collect(Collectors.toSet()));

		}

		return prev;


	}

}
