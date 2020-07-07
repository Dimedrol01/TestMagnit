package application.model.dao;

import application.model.Constants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = Constants.ENTRY)
public class Entry {
    private int field;

    @XmlElement(name = Constants.FIELD)
    public void setField(int f) {
        this.field = f;
    }

    public static Entry getEntry(int f) {
        Entry entry = new Entry();
        entry.setField(f);
        return entry;
    }

    public int getField() {
        return field;
    }
}
