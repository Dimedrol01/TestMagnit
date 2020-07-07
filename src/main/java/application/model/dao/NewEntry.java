package application.model.dao;

import application.model.Constants;

import javax.xml.bind.annotation.XmlAttribute;

public class NewEntry {
    private int field;

    @XmlAttribute(name = Constants.FIELD)
    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }
}
