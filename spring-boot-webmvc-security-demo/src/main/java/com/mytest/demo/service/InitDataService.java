package com.mytest.demo.service;

import com.mytest.demo.entity.DemoRole;
import com.mytest.demo.entity.DemoUser;
import com.mytest.demo.repository.DemoRoleRepository;
import com.mytest.demo.repository.DemoUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class InitDataService {

    private final static Logger logger = LoggerFactory.getLogger(InitDataService.class);

    private final static Map<String, String> nameDescMap = new TreeMap<>();

    static {
        nameDescMap.put("A1", "大型客车");
        nameDescMap.put("A2", "牵引车");
        nameDescMap.put("A3", "城市工交车");
        nameDescMap.put("B1", "中型客车");
        nameDescMap.put("B2", "大型货车");
        nameDescMap.put("C1", "小型汽车");
        nameDescMap.put("C2", "小型自动档汽车");
        nameDescMap.put("C3", "低速载货汽车");
        nameDescMap.put("C4", "三轮车");
    }

    @Autowired
    private DemoUserRepository userRepository;

    @Autowired
    private DemoRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void init() {
        initAllRoles();
        initAllUsers();
    }

    public void find() {
        List<DemoUser> users = userRepository.findAll();
        logger.info("***** Insert result: *****");
        users.forEach(p -> logger.info(p.toString()));
        logger.info("**************************");
    }

    private void initAllRoles() {

        nameDescMap.forEach((p, q) -> {
            DemoRole role = new DemoRole(p, q);
            roleRepository.save(role);
        });

        roleRepository.flush();
    }

    private void initAllUsers() {
        DemoUser user;
        user = new DemoUser("A1", encoder.encode("A1"), RoleBuilder.instance().gen(1L).bulid());
        userRepository.save(user);
        user = new DemoUser("A2", encoder.encode("A2"), RoleBuilder.instance().gen(2L).bulid());
        userRepository.save(user);
        user = new DemoUser("A3", encoder.encode("A3"), RoleBuilder.instance().gen(3L).bulid());
        userRepository.save(user);
        user = new DemoUser("B1", encoder.encode("B1"), RoleBuilder.instance().gen(4L).bulid());
        userRepository.save(user);
        user = new DemoUser("B2", encoder.encode("B2"), RoleBuilder.instance().gen(5L).bulid());
        userRepository.save(user);
        user = new DemoUser("C1", encoder.encode("C1"), RoleBuilder.instance().gen(6L).bulid());
        userRepository.save(user);
        user = new DemoUser("C2", encoder.encode("C2"), RoleBuilder.instance().gen(7L).bulid());
        userRepository.save(user);
        user = new DemoUser("C3", encoder.encode("C3"), RoleBuilder.instance().gen(8L).bulid());
        userRepository.save(user);
        user = new DemoUser("C4", encoder.encode("C4"), RoleBuilder.instance().gen(9L).bulid());
        userRepository.save(user);

        userRepository.flush();
    }

    public static class RoleBuilder {

        private Set<DemoRole> roleSet;

        private RoleBuilder() {
            initData();
        }

        public static RoleBuilder instance() {
            return new RoleBuilder();
        }

        public RoleBuilder gen(Long id) {
            roleSet.add(new DemoRole(id));
            return this;
        }

        public Set<DemoRole> bulid() {
            return roleSet;
        }

        private void initData() {
            roleSet = new HashSet<>();
        }
    }
}