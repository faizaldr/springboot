package id.eduparx.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.eduparx.social.model.User;

@Repository
// JPARepository Return <Model,tipedata_id>
public interface UserRepository extends JpaRepository<User,Long>{
    
}
