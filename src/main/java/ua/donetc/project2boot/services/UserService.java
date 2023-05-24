package ua.donetc.project2boot.services;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.donetc.project2boot.models.QUsers;
import ua.donetc.project2boot.models.Users;
import ua.donetc.project2boot.repositories.UserRepo;

import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;




    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;

    }

//    public List<Users> getUserByEmailAndRole(String email, String role){
//        QUsers qUsers = QUsers.users;
//        Predicate predicate = qUsers.email.eq(email).and(qUsers.role.eq(role));
//
//        JPAQuery<Users> query = queryFactory.selectFrom(qUsers).where(predicate);
//        return query.fetch();
//    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<Users> findAll() {
        return userRepo.findAll();
    }

    public Users findOneUser(int id) {
        Optional<Users> foundUser = userRepo.findById(id);
        return foundUser.orElse(null);
    }

    @Transactional
    public void saveUser(Users users) {
        String encodedPass = passwordEncoder.encode(users.getPassword());
        users.setPassword(encodedPass);
        users.setRole("ROLE_USER");
        userRepo.save(users);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public void updateUser(int id, Users updateUsers) {
        updateUsers.setId(id);
        userRepo.save(updateUsers);

    }

    public Optional<Users> getUserByFullName(String fullName) {
        return userRepo.findUsersByUsername(fullName);
    }

    public List<Users> findUsersByRole(String role){
        return userRepo.findUsersByRole(role);
    }




}
