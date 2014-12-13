package poad;

import java.util.Arrays;

/** some utilities */
public class Utils {
  /**
   * get a string representation of a given object
   * 
   * @param o
   *          the object
   * @return the string representation
   */
  public static final String toString(final Object o) {
    if (o == null) {
      return String.valueOf(o);
    }
    if (o instanceof byte[]) {
      return Arrays.toString((byte[]) o);
    }
    if (o instanceof int[]) {
      return Arrays.toString((int[]) o);
    }
    if (o instanceof long[]) {
      return Arrays.toString((long[]) o);
    }
    if (o instanceof double[]) {
      return Arrays.toString((double[]) o);
    }
    if (o instanceof float[]) {
      return Arrays.toString((float[]) o);
    }
    if (o instanceof boolean[]) {
      return Arrays.toString((boolean[]) o);
    }
    if (o instanceof char[]) {
      return Arrays.toString((char[]) o);
    }
    if (o instanceof short[]) {
      return Arrays.toString((short[]) o);
    }
    if (o.getClass().isArray()) {
      return Arrays.deepToString((Object[]) o);
    }
    return o.toString();
  }
}
