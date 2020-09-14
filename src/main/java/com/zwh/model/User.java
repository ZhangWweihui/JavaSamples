package com.zwh.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.FactoryBean;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = -2692025054927651837L;
    
    private Long id;
    private String name;
    private Date birthday;
    private String idCardNum;
    private Integer sex;
    private String address;

    public static class UserStaticFactory {

        private UserStaticFactory(){}

        public static User getUser() {
            User user = new User();
            user.setId(1L);
            user.setName("酷酷酷");
            user.setBirthday(new Date());
            return user;
        }
    }

    public static class UserFactory {

        public UserFactory(){}

        public User getUser() {
            User user = new User();
            user.setId(1L);
            user.setName("酷酷酷");
            user.setBirthday(new Date());
            return user;
        }
    }

    public static class UserFactory2 implements FactoryBean<User> {

        @Override
        public User getObject() throws Exception {
            User user = new User();
            user.setId(1L);
            user.setName("酷酷酷");
            user.setBirthday(new Date());
            return user;
        }

        @Override
        public Class<User> getObjectType() {
            return User.class;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }
    }
}
