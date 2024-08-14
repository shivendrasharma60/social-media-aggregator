package com.social.media.aggregator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.media.aggregator.entity.Influencer;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {

	Influencer findByUniquehash(String uniquehash);

	List<Influencer> findByFollowers_Id(Long userId);

}
