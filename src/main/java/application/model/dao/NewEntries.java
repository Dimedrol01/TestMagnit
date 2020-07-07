package application.model.dao;

import application.model.Constants;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = Constants.ENTRY, name = Constants.ROOT_ELEMENT)
@XmlRootElement (name = Constants.ROOT_ELEMENT)
public class NewEntries {
    private List<NewEntry> newEntry = new ArrayList<NewEntry>();

    public List<NewEntry> getEntry() {
        return newEntry;
    }

    public void setEntry(List<NewEntry> entry) {
        this.newEntry = entry;
    }
}
