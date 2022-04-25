package filter;

import packet.MyPacket;

/**
 * @author R.Q.
 * description:与逻辑过滤器
 */
public class AndFilter implements Filter {
    private final Filter field1;
    private final Filter field2;

    AndFilter(Filter field1, Filter field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    @Override
    public boolean matches(MyPacket myPacket) {
        return field1.matches(myPacket) && field2.matches(myPacket);
    }
}
