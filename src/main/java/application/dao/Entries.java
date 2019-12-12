package application.dao;

import application.other.Constants;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = Constants.ENTRY, name = Constants.ROOT_ELEMENT)
@XmlRootElement (name = Constants.ROOT_ELEMENT)
public class Entries {
    private List<Entry> entry = new ArrayList<Entry>();

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }
}
