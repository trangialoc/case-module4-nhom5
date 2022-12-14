package com.codegym.vn.repository;

import com.codegym.vn.model.FeeInformation;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IFeeInformationRepository extends PagingAndSortingRepository<FeeInformation, Long> {
}
