package com.example.cloudArchitecture.repository;

import com.example.cloudArchitecture.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {
}
