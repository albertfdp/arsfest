package dk.dtu.arsfest.model;

import java.util.ArrayList;
import java.util.Collection;

public class Predicate {
	
	public static Object predicateParams;
	
	public interface IPredicate<T> { boolean apply (T type); };
	
	public static <T> Collection<T> filter(Collection<T> collection, IPredicate<T> predicate) {
		Collection<T> result = new ArrayList<T>();
		for (T element : collection) {
			if (predicate.apply(element)) {
				result.add(element);
			}
		}
		return result;
	}
	
	public static <T> T select(Collection<T> collection, IPredicate<T> predicate) {
		T result = null;
		for (T element : collection) {
			if (!predicate.apply(element))
				continue;
			result = element;
			break;
		}
		return result;
	}
	
	public static <T> T select(Collection<T> collection, IPredicate<T> predicate, T defaultValue) {
		T result = defaultValue;
		for (T element : collection) {
			if (!predicate.apply(element))
				continue;
			result = element;
			break;
		}
		return result;
	}

}
