package com.mytest.demo.repository.db1;

import com.mytest.demo.entity.db1.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("db1CountryRepository")
public interface CountryRepository extends JpaRepository<Country, Long> {
}