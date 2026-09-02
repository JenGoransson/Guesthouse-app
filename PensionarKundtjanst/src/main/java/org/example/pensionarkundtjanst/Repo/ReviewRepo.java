package org.example.pensionarkundtjanst.Repo;

import org.example.pensionarkundtjanst.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}
