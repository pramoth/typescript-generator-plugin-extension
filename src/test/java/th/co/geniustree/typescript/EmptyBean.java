package th.co.geniustree.typescript;

import java.util.List;

public class EmptyBean {
    private List<EmptyBean> children;
    private EmptyBean parent;
    private Studen studen;
    private String name;

    public List<EmptyBean> getChildren() {
        return children;
    }

    public void setChildren(List<EmptyBean> children) {
        this.children = children;
    }

    public EmptyBean getParent() {
        return parent;
    }

    public void setParent(EmptyBean parent) {
        this.parent = parent;
    }

    public Studen getStuden() {
        return studen;
    }

    public void setStuden(Studen studen) {
        this.studen = studen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
