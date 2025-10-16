package id.eduparx.social.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.eduparx.social.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
    //findById(Long id)
    Optional<Post> findBySlug(String slug);    
}
