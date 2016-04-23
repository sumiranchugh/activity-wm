package org.rssb.awm.confg;

import org.activiti.engine.form.AbstractFormType;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.rssb.awm.processes.formtypes.ApproveRejectFormType;
import org.rssb.awm.processes.formtypes.AttachmentFormType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sumiran Chugh on 4/1/2016.
 *
 * @copyright atlas
 */
@Configuration
@Order(90)
public class ActivitiConfig {

    @Bean
    public static BeanPostProcessor activitiConfiguration() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof SpringProcessEngineConfiguration) {
                    List<AbstractFormType> customFormTypes = Arrays.<AbstractFormType>asList(new AttachmentFormType(), new ApproveRejectFormType());
                    ((SpringProcessEngineConfiguration)bean).setCustomFormTypes(customFormTypes);
                    ((SpringProcessEngineConfiguration)bean).setHistoryLevel(HistoryLevel.FULL);
                }
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }
        };

    }
}
