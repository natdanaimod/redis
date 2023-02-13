package co.th.natdanai.redis.repository;

import co.th.natdanai.redis.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

    Optional<Tutorial> findById(Long id);

    List<Tutorial> findByTitleContaining(String title);

}
