package pronto;

import clojure.lang.IPersistentSet;
import clojure.lang.IPersistentMap;

import com.google.protobuf.Message;

public interface ProtoMapper {
    String getNamespace();

    IPersistentSet getClasses();

    ProtoMap fromBytes(Class<? extends Message> clazz, byte[] bytes);

    ProtoMap fromProto(Class<? extends Message> clazz, Message proto);

    ProtoMap getProto(Class<? extends Message> clazz);

    ProtoMap getTransient(Class<? extends Message> clazz);

    IPersistentMap getContext();
}
