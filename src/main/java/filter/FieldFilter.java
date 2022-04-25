package filter;

import packet.MyPacket;

/**
 * @author R.Q.
 * description:内容解析类，判断过滤条件是否格式不正确，匹配包
 */
class FieldFilter implements Filter {
    private final String filterName;
    private final String filterValue;

    FieldFilter(String filterString) throws InvalidFilterException {
        String[] parts = filterString.split("=");
        if (parts.length != 2) {
            throw new InvalidFilterException("过滤器字符串: " + filterString + " 格式不正确！");
        }
        filterName = parts[0].trim();
        filterValue = parts[1].trim();
    }

    @Override
    public boolean matches(MyPacket myPacket) {
        return switch (filterName) {
            case "srcaddr" -> myPacket.getSrcAddress().equalsIgnoreCase(filterValue);
            case "destaddr" -> myPacket.getDestAddress().equalsIgnoreCase(filterValue);
            case "type" -> myPacket.getPacketType().name().equalsIgnoreCase(filterValue);
            case "length" -> Integer.toString(myPacket.getLength()).equalsIgnoreCase(filterValue);
            default -> false;
        };
    }
}
