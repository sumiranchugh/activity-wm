package org.rssb.awm.processes.formtypes;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.form.AbstractFormType;

/**
 * Created by Sumiran Chugh on 4/1/2016.
 *
 * @copyright atlas
 */
public class ApproveRejectFormType extends AbstractFormType {

    public static final String TYPE="approvereject";


    @Override
    public String getName(){
        return TYPE;
    }

    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        if (propertyValue==null || "".equals(propertyValue)) {
            return null;
        }
        return Boolean.valueOf(propertyValue);
    }
    @Override
    public String convertModelValueToFormValue(Object modelValue) {

        if (modelValue==null) {
            return null;
        }

        if(Boolean.class.isAssignableFrom(modelValue.getClass())
                || boolean.class.isAssignableFrom(modelValue.getClass())) {
            return modelValue.toString();
        }
        throw new ActivitiIllegalArgumentException("Model value is not of type boolean, but of type " + modelValue.getClass().getName());
    }

}
