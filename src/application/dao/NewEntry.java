package application.dao;

import application.other.Constants;

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
