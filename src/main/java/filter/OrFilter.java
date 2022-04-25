package filter;

import packet.MyPacket;

/**
 * @author R.Q.
 * description:或解析过滤器
 */
public class OrFilter implements Filter {
    private final Filter leftSide;
    private final Filter rightSide;

    OrFilter(Filter leftSide, Filter rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public boolean matches(MyPacket myPacket) {
        return leftSide.matches(myPacket) || rightSide.matches(myPacket);
    }
}
