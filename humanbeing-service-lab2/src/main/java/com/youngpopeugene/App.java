package com.youngpopeugene;

import com.youngpopeugene.controller.EnumController;
import com.youngpopeugene.controller.HumanBeingController;
import com.youngpopeugene.controller.TeamController;
import com.youngpopeugene.exception.*;
import com.youngpopeugene.filter.*;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
public class App extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        classes.add(HumanBeingController.class);
        classes.add(TeamController.class);
        classes.add(EnumController.class);

        classes.add(NotFoundExceptionMapper.class);
        classes.add(UnprocessableEntityExceptionMapper.class);
        classes.add(ValidationExceptionMapper.class);
        classes.add(PageExceptionMapper.class);
        classes.add(ExceptionMapper.class);
        classes.add(AnotherExceptionMapper.class);
        classes.add(TransactionalExceptionMapper.class);


        classes.add(URILengthFilter.class);
        classes.add(RedirectHttpFilter.class);
        classes.add(CorsFilter.class);
        classes.add(RateLimitingFilter.class);
        classes.add(RateLimitingResponseFilter.class);
        return classes;
    }

}
