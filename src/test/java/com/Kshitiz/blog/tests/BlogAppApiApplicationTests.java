package com.Kshitiz.blog.tests;


import com.Kshitiz.blog.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApiApplicationTests {

    @Autowired
    private UserRepo userRepo;
    @Test
    void contextLoads() {
        // verifies if the Spring context starts properly
    }
    @Test
    public void repoTest()
    {
        String className = this.userRepo.getClass().getName();
        String packName = this.userRepo.getClass().getPackageName();
        System.out.println(className);
        System.out.println(packName);
    }

}
