package pronto;

import clojure.lang.Keyword;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;

/**
 * Base functionality implemented by any generated proto-map.
 * <p>
 * Note: the ugly `pmap_` prefix in method names is a precaution to avoid
 * a conflict with the `get<Field>` and `has<Field>` methods that are generated as part of
 * the <Object>OrBuilder interfaces.
 **/
public interface ProtoMap<T extends Message> {
    boolean isMutable();

    boolean pmap_hasField(Keyword key);

    ProtoMap<T> clearField(Keyword key);

    T pmap_getProto();

    Keyword whichOneOf(Keyword key);

    Message.Builder pmap_getBuilder();

    ProtoMap<T> copy(Message.Builder builder);

    ProtoMap<T> remap(ProtoMapper mapper);

    ProtoMap<?> empty(Keyword keyword);

    ProtoMap<?> fromBuilder(Message.Builder builder);
}
