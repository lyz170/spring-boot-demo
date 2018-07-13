package com.mytest.demo.repository.db2;

import com.mytest.demo.entity.db2.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("db2CountryRepository")
public interface CountryRepository extends JpaRepository<Country, Long> {
}