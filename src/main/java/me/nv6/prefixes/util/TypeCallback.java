package me.nv6.prefixes.util;

import java.io.Serializable;

public interface TypeCallback<T> extends Serializable {

	void callback(T data);

}
