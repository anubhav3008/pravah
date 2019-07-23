package com.pravah.web.dao;

import org.jdbi.v3.spring4.JdbiFactoryBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutosuggestionDao {

    @Autowired
    JdbiFactoryBean jdbiFactoryBean;

    private AutoSuggestionJDBIDao getAutoSuggestJdbiDao() throws  Exception {
        return	jdbiFactoryBean.getObject().onDemand(AutoSuggestionJDBIDao.class);
    }

    public List<String> getStaticValue() throws Exception {
        return getAutoSuggestJdbiDao().getStaticValue();
    }


    interface AutoSuggestionJDBIDao{

        @SqlQuery("select val from autosuggestion")
        List<String> getStaticValue();

    }

}
