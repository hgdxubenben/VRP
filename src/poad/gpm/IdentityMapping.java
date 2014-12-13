package poad.gpm;

import poad.IGPM;

/**
 * The identity gpm which can be used when the search space and the solution space are the same.
 * 
 * @param <T>
 *          the basic type
 */
public final class IdentityMapping<T> implements IGPM<T, T> {
  /** the globally shared instance of the identity mapping */
  public static final IdentityMapping<Object> IDENTITY_MAPPING = new IdentityMapping<>();

  /** instantiate */
  private IdentityMapping() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final T gpm(final T genotype) {
    return genotype;
  }
}
