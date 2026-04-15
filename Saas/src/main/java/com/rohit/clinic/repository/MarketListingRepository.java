package com.rohit.clinic.repository;

import com.rohit.clinic.entity.MarketListing;
import com.rohit.clinic.entity.MatchSide;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketListingRepository extends JpaRepository<MarketListing, Long> {

    List<MarketListing> findByProduct_IdAndSideAndIsActiveTrue(Long productId, MatchSide side);

    List<MarketListing> findBySideAndIsActiveTrue(MatchSide side);
}
