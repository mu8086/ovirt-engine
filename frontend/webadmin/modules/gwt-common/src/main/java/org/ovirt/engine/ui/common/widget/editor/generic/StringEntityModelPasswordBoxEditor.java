package org.ovirt.engine.ui.common.widget.editor.generic;

import org.ovirt.engine.ui.common.widget.parser.generic.ToStringEntityModelParser;

public class StringEntityModelPasswordBoxEditor extends EntityModelPasswordBoxEditor<String> {

    public StringEntityModelPasswordBoxEditor() {
        super(new ToStringEntityModelRenderer<String>(), new ToStringEntityModelParser());
    }

    public void setAutocomplete(String value) {
        getContentWidget().getElement().setAttribute("autocomplete", value); //$NON-NLS-1$
    }
}
