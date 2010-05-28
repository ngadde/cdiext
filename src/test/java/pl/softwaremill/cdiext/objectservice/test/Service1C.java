package pl.softwaremill.cdiext.objectservice.test;

/**
 * @author Adam Warski (adam at warski dot org)
 */
public class Service1C implements Service1<C> {
    private C serviced;

    @Override
    public void setServiced(C serviced) {
        this.serviced = serviced;
    }

    @Override
    public Object get() {
        return serviced.getValue();
    }
}