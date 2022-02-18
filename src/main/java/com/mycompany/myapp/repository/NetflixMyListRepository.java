package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NetflixMyList;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NetflixMyList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NetflixMyListRepository extends JpaRepository<NetflixMyList, Long> {
    @Query("select netflixMyList from NetflixMyList netflixMyList where netflixMyList.user.login = ?#{principal.username}")
    List<NetflixMyList> findByUserIsCurrentUser();
}
