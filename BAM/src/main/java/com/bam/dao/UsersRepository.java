
package com.bam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bam.beans.Batch;
import com.bam.beans.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	public Users findByUserId(Integer id);

	public Users findByEmail(String email);

	public List<Users> findByBatch(Batch batch);

	public List<Users> findByRole(int role);
}
