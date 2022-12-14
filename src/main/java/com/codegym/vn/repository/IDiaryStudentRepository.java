package com.codegym.vn.repository;

import com.codegym.vn.model.DiaryStudent;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IDiaryStudentRepository extends PagingAndSortingRepository<DiaryStudent, Long> {
}
