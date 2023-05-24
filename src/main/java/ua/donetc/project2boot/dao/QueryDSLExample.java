//package ua.donetc.project2boot.dao;
//
//import com.querydsl.core.types.Predicate;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ua.donetc.project2boot.models.QUsers;
//import ua.donetc.project2boot.models.Users;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//
//@Component
//
//public class QueryDSLExample {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Autowired
//    public QueryDSLExample(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    public void queryExample() {
//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//
//        QUsers users = QUsers.users;
//
//        Predicate predicate = users.role.eq("admin")
//                .and(users.email.contains("@example.com"));
//
//        JPAQuery<Users> query = queryFactory.selectFrom(users)
//                .where(predicate);
//
//        List<Users> userList = query.fetch();
//
//        for (Users user : userList) {
//            System.out.println("Username: " + user.getUsername() + ", Email: " + user.getEmail());
//        }
//    }
//
//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//}