package com.metafour.mtrak.router.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.metafour.mtrak.router.entities.GeneralLog;

/**
 * @author minhaj
 *
 */
@Repository
public interface GeneralLogRepo extends CrudRepository<GeneralLog, Long> {

	GeneralLog findByCode(String code);
	List<GeneralLog> findAllByCode(String code);
	List<GeneralLog> findByCodeContaining(String code);
}
