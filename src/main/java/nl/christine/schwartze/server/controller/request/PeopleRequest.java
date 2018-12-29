package nl.christine.schwartze.server.controller.request;

import java.util.ArrayList;
import java.util.List;

public class PeopleRequest {

    private List<Integer> ids = new ArrayList<>();

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids.addAll(ids);
    }
 }
