package filter;

import packet.MyPacket;

/**
 * @author R.Q.
 * description:否解析过滤器
 */
public class NotFilter implements Filter {
    private final Filter filter;

    NotFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public boolean matches(MyPacket myPacket) {
        return !filter.matches(myPacket);
    }
}
