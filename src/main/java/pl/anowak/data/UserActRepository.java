package pl.anowak.data;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.anowak.model.ApiUser;
import pl.anowak.model.User;
import pl.anowak.model.UserAct;
import pl.anowak.model.UserLog;

@ApplicationScoped
public class UserActRepository {

	@Inject
	EntityManager em;

	
	public void persist(UserAct userAct) {
		em.persist(userAct);
	}
	
	 public List<UserLog> getAll(User userId) {
		 em.find(UserLog.class, 1L);
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<UserLog> criteria = cb.createQuery(UserLog.class);
	        Root<UserLog> member = criteria.from(UserLog.class);
	        criteria.select(member).where(cb.equal(member.get("user").get("id"), userId.getId()));
	        return em.createQuery(criteria).getResultList();
	 }
	 
	 public Optional<User> getByToken(String token) {
	        CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<UserLog> criteria = cb.createQuery(UserLog.class);
	        Root<UserLog> member = criteria.from(UserLog.class);
	        criteria.select(member).where(cb.equal(member.get("authorizationToken"), token));
	        try {
	        	UserLog ul = em.createQuery(criteria).getSingleResult();
	        	return Optional.of(ul.getUser());
	        } catch(Exception ex) {
	        	return Optional.empty();
	        }
	 }

}
