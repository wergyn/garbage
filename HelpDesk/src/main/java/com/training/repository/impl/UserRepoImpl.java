package com.training.repository.impl;

import com.training.enums.Role;
import com.training.model.User;
import com.training.repository.UserRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepoImpl extends GenericRepoImpl<User, Long> implements UserRepo {

    @Override
    public Optional<User> findUserByEmail(String email) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> goodRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(goodRoot).where(criteriaBuilder.equal(goodRoot.get("email"), email));

        return currentSession().createQuery(criteriaQuery).getResultList().stream().findFirst();
    }

    @Override
    public List<User> findUsersByRole(Role role) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> goodRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(goodRoot)
                .where(criteriaBuilder.equal(goodRoot.get("role"), role));

        return currentSession().createQuery(criteriaQuery).list();
    }
}
