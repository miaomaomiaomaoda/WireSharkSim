package filter;

import packet.MyPacket;

/**
 * @author R.Q.
 * description:过滤器接口
 */
public interface Filter {
    /**
     * function:匹配包，返回匹配结果
     *
     * @param myPacket 包
     * @return 匹配结果
     */
    boolean matches(MyPacket myPacket);
}

