package pronto;

import com.google.protobuf.Message;

public class RT {

    public static Message getProto(ProtoMap map) {
        return map.pmap_getProto();
    }

    public static boolean isBuilder(Object o) {
        return o instanceof Message.Builder;
    }
}
