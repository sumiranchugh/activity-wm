package org.rssb.awm.processes.formtypes;

import org.activiti.engine.form.AbstractFormType;

/**
 * Created by Sumiran Chugh on 4/1/2016.
 *
 * @copyright atlas
 */
public class AttachmentFormType extends AbstractFormType {

    public static final String TYPE="attachment";
    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        return propertyValue;
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        return (String)modelValue;
    }

    @Override
    public String getName() {
        return TYPE;
    }
}
